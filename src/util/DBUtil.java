package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import annotation.Colonne;
import annotation.NoMap;
import annotation.Table;
import database.Column;
import database.DBColumn;
import exception.MismatchException;

public class DBUtil {

    public static String getTableName(Object obj){
        Class<?> clazz = obj.getClass();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table annotation = clazz.getAnnotation(Table.class);
            if (!annotation.nom().equals("")) {
                return annotation.nom();
            }
            return clazz.getSimpleName().toLowerCase();
        }
        return null;
    }

    public static String getTableName(Class<?> clazz){
        if (clazz.isAnnotationPresent(Table.class)) {
            Table annotation = clazz.getAnnotation(Table.class);
            if (!annotation.nom().equals("")) {
                return annotation.nom();
            }
            return clazz.getSimpleName().toLowerCase();
        }
        return null;
    }

    public static List<Column> getColumns(Object obj) {
        List<Column> columns = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NoMap.class)) {
                continue;
            }
            Column column = new Column();
            column.setNomColonneClasse(field.getName());
            if (field.isAnnotationPresent(Colonne.class)) {
                Colonne col = field.getAnnotation(Colonne.class);
                column.setNomColonneBase(col.value());
            } 
            else column.setNomColonneBase(field.getName());
            column.setTypeColonne(field.getType().getSimpleName());
            columns.add(column);
        }

        return columns;
    }

    public static List<Column> getColumns(Class<?> clazz) {
        List<Column> columns = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NoMap.class)) {
                continue;
            }
            Column column = new Column();
            column.setNomColonneClasse(field.getName());
            if (field.isAnnotationPresent(Colonne.class)) {
                Colonne col = field.getAnnotation(Colonne.class);
                column.setNomColonneBase(col.value());
            } 
            else column.setNomColonneBase(field.getName());
            column.setTypeColonne(field.getType().getSimpleName());
            columns.add(column);
        }

        return columns;
    }

    public static boolean verifyColumns(List<Column> colonneClasse, List<DBColumn> colonneBase) throws MismatchException {
        if (colonneBase.size() != colonneClasse.size()) {
            System.out.println(colonneBase.size());
            System.out.println(colonneClasse.size());
            throw new MismatchException("nombre de colonnes invalide");
        }
        for (int i = 0; i < colonneClasse.size(); i++) {
            if (!colonneBase.get(i).getNomColonne().equals(colonneClasse.get(i).getNomColonneBase())) {
                throw new MismatchException("noms colonnes invalide");
            }
        }
        return true;
    }

    public static String getNextSequenceValue(String sequenceName, Connection con) throws SQLException {
        String sql = "SELECT nextval('" + sequenceName + "')";
        try (PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return String.valueOf(rs.getInt(1)); 
            } else {
                throw new SQLException("La sequence n'existe pas");
            }
        }
    }


    public static void preparePK(Object obj, Connection con) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        Class<?> clazz = obj.getClass();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table annotation = clazz.getAnnotation(Table.class);
            Method met = clazz.getDeclaredMethod("setId", String.class);
            String prefix = annotation.prefixe(); 
            String sequenceName = "seq_" + getTableName(obj); 
            String sequenceValue = getNextSequenceValue(sequenceName, con); 
            String formattedId = prefix + String.format("%05d", Integer.parseInt(sequenceValue));
            met.invoke(obj, formattedId);
        }
    }


}

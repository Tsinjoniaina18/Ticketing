package database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.PGConnect;
import exception.MismatchException;
import util.DBUtil;

/**
 *
 * @author Miarantsoa
 */
public class GenericRepo<T>{

    public static <T> T save(T obj) throws SQLException, MismatchException{
        Class<?> clazz = obj.getClass(); 
        String id = null;
        try {
            Method met = clazz.getDeclaredMethod("getId");
            id = (String) met.invoke(obj);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            if (id != null) {
                obj = update(obj);
            } else {
                obj = persist(obj);
            }
            return obj;
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de l'insertion/la mise à jour de donnée");
        }
    }

    public static <T> List<T> findAll(Class<T> clazz) throws SQLException, MismatchException {
        PGConnect pgConnect = PGConnect.getInstance();
        Connection connection = pgConnect.getConnection();

        String tableName = DBUtil.getTableName(clazz);
        List<Column> columns = DBUtil.getColumns(clazz);
        List<DBColumn> dbColumns = DBColumn.getDBColumns(tableName, connection);
        DBUtil.verifyColumns(columns, dbColumns);

        String query = "SELECT * FROM " + tableName;

        List<T> resultList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();

                for (Column column : columns) {
                    String columnName = column.getNomColonneClasse();
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true); 
                    Object value = resultSet.getObject(columnName);
                    if(field.getType().getName().equals("double")){
                        value = resultSet.getDouble(columnName);
                    } else if (field.getType().getName().equals("java.time.LocalDateTime")) {
                        value = ((Timestamp) value).toLocalDateTime();
                    }
                    field.set(obj, value);
                }

                resultList.add(obj); 
            }
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException |
                InvocationTargetException | NoSuchMethodException e) {
            throw new SQLException("Erreur lors de la création des objets", e);
        }

        return resultList;
    }

    public static <T> List<T> findCondition(Class<T> clazz, String afterWhere) throws SQLException, MismatchException {
        PGConnect pgConnect = PGConnect.getInstance();
        Connection connection = pgConnect.getConnection();

        String tableName = DBUtil.getTableName(clazz);
        List<Column> columns = DBUtil.getColumns(clazz);
        List<DBColumn> dbColumns = DBColumn.getDBColumns(tableName, connection);
        DBUtil.verifyColumns(columns, dbColumns);

        String query = "SELECT * FROM " + tableName + " where 1=1";
        if(afterWhere!=null){
            query += afterWhere;
        }

        List<T> resultList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();

                for (Column column : columns) {
                    String columnName = column.getNomColonneClasse();
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true); 
                    Object value = resultSet.getObject(columnName);
                    if(field.getType().getName().equals("double")){
                        value = resultSet.getDouble(columnName);
                    } else if (field.getType().getName().equals("java.time.LocalDateTime")) {
                        value = ((Timestamp) value).toLocalDateTime();
                    }
                    field.set(obj, value);
                }

                resultList.add(obj); 
            }
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException |
                InvocationTargetException | NoSuchMethodException e) {
            throw new SQLException("Erreur lors de la création des objets", e);
        }

        return resultList;
    }

    public static <T> T findById(String id, Class<T> clazz) throws SQLException, MismatchException {
        PGConnect pgConnect = PGConnect.getInstance();
        Connection connection = pgConnect.getConnection();
    
        String tableName = DBUtil.getTableName(clazz);
        List<Column> columns = DBUtil.getColumns(clazz);
        List<DBColumn> dbColumns = DBColumn.getDBColumns(tableName, connection);
        DBUtil.verifyColumns(columns, dbColumns);
    
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);  
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    T obj = clazz.getDeclaredConstructor().newInstance();
    
                    for (Column column : columns) {
                        String columnName = column.getNomColonneClasse();
                        Field field = clazz.getDeclaredField(columnName);
                        field.setAccessible(true);
                        Object value = resultSet.getObject(columnName);
                        if(field.getType().getName().equals("double")){
                            value = resultSet.getDouble(columnName);
                        } else if (field.getType().getName().equals("java.time.LocalDateTime")) {
                            value = ((Timestamp) value).toLocalDateTime();
                        }
                        field.set(obj, value);
                    }
    
                    return obj; 
                }
            } catch (NoSuchFieldException | IllegalAccessException | InstantiationException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new SQLException("Error while creating object", e);
            }
        }
    
        return null;  
    }
    

    public static <T> void remove(T obj) throws SQLException, MismatchException {
        Class<?> clazz = obj.getClass();
        PGConnect pgConnect = PGConnect.getInstance();
        Connection connection = pgConnect.getConnection();
        
        String tableName = DBUtil.getTableName(obj);
        List<Column> columns = DBUtil.getColumns(obj);
        List<DBColumn> dbColumns = DBColumn.getDBColumns(tableName, connection);
        DBUtil.verifyColumns(columns, dbColumns);

        String query = "DELETE FROM " + tableName + " WHERE id = ?";
    
        String id = "";
        try {
            Method met = clazz.getDeclaredMethod("getId");
            id += (String) met.invoke(obj);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        }
    
    }

    public static <T> T persist(T obj) throws SQLException, MismatchException {
        PGConnect pgConnect = PGConnect.getInstance();
        Connection connection = pgConnect.getConnection();
        
        String tableName = DBUtil.getTableName(obj);
        List<Column> columns = DBUtil.getColumns(obj);
        List<DBColumn> dbColumns = DBColumn.getDBColumns(tableName, connection);
        DBUtil.verifyColumns(columns, dbColumns);
        try {
            DBUtil.preparePK(obj, connection);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
        queryBuilder.append(tableName).append(" (");
        for (int i = 0; i < columns.size(); i++) {
            queryBuilder.append(columns.get(i).getNomColonneBase());
            if (i < columns.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(") VALUES (");
        for (int i = 0; i < columns.size(); i++) {
            queryBuilder.append("?");
            if (i < columns.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(") RETURNING id");

        String query = queryBuilder.toString();
        System.out.println(query);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Class<?> clazz = obj.getClass();
            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);
                String fieldName = column.getNomColonneClasse();
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true); 
                Object value = field.get(obj);
                preparedStatement.setObject(i + 1, value);
            }


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Field idField = clazz.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(obj, resultSet.getObject("id")); 
            }

            return obj;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new SQLException("Erreur lors de l'accès aux champs de l'objet", e);
        } catch (SQLException e) {
            throw new SQLException("Possible missmatch des colonnes, verifier votre base", e);
        }
    }

    public static <T> T update(T obj) throws SQLException, MismatchException {
        PGConnect pgConnect = PGConnect.getInstance();
        Connection connection = pgConnect.getConnection();
    
        String tableName = DBUtil.getTableName(obj);
        List<Column> columns = DBUtil.getColumns(obj);
        List<DBColumn> dbColumns = DBColumn.getDBColumns(tableName, connection);
    
        DBUtil.verifyColumns(columns, dbColumns);
    
        StringBuilder queryBuilder = new StringBuilder("UPDATE ");
        queryBuilder.append(tableName).append(" SET ");
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            queryBuilder.append(column.getNomColonneBase()).append(" = ?");
            if (i < columns.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(" WHERE id = ?");
    
        String query = queryBuilder.toString();
        System.out.println(query);
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Class<?> clazz = obj.getClass();
            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);
                String fieldName = column.getNomColonneClasse();
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true); 
                Object value = field.get(obj); 
                preparedStatement.setObject(i + 1, value); 
            }
    
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(obj);
            preparedStatement.setObject(columns.size() + 1, idValue);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Aucun enregistrement trouvé pour l'ID spécifié.");
            }
    
            return obj;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new SQLException("Erreur lors de l'accès aux champs de l'objet", e);
        } catch (SQLException e) {
            throw new SQLException("Possible missmatch des colonnes, verifier votre base", e);
        }
    }
    
}

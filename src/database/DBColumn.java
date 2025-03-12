package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBColumn {
    
    private String nomColonne;

    public String getNomColonne() {
        return nomColonne;
    }

    public void setNomColonne(String nomColonne) {
        this.nomColonne = nomColonne;
    }

    public DBColumn(String nomColonne) {
        this.setNomColonne(nomColonne);
    }

    public static List<DBColumn> getDBColumns(String nomTable, Connection c) throws SQLException {
        List<DBColumn> result = new ArrayList<>();
        System.out.println(nomTable);
        try {
            DatabaseMetaData metaData = c.getMetaData();

            ResultSet res = metaData.getColumns(null, null, nomTable, null);
            while (res.next()) {
                DBColumn db = new DBColumn(res.getString("COLUMN_NAME"));
                result.add(db);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException("Accession aux colonnes de la table "+ nomTable + " impossible" + e.getMessage());
        }
    }
    
}

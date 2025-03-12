package connection;

import java.io.Serializable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Miarantsoa
 */
public class PGConnect implements Serializable {

    private static PGConnect instance;
    private Connection connection;

    private PGConnect() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.err.println("Fichier application.properties introuvable.");
            }
            Class.forName("org.postgresql.Driver") ;


            String url = properties.getProperty("spa.url");
            String user = properties.getProperty("spa.username");
            String password = properties.getProperty("spa.password");

            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de propriétés : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized PGConnect getInstance() {
        if (instance == null) {
            instance = new PGConnect();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Erreur lors du démarrage de la transaction : " + e.getMessage());
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true); 
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'extinction de la transaction: "+ e.getMessage());
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la validation de la transaction : " + e.getMessage());
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'annulation de la transaction : " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
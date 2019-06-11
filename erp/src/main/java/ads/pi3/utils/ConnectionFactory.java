package ads.pi3.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.cj.jdbc.Driver;

public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/projeto_integrador?useTimezone=true&serverTimezone=UTC";

    public static Connection getConnetion() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na Conexão! ", ex);
        }
    }
    // iniciar a conexão com o banco

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // finalizar a conexão com o banco

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

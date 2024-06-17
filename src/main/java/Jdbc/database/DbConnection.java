package Jdbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    // Bloqueio para sincronização
    private static final Object lock = new Object();
    private static Connection con = null;

    // Método para obter o objeto de conexão
    public static Connection getConnection() {
        synchronized (lock) {
            try {
                // Verifica se a conexão está fechada e tenta reabri-la se necessário
                if (con == null || con.isClosed()) {
                    String url = "jdbc:mysql://localhost:3306/COMPANY";
                    String user = "root";
                    String pass = "root";
                    con = DriverManager.getConnection(url, user, pass);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Não foi possível conectar ao banco de dados, verifique os dados de conexão\n"+e);
            }
        }
        return con;
    }
}

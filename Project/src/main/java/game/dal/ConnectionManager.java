package game.dal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private final String user = "root";
    private final String password = "271828";
    private final String hostName = "localhost";
    private final int port = 3306;
    private final String schema = "CS5200Project";
    private final String timezone = "UTC";

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.user);
        connectionProps.put("password", this.password);
        connectionProps.put("serverTimezone", this.timezone);
        
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://" + this.hostName + ":" + this.port + "/" + this.schema,
                connectionProps);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }
} 
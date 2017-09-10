package connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by ZeroxTV
 */
public class MySQL {
    private Connection connection;
    private String ip;

    public void connect(String ip, String username, String password) {
        try {
            MysqlDataSource source = new MysqlDataSource();
            source.setServerName(ip);
            source.setUser(username);
            source.setPassword(password);
            connection = source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String command) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(command);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String command) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeArray(ArrayList<String> commands) {
        for (String command : commands) {
            execute(command);
        }
    }

    public void switchDatabase(String name)  {
        try {
            connection.setCatalog(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

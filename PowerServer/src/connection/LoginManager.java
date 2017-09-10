package connection;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LoginManager {

    private MySQL db = new MySQL();

    public LoginManager() {
        db.connect("localhost", "server", "powerbox");
        db.execute("CREATE DATABASE IF NOT EXISTS Login");
        db.switchDatabase("Login");
        db.execute("CREATE TABLE IF NOT EXISTS Users (" +
                "Name VARCHAR(24) PRIMARY KEY, " +
                "Trash VARCHAR(32), " +
                "Password VARCHAR(256))");
    }

    public boolean checkLogin(String user, String password) {
        try {
            ResultSet rs = db.executeQuery("SELECT * FROM Users WHERE Name='" + user + "'");
            rs.next();
            String trash = rs.getString("Trash");
            String passHash = rs.getString("Password");
            String connected = password + trash;

            System.out.println(passHash);
            System.out.println(connected);

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(connected.getBytes());
            byte[] bytes = messageDigest.digest();
            String s = DatatypeConverter.printHexBinary(bytes);

            return s.equals(passHash);

        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void register(String user, String password) {
        try {
            String trash = UUID.randomUUID().toString().replace("-", "");

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String connected = password + trash;
            messageDigest.update(connected.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            String s = DatatypeConverter.printHexBinary(bytes);

            db.execute("INSERT INTO Users (Name, Trash, Password) VALUES " + String.format("('%s','%s','%s')", user, trash, s));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

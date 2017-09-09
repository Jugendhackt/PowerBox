package connection;

public class Main {
    private static Thread listener;
    public static LoginManager login;

    public static void main(String[] args) {
        listener = new Listener().runThread();

        //MySQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        login = new LoginManager();
    }
}

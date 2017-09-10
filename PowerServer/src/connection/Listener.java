package connection;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Listener implements Runnable {

    private ServerSocket socket;

    public static ArrayList<SingleSocketCommunicator> connections = new ArrayList<>();

    Listener() {
        try {
            socket = new ServerSocket(144);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Thread runThread() {
        Thread t = new Thread(this);
        t.run();
        return t;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket client = socket.accept();
                if (client.isConnected()) {
                    SingleSocketCommunicator ssc = new SingleSocketCommunicator(client);
                    connections.add(ssc);
                    new Thread(ssc).run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package connection;

import connection.packets.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SingleSocketCommunicator implements Runnable {
    private Socket socket;
    private Packet current = null;
    public String username;

    public SingleSocketCommunicator(Socket socket) {
        this.socket = socket;
    }

    private void send(String s) throws IOException {
        byte[] bytes = s.getBytes();
        ByteBuffer bb = ByteBuffer.wrap(new byte[bytes.length]);
        bb.put(bytes);
        socket.getOutputStream().write(bb.array());
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            while(socket.isConnected()) {
                if (in.available() == 0) continue;
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                if (current == null) {
                    Packet.getFromId(Integer.parseInt(new String(bytes)));
                } else {
                    current.pass(new String(bytes));
                }
                System.out.println(new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logIn(String username) {
        this.username = username;
    }
}

package connection.packets;

import connection.Listener;
import connection.Main;
import connection.SingleSocketCommunicator;

import java.net.Socket;

public class LoginPacket extends Packet {
    @Override
    public int getArgAmount() {
        return 2;
    }

    @Override
    public void execute(SingleSocketCommunicator s) {
        if (Main.login.checkLogin(args.get(0), args.get(1))) {
            s.logIn(args.get(0));
        }
    }
}

package connection.packets;

import connection.Main;
import connection.SingleSocketCommunicator;

import java.net.Socket;

public class RegisterPacket extends Packet {

    @Override
    public int getArgAmount() {
        return 2;
    }

    @Override
    public void execute(SingleSocketCommunicator s) {
        Main.login.register(args.get(0), args.get(1));
    }

    //169.254.131.56
}

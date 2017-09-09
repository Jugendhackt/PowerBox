package connection.packets;

import connection.SingleSocketCommunicator;

import java.net.Socket;
import java.util.List;

public abstract class Packet {
    protected List<String> args;

    public abstract int getArgAmount();

    public boolean takesNextArg() {
        return getArgAmount() != args.size();
    }

    public abstract void execute(SingleSocketCommunicator s);

    public void pass(String arg) {
        if (takesNextArg()) args.add(arg);
    }

    public static Packet getFromId(int id) {
        switch (id) {
            case 2001: //login
                return new LoginPacket();
            case 2002: //register
                return new RegisterPacket();
        }

        return null;
    }
}

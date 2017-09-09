package connection.packets;

import connection.SingleSocketCommunicator;

import java.util.Random;

public class ReservePacket extends Packet {
    @Override
    public int getArgAmount() {
        return 1;
    }

    @Override
    public void execute(SingleSocketCommunicator s) {
        int pin = 0;
        for (int i = 0; i < 4; i++) {
            pin = pin * 10 + new Random().nextInt(9);
        }

    }
}

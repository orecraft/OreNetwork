package top.mahua_a.orenetwork.network;

import java.net.DatagramPacket;

public interface IPacketHandler {
    void handle(DatagramPacket packet);
}

package top.mahua_a.orenetwork;

public class Main {
    public static OreNetwork oreNetwork;

    public static void main(String[] args) {
        oreNetwork = new OreNetwork();
        //   udp.handle();
        oreNetwork.start();
    }

}


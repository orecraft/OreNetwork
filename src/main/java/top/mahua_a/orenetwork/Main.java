package top.mahua_a.orenetwork;

import top.mahua_a.orenetwork.node.Node;

public class Main {
    public static OreNetwork oreNetwork;

    public static void main(String[] args) {

        OreNetwork.addSeedNode(new Node("127.0.0.1",1008,true));
        //   udp.handle();
        if(args.length>0) {
            OreNetwork.start(args[0].equalsIgnoreCase("--server"));
        }else{
            OreNetwork.start(false);
        }
    }

}


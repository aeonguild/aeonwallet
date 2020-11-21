package com.aeon.app.models;


import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

public class Node {
    private static final String TAG = "Node";
    public String hostAddress;
    public String hostPort;
    public long height;
    public long target;
    public long version;
    public static ArrayList<Node> nodes;

    static {
        nodes = new ArrayList<>();
        //nodes.add(new Node("5.45.108.241","32405"));
        nodes.add(new Node("144.76.113.157"));
        nodes.add(new Node("95.111.247.164"));
        nodes.add(new Node("95.216.145.71"));
        //nodes.add(new Node("144.76.113.157"));
        //nodes.add(new Node("194.116.45.216"));
        nodes.add(new Node("3.14.4.25"));
    }
    public Node(String hostAddress){
        this(hostAddress,"11181");
    }
    public Node(String hostAddress, String port) {
        Log.v(TAG, "Node");
        this.hostAddress = hostAddress;
        this.hostPort = port;
    }

    public static Node
    pickRandom(){
        Node newNode  = nodes.get(new Random().nextInt(nodes.size()));
        return new Node(newNode.hostAddress, newNode.hostPort);
    }
}

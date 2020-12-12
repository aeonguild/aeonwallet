package com.aeon.app.irc;

import java.io.*;
import java.net.*;
import java.util.*;

public class IRCMessageLoop implements Runnable {
    Socket server;
    OutputStream out;
    List<String> channelList;
    boolean initial_setup_status;
    
    public IRCMessageLoop() {
        String serverName ="chat.freenode.net";
        int port = 6667;
        channelList = new ArrayList<String>();
        try
        {
            server = new Socket(serverName, port);
            out = server.getOutputStream();
        }
        catch (IOException info)
        {}
    }

    void send(String text) {
        byte[] bytes = (text + "\r\n").getBytes();
        
        try {
            out.write(bytes);
        }
        catch (IOException info)
        {}
    }
    
    void nick(String nickname) {
        String msg = "NICK " + nickname;
        send(msg);
    }
    
    void user(String username, String hostname, String servername, String realname) {
        String msg = "USER " + username + " " + hostname + " " + servername +  " :" + realname;
        send(msg);
    }

    void join(String channel) {
        if (!initial_setup_status) {
            channelList.add(channel);
            return;
        }
        String msg = "JOIN " + channel;
        send(msg);
    }
    
    void part(String channel) {
        String msg = "PART " + channel;
        send(msg);
    }
    
    void privmsg(String to, String text) {
        String msg = "PRIVMSG " + to + " :" + text;
        send(msg);
    }
    
    void pong(String server) {
        String msg = "PONG " + server;
        send(msg);
    }
    
    void quit(String reason) {
        String msg = "QUIT :Quit: " + reason;
        send(msg);
    }
    
    void initial_setup() {
    
        initial_setup_status = true;
    
        // now join the channels. you need to wait for message 001 before you join a channel.
        for (String channel: channelList) {
            join(channel);
        }
        
        
    }
    
    void processMessage(String ircMessage) {
        Message msg = MessageParser.message(ircMessage);
            
        if (msg.command.equals("privmsg")) {
            String target, content;
            
            if (msg.content.equals("\001VERSION\001")) {
                privmsg(msg.nickname, "Prototype IRC Client (Built to learn)");
                return;
            }
            System.out.println("PRIVMSG: " + msg.nickname + ": " + msg.content);
        }
        else if (msg.command.equals("001")) {
            initial_setup();
            return;
        }
        else if (msg.command.equals("ping")) {
            pong(msg.content);
        }
    }
    
    public void run() {
        InputStream stream = null;
        nick("Christine_Chubbuck");
        user("6a3k_49GRK", "null", "null", "real name");
        join("#aeon");
        try
        {
            stream = server.getInputStream();
            MessageBuffer messageBuffer = new MessageBuffer();
            byte[] buffer = new byte[512];
            int count;
            
            while (true) {
                count = stream.read(buffer);
                if (count == -1)
                    break;
                messageBuffer.append(Arrays.copyOfRange(buffer, 0, count));
                while (messageBuffer.hasCompleteMessage()) {
                    String ircMessage = messageBuffer.getNextMessage();

                    System.out.println("\"" + ircMessage + "\"");
                    processMessage(ircMessage);
                }
            }
        }
        catch (IOException info)
        {
            quit("error in messageLoop");
            info.printStackTrace();
        }
    }
}

package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;


import java.util.*;
public class Main {

    private static ServerSocket serverSocket;
    private static final int PORT = 1234;

    public static void main(String[] args) {


        String host;

        Scanner input = new Scanner(System.in);

        InetAddress address;
        System.out.print("\n\nEnter host name: ");

        host = input.next();

        try {

            address = InetAddress.getByName(host);

            System.out.println("IP address: "

                    + address.toString());

        } catch (UnknownHostException uhEx) {

            System.out.println("Could not find " + host);

        }

        printMyIp();

        System.out.println("Opening port");
        try {
            serverSocket = new ServerSocket(PORT);
        }catch (IOException ioE)
        {
            System.out.println("Error: " + ioE);
            System.exit(1);
        }
        do {
            handleClient();
        }while (true);


    }

    private static void printMyIp(){

        try
        {
            InetAddress address =
                    InetAddress.getLocalHost();
            System.out.println(" My Local Address: " +address);
        }
        catch (UnknownHostException uhEx) {
            System.out.println("Could not find local address!");
        }
    }

    private static void handleClient() {
        Socket link = null;

        try {
            link = serverSocket.accept();

            Scanner input =
                    new Scanner(link.getInputStream());
            PrintWriter output =
                    new PrintWriter(
                            link.getOutputStream(),true);

            int numMessages = 0;
            String message = input.nextLine();
            while(!message.equals("***CLOSE***"))
            {
                System.out.println("Message received.");
                numMessages++;
                output.println("Message " + numMessages
                + ": " + message);

                message = input.nextLine();
            }
            output.println(numMessages + " messages received.");
        }catch (IOException ioE)
        {
            ioE.printStackTrace();
        }finally {
            try {
                System.out.println(
                        "\n* Closing connection... *"
                );
                link.close();
            }catch (IOException ioE){
                System.out.printf("UNABLE TO DISCONNECT!");
                System.exit(1);
            }
        }
    }

}



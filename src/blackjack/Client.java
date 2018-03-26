/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Jabneel
 */
public class Client {
    Socket socket = null;
    PrintWriter out = null;

    public Client(String machineName, int port) {
        try {
            socket = new Socket("localhost", port);
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Could not send to " + machineName + ":" + port);
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("Generic exception");
            e.printStackTrace();
        }
    }

    void send(String msg) {
        out.println(msg);
    }

    public static void main(String argv[]) throws IOException ,ClassNotFoundException
    {
        String message;
        ArrayList<Card> hand = new ArrayList<>();
        
        String hostName = "localhost";
        int port = 40059;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Remote host: " + hostName + " Port: " + port);
        Client sender = new Client(hostName,port);
        BufferedReader remoteInput = new BufferedReader(new InputStreamReader(sender.socket.getInputStream()));
        ObjectInputStream ois=new ObjectInputStream(sender.socket.getInputStream());
        ObjectOutputStream oostream=new ObjectOutputStream(sender.socket.getOutputStream());
        
        String commandAvail ="\nCommands available: deal , show, stay\n";
        System.out.print(commandAvail);
        while (true) {
            System.out.print("Enter a command: ");            
            message = input.readLine();
            sender.send(message);
            
            if(message.equals("deal"))
            {
                Card newCard = ((Card) (ois.readObject()));
                hand.add(newCard);
                System.out.println("Card Received: \n"+newCard);
            }
            else if(message.equals("show"))
            {
                System.out.println("Current Hand: \n"+hand);
            }
            else if(message.equals("stay"))
            {
                try { oostream.writeObject(hand);} catch(Exception e){ break;} 
                   System.out.println("Sending hand to server: "+hand);
                   message = remoteInput.readLine();
                   System.out.println("Total Value of Hand: " + message +"\n");
            }
        }
    }
}

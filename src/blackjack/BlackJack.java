/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Jabneel
 * 
 */
public class BlackJack {

    /**
     * @param args the command line arguments
     */
    PrintWriter out=null;
    //static int aceValue =0;
    public static void main(String[] args)throws IOException, InterruptedException, ClassNotFoundException {
        // TODO code application logic here
        Deck newDeck = new Deck();
        
        ArrayList<Card> cards = new ArrayList<>(); 
        cards = newDeck.getDeck();
        ArrayList <Card> hand;
        System.out.println("Deck: \n"+cards);
        System.out.println(cards.get(0));
        
        try {
            ServerSocket s = new ServerSocket(40059);

            //This server only accepts 1 client
            System.out.println("Waiting for client to connect ");
            Socket client = s.accept();
            System.out.println("We have a connection!!!");

            BufferedReader remoteInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
            /* The following could be used to create a synchronized conversation
      BufferedReader localInput=new BufferedReader(new InputStreamReader(System.in));  //could have used Scanner - just did this to make both inputs look alike
             */
            PrintWriter remoteOutput = new PrintWriter(client.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            
            ObjectOutputStream oostream=new ObjectOutputStream(client.getOutputStream());
            String message;
            
            ObjectInputStream ois=new ObjectInputStream(client.getInputStream());

            Card aceCard0 =  new Card(0,0);
            Card aceCard1 =  new Card(0,1);
            Card aceCard2 =  new Card(0,2);
            Card aceCard3 =  new Card(0,3);
            
            while (true) {
                String aceStr ;
                message = remoteInput.readLine();
                System.out.println("\nCommand from client: " + message);
                int aceValue =0;
                if(message.equals("deal"))
                {
                    Card randomCard=cards.get((int) (Math.random()*cards.size()));
                    System.out.println("Sending: " + randomCard);
                    try { 
                        oostream.writeObject(randomCard);
                        if(randomCard.equals(aceCard0) || randomCard.equals(aceCard1) ||randomCard.equals(aceCard2) ||randomCard.equals(aceCard3)) 
                        {
                            aceStr = remoteInput.readLine();
                            aceValue = Integer.parseInt(aceStr);
                        }
                        
                    } catch(Exception e){ break;} 
                        Thread.sleep(2000);
                }
                else if(message.equals("stay"))
                {
                    
                    hand= (ArrayList <Card>)ois.readObject();
                    System.out.println(hand.getClass());
                    System.out.println(hand);
                    if(hand instanceof ArrayList)
                    {
                        int handTotal=0;
                        handTotal+=aceValue;
                        ArrayList<Card> clientHand = (ArrayList) hand;

                        System.out.println("Input was an ArrayList\n");
                        for(int x = 0; x<clientHand.size(); x++)
                        {
                            int r;
                            int cardValue;
                            //Check card values in client handssta
                            r=clientHand.get(x).getRank();
                            cardValue =clientHand.get(x).getValue(r);
                            handTotal+=cardValue;
                        }
                        message ="Total value of hand: "+handTotal;
                        System.out.println(message);
                        remoteOutput.println(message);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    void send(String msg)
    {
        out.println(msg);
    }
}
    


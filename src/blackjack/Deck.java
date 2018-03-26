/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;
import java.util.*;
/**
 *
 * @author Zaido
 */
public class Deck {
    ArrayList<Card> myDeck= new ArrayList<>();
    
    public Deck()
    { 
    //Initialize the cards in deck
        for(int s = 0; s<=3;s++){
            for(int r =0; r<=12;r++){
                myDeck.add(new Card(s, r));
            }
        }
    }
    
    public ArrayList<Card> getDeck()
    {
        return this.myDeck;
    }
    public String toString()
    {
        String result = " "+myDeck;
        return result;
    }
    
}

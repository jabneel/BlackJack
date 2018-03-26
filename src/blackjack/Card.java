/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.io.Serializable;
/**
 *
 * @author Zaido
 */
public class Card implements Serializable{
    int r, s;
    String [] rank  = {"ace","2","3","4","5","6","7","8","9","10","jack","queen","king"};
    String[] suit  = {"spades","hearts", "diamonds","clubs"};
    
    public int value;
    
    public Card(int s,int r )
    {
        this.r= r;
        this.s = s;
        
    }
    
    public Card()
    {
        
    }
    
    public int getRank()
    {
        return r;
    }
    
    public int getValue(int i)
    {
        int value =0;
            for(int x = 1;x<=r; x++)
            {
                if(r!=0 && x==r && r<10)
                {
                    value= x+1;
                }
                if(x==r && r>=10)
                {
                    value = 10;
                }
            }
        return value;
    }

    public String toString()
    {
        return "R:" + rank[r] + " S:" + suit[s];
    }
    
}

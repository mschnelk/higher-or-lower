import java.util.*;

public class Deck {
    
    private int[] deck = new int[52];
    private int remainingcards = 52;

    public Deck()
    {
        // add cards to deck
        for (int i = 1; i <= 52; i++)
            deck[i-1] = i;
        shuffle();
    }

    public void shuffle()
    {
        Random rand = new Random();
        for (int i = 0; i < remainingcards; i ++)
        {
            int oldCard = deck[i];
            int newPos = rand.nextInt(remainingcards);
            deck[i] = deck[newPos];
            deck[newPos] = oldCard;

        }
    }

    public String getSuit(int card)
    {
        String suit = "";
        if (card < 14)
            suit = "♠️";
        else if (card < 27)
            suit = "♣️";
        else if (card < 40)
            suit = "♥︎";
        else
            suit = "♦";
        return suit;
    }  
    
    public String getColor(int card)
    {
        if (getSuit(card).equals("♠️") || getSuit(card).equals("♣️"))
            return "black";
        else
            return "red";
    }

    public int cardAt(int position)
    {
        return deck[position];
    }

    public int getCardValue(int card)
    {
        for (int i = 13; i > 0; i--)
            if (card%i == 0)
                return i;

        return 0;
    }

    public String getCardString(int card)
    {
        String value = "";
        if (getCardValue(card) == 13)
            value += "K";
        else if (getCardValue(card) == 12)
            value += "Q";
        else if (getCardValue(card) == 11)
            value += "J";
        else if (getCardValue(card) == 1)
            value += "A";
        else
            value += getCardValue(card);
        
        return value + getSuit(card);
    }

    public int nextCard()
    {
        int nextcard = cardAt(52-remainingcards);
        remainingcards--;
        return nextcard;
    }

    public int getRemainingCards()
    {
        return remainingcards;
    }

    public String toString()
    {
        String deckstring = "";
        for (int i = 52-remainingcards; i < 52; i++)
            deckstring += "[" + deck[i] + "] ";
        return deckstring;
    }

    public void reset()
    {
        remainingcards = 52;
        shuffle();
    }
}

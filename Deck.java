import java.util.*;

public class Deck {

    private String[] stringDeck = new String[52];   // holds actual card values (e.g. K♥︎)
    private int numberOfCards;

    public Deck()
    {
        int value;
        for (int i = 51; i >= 0; i--)
        {
            int cardNumber = i+1;   // number 1-52 used to find suit and value
            value = (cardNumber)%13;   
            if (value == 0)
                stringDeck[i] = "K" + getSuit(cardNumber);
            else if (value == 12)
                stringDeck[i] = "Q" + getSuit(cardNumber);
            else if (value == 11)
                stringDeck[i] = "J" + getSuit(cardNumber);
            else if (value == 1)
                stringDeck[i] = "A" + getSuit(cardNumber);
            else
                stringDeck[i] = value + getSuit(cardNumber);
                    
            numberOfCards++;       
        }

        shuffle();
    }

    public void shuffle()
    {
        Random rand = new Random();
        for (int i = 0; i < numberOfCards; i ++)
        {
            String oldCardString = stringDeck[i];

            int newPos = rand.nextInt(numberOfCards);

            stringDeck[i] = stringDeck[newPos];
            stringDeck[newPos] = oldCardString;

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

    public String nextCard()
    {
        String nextcard = stringDeck[numberOfCards-1];
        numberOfCards--;
        return nextcard;
    }

    public int getCardValue(String card)
    {
        if (card.contains("K"))
            return 13;
        else if (card.contains("Q"))
            return 12;
        else if (card.contains("J"))
            return 11;
        else if (card.contains("A"))
            return 1;
        else if (card.contains("10"))
            return 10;
        else 
            return Character.getNumericValue(card.charAt(0));
    }

    public String getColor(String cardValue)
    {
        if (cardValue.contains("♠️") || cardValue.contains("♣️"))
            return "black";
        else
            return "red";
    }

    public int getNumberOfCards()
    {
        return numberOfCards;
    }

    public String toString()
    {
        String deckstring = "";
        for (int i = 0; i < numberOfCards; i++)
            deckstring += "[" + stringDeck[i] + "] ";
        return deckstring;
    }

    public void reset()
    {
        numberOfCards = 52;
        shuffle();
    }
}

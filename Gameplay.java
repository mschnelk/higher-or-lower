import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Gameplay extends JPanel implements MouseListener {
    private String page = "MENU";
    private String[][] board = new String[3][3];
    private Deck deck;

    private int guessRow = -1;  // card position that the user is guessing at
    private int guessCol = -1;

    private boolean guessing = false;
    private boolean playing = false;
    private boolean win = false;

    public Gameplay()
    {
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        deck = new Deck();
    }

    public void makeBoard()
    {
        // add first nine cards in deck to the board
        for (int i  = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = deck.nextCard();
    }

    public void paint(Graphics g)
    {
        // fonts
        Font normal = new Font("Times New Roman", Font.BOLD, 25);
        Font title = new Font ("Times New Roman", Font.BOLD, 40);
        Font smaller = new Font ("Times New Roman", Font.BOLD, 20);
        Font bigger = new Font ("Times New Roman", Font.BOLD, 60);  

        // background
        g.setColor(Color.pink);
        g.fillRect(1,1,500,700);

        switch(page)
        {
            case "MENU":
                // print title
                g.setColor(Color.red);
                g.setFont(smaller);
                g.drawString("Welcome to", (500-g.getFontMetrics().stringWidth("Welcome to"))/2, 150);

                // print ◆︎ ♥︎
                g.setFont(bigger);
                g.drawString("♥︎", 80, 300);
                g.drawString("♦", 280, 300);
                
                // print subtitle
                g.setColor(Color.black);
                g.setFont(title);
                int titlewidth = g.getFontMetrics().stringWidth("Higher or Lower");
                g.drawString ("Higher or Lower", (500-titlewidth)/2, 200);
                
                // print ♠️ ♣️
                g.setColor(Color.black);
                g.setFont(bigger);
                g.drawString("♠️", 180, 300);
                g.drawString("♣️", 380, 300);

                // option boxes
                g.setColor(Color.red);
                g.fillRect(250-titlewidth/2+10, 375, titlewidth-20, 50);
                g.fillRect(250-titlewidth/2+10, 440, titlewidth-20, 50);
                g.fillRect(250-titlewidth/2+10, 505, titlewidth-20, 50);


                // option text
                g.setColor(Color.pink);
                g.setFont(normal);
                g.drawString("New Game",190, 410);
                g.drawString("Instructions", 183, 475);
                g.drawString("Leaderboard", 180, 537);
                break;
            case "GAME":
                // print title
                g.setFont(smaller);
                g.setColor(Color.white);
                g.drawString("Higher or Lower", 250-(g.getFontMetrics().stringWidth("Higher or Lower"))/2, 25);
            
                // draw board
                for (int i = 0; i < board.length; i++)
                {
                    for (int j = 0; j < board[0].length; j ++)
                    {
                        // draw cards
                        g.setFont(bigger);
                        g.setColor (Color.white);
                        g.fillRoundRect(20+155*j, 35+210*i, 140, 196, 5, 5);

                        // set color of card font
                        if (deck.getColor(board[i][j]).equals("red"))
                            g.setColor(Color.red);
                        else if (deck.getColor(board[i][j]).equals("black"))
                            g.setColor(Color.black);
    
                        // add card number and suit to card
                        g.drawString((board[i][j]), 50+155*j, 160+210*i);

                        // if user is guessing a card, show higher & lower options
                        if (guessing && i == guessRow && j == guessCol)
                        {
                            // outline for options
                            g. setColor(Color.pink);
                            g.fillRect(30+155*j, 45+210*i, 120, 50);
                            g.fillRect(30+155*j, 171+210*i, 120, 50);

                            // add text for guessing
                            g.setFont(smaller);
                            g.setColor(Color.white);
                            g.drawString("Higher", 60+155*j, 75+210*i);
                            g.drawString("Lower", 65 + 155*j, 201+210*i);

                        }

                        // if they guess incorrectly, show game over screen
                        if (!playing)
                        {
                            // big box for game over screen
                            if (win)
                                g.setColor(Color.blue);
                            else
                                g.setColor(Color.red);
                            g.fillRect(125, 225, 250, 250);

                            // small boxes for after game choices
                            g.setColor(Color.pink);
                            g.fillRect(175, 370 ,150, 40);
                            g.fillRect(180, 420, 140, 30);
                                    
                            // add text to game over box
                            g.setFont(title);
                            if (win)
                            {
                                g.setColor(Color.white);
                                g.setFont(title);
                                g.drawString ("YOU", 125 +(250 - g.getFontMetrics().stringWidth("YOU"))/2, 280);
                                g.drawString("WON", 125 +(250 - g.getFontMetrics().stringWidth("WON"))/2, 320);
                            }
                            else
                            {
                                g.setColor(Color.pink);
                                g.drawString ("GAME", 125 +(250 - g.getFontMetrics().stringWidth("GAME"))/2, 280);
                                g.drawString("OVER", 125 +(250 - g.getFontMetrics().stringWidth("OVER"))/2, 320);

                                g.setFont(smaller);
                                int score = deck.getNumberOfCards() + 1;
                                g.drawString("You had " + score + " cards left", 125 +(250 - g.getFontMetrics().stringWidth("You had XX cards left"))/2, 350);
                            }
            
                            g.setColor(Color.white);
                            g.setFont(normal);
                            g.drawString ("Play Again", 250 - (g.getFontMetrics().stringWidth("Play Again"))/2, 400);

                            g.setFont(smaller);
                            g.drawString("Back to Home", 250 - (g.getFontMetrics().stringWidth("Back to Home"))/2, 442);
                            
                        }
                        // if they win (go through all the cards, show a winning screen)
                        else if (!playing && deck.getNumberOfCards()==0)
                        {
                             // big box for game over screen
                             g.setColor(Color.blue);
                             g.fillRect(125, 225, 250, 250);
 
                             // small boxes for after game choices
                             g.setColor(Color.pink);
                             g.fillRect(175, 370 ,150, 40);
                             g.fillRect(180, 420, 140, 30);

                              // add text to game over box
                            g.setColor(Color.white);
                            g.setFont(title);
                            g.drawString ("YOU", 125 +(250 - g.getFontMetrics().stringWidth("YOU"))/2, 280);
                            g.drawString("WON", 125 +(250 - g.getFontMetrics().stringWidth("WON"))/2, 320);

                            g.setColor(Color.white);
                            g.setFont(normal);
                            g.drawString ("Play Again", 250 - (g.getFontMetrics().stringWidth("Play Again"))/2, 400);

                            g.setFont(smaller);
                            g.drawString("Back to Home", 250 - (g.getFontMetrics().stringWidth("Back to Home"))/2, 442);
                        }


                    }
                }
                break;
             
            case "HELP": 
                // gameplay instructions 
                g.setColor(Color.white);
                g.fillRect(1,1,500,700);
                
                // add title & subtitle
                g.setColor(Color.black);
                g.setFont(title);
                g.drawString("Instructions", 250 - (g.getFontMetrics().stringWidth("Instructions"))/2, 60);

                g.setFont(normal);
                g.drawString("Important Notes:", 20, 280);

                // add instructions
                g.setFont(smaller);
                g.drawString ("In Higher or Lower, 9 cards will be placed face up", 20, 100);
                g.drawString("in a 3x3 grid", 20, 120);
                g.drawString("The player will choose a face up card and guess if the", 20, 150);
                g.drawString("the next card in the deck will be higher or lower than", 20, 170);
                g.drawString("the chosen face up card",  20, 190);
                g.drawString("The goal of the game is to get through the whole deck", 20, 220);
                g.drawString("by guessing the ranks of all 52 cards correctly", 20, 240);

                g.drawString(" * Ace's are considered low", 20, 310);
                g.drawString(" * If the cards are equal in value, it counts as a", 20, 335);
                g.drawString("correct guess", 40, 355);
                g.drawString(" * I've never witnessed anyone actually complete this", 20, 380);
                g.drawString("game, so GOOD LUCK!!", 40, 400);
                
                // add options after instructions
                g.setColor(Color.pink);
                g.fillRect(150, 430, 200, 60);
                g.fillRect(175, 500, 150, 50);

                g.setFont(normal);
                g.setColor(Color.white);
                g.drawString("Play Game", 250 - (g.getFontMetrics().stringWidth("Play Game"))/2, 467);

                g.setFont(smaller);
                g.drawString("Back to Menu", 250 - (g.getFontMetrics().stringWidth("Back to Menu"))/2, 532);
                break;
        
            case "SCORES":
                // add title
                g.setFont(title);
                g.setColor(Color.white);
                g.drawString("Leaderboard", 250 - (g.getFontMetrics().stringWidth("Leaderboard"))/2, 160);
                
                // box to hold scores
                g.setColor(Color.red);
                g.fillRect(100, 200, 300, 300);

                g.setFont(normal);
                g.setColor(Color.white);

                // add scores
                for (int i = 0; i < 5; i++)
                {
                    g.drawString(i+1 + ".", 130, 260+50*i);

                    try {
                        g.drawString(scoreData(i)[0], 160, 260+50*i);
                        g.drawString(scoreData(i)[1], 380 - g.getFontMetrics().stringWidth(scoreData(i)[1]), 260+50*i);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                // add button to return home
                g.setColor(Color.white);
                g.fillRect(150, 525, 200, 50);

                g.setColor(Color.pink);
                g.drawString("Back to Home", 250 - (g.getFontMetrics().stringWidth("Back to Home"))/2, 560);

                break;
            }
    }


    // return string array [score, name] for given place in file
    public String[] scoreData(int place) throws FileNotFoundException
    {
        File scores = new File("leaderboard.txt");
        Scanner fileReader = new Scanner(scores);
        String nextScore = null;

        for (int i = 0; i <= place; i++)
            nextScore = fileReader.nextLine();

        // scores as stored as ##name, use substring to get the wanted data
        fileReader.close();
        String[] leaderboardArray = {nextScore.substring(0, 2), nextScore.substring( 2)};

        return leaderboardArray;
    }

    public void checkForHighScore (int score) throws FileNotFoundException
    {
        File scores = new File("leaderboard.txt");
        String[][] leaderboard = new String[5][2];  // [score, name]

        // fill leaderboard array
        for (int i = 0; i < 5; i++)
        {   
            leaderboard[i][0] = scoreData(i)[0];  
            leaderboard[i][1] = scoreData(i)[1];  
        }
           
        

        // check score against those on leaderboard
        for (int i = 0; i < 5; i++)
        {                  
            if (score < Integer.parseInt(leaderboard[i][0]))
            {
                // make room for new score
                for (int j = 4; j > i; j--)
                {
                    leaderboard[j][0] = leaderboard[j-1][0];
                    leaderboard[j][1] = leaderboard[j-1][1];
                }

                // add score & name to array
                if (score < 10) 
                    leaderboard[i][0] = "0" + score;
                else
                    leaderboard[i][0] = "" + score;
                String name = JOptionPane.showInputDialog( "You got a high score! Enter your name:");
                leaderboard[i][1] = name;

                break;
            }        
        }

        // delete and rewrite leaderboard file
        PrintWriter fileWriter = new PrintWriter(scores);
        fileWriter.write("");
        for (int i = 0; i < 5; i++)
            fileWriter.println(leaderboard[i][0] + leaderboard[i][1]);

        fileWriter.close();
        
    }

    public void switchPage(String newpage) // switch between menu and gameplay
    {
        page = newpage;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent m) {
       
        int x = m.getX();
        int y = m.getY();

        switch (page)
        {
            case "HELP":
                if (x >= 150 && x <= 350 && y >= 430 && y <= 490)
                    startGame();
                else if (x >= 175 && x <= 325 && y >= 500 && y <= 550)
                    mainMenu();
                break;

            case "MENU":
                if (x >= 95 && x <= 404 && y >= 375 && y <= 425)
                    startGame();

                else if (x >= 95 && x <= 404 && y >= 440 && y <= 490)
                    switchPage("HELP");
                
                else if (x >= 95 && x <= 404 && y >= 505 && y <= 550)
                    switchPage("SCORES");
                
                break;

            case "GAME":
                if (!guessing && playing)
                {
                    // find the card that user clicked
                    for (int i = 0; i < board.length; i++)
                        for (int j = 0; j < board[0].length; j++)
                            if (x >= 20+155*j && x <= 160+155*j && y >= 35+210*i && y <= 231+210*i)
                            {
                                guessRow = i;
                                guessCol = j;
                                guessing = true;
                                repaint();
                            }
                }

                else if (playing && guessing){
                    // find whether user guessed higher or lower
                    if (x >= 30+155*guessCol && x <= 150+155*guessCol &&
                        y >= 45+210*guessRow && y <= 95+210*guessRow)
                            makeGuess(1); // higher

                    else if (x >= 30+155*guessCol && x <= 150+155*guessCol &&
                        y >= 171+210*guessRow && y <= 221+210*guessRow)
                            makeGuess(-1); // lower
                }

                else if (!playing && !guessing)
                {
                    // find what option the user picked 
                    if (x >= 175 && x <= 325 && y >= 370 && y <= 410)
                        startGame();
                    else if (x >= 180 && x <= 320 && y >= 420 && y <= 450)
                        mainMenu();

                }
                break; 
            
            case "SCORES":
                //g.fillRect(150, 525, 200, 50);
                if (x >= 150 && x <= 350 && y >= 525 && y <= 575)
                    mainMenu();
        }
        
    }

    public void startGame()
    {
        // reset variables for proper gameplay
        playing = true;
        guessing = false;
        win = false;
        guessRow = -1;
        guessCol = -1;
        // reshuffle deck & reset number of cards
        deck.reset();

        makeBoard();
        switchPage("GAME");
        repaint();
    }

    public void mainMenu()
    {
        // reset variables & return to main menu
        guessing = false;
        playing = false;
        guessRow = -1;
        guessCol = -1;
        switchPage("MENU");
        repaint();
    }

    public void winAnimation()
    {

    }

    public void makeGuess(int guess)
    {
        guessing = false;

        // if incorrect guess, game is over & player did not win
        if (!correctGuess(guess))
        {
            win = false;
            playing = false;
            repaint();               
            try 
                {
                    checkForHighScore(deck.getNumberOfCards()+1);
                }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // if correct guess & no cards remaining, player won
        else if (deck.getNumberOfCards() == 0)
        {
            win = true;
            playing = false;
            repaint();               
            try 
                {
                    checkForHighScore(deck.getNumberOfCards());
                }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
           
        }
        
        // if game is not over and player made correct guess, reset variables for gameplay
        if (playing) {
            guessing = false;
            guessRow = -1;
            guessCol = -1;
            repaint();
        }

    }

    public boolean correctGuess(int guess)
    {
        // store value of current card to compare
        String currentcard = board[guessRow][guessCol];

        // find value of next card in the deck
        board[guessRow][guessCol] = deck.nextCard();

        if ((deck.getCardValue(currentcard) < deck.getCardValue(board[guessRow][guessCol]) && guess == -1) || 
        (deck.getCardValue(currentcard) > deck.getCardValue(board[guessRow][guessCol]) && guess == 1))
            return false;
        
        return true;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

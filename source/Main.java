package source;
import javax.swing.JFrame;

public class Main 
{
    public static void main (String[] args)
    {
        JFrame obj = new JFrame();
        obj.setBounds(10,10,500, 700);
        Gameplay game = new Gameplay();
        obj.setTitle("High-Low Solitaire");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
    }
}

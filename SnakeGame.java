import javax.swing.JFrame;
public class SnakeGame {
    public static void main(String[] args) {
        JFrame frame=new JFrame("Sa=nake GAme-Nokia");
        GamePanel gamepanel=new GamePanel();
        frame.add(gamepanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

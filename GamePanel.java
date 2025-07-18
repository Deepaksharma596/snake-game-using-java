import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener {
    static final int Screen_Width=600;
    static final int Screen_Height=600;
    static final int Unit_size=25;
    static final int Game_Units=(Screen_Width*Screen_Height)/(Unit_size*Unit_size);
    static final int delay=100;

    final int []x=new int[Game_Units];
    final int []y=new int[Game_Units];

    int bodyparts=6;
    int appleeaten;

    int appleX;
    int appleY;

    char direction='R';

    boolean running =false;
    
    Timer timer;
    Random random;

    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(Screen_Width,Screen_Height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(delay,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running){
            for(int i=0;i<Screen_Height/Unit_size;i++){
                g.setColor(Color.darkGray);
                g.drawLine(i*Unit_size,0,i*Unit_size,Screen_Height);
                g.drawLine(0,i*Unit_size,Screen_Width,i*Unit_size);
            }
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,Unit_size,Unit_size);

            for(int i=0;i<bodyparts;i++){
                if(i==0){
                    g.setColor(Color.green);
                }
                else{
                    g.setColor(new Color(45,180,0));
                }
                g.fillRect(x[i],y[i],Unit_size,Unit_size);
            }
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free",Font.BOLD,30));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score: " + appleeaten,
                (Screen_Width - metrics.stringWidth("Score: " + appleeaten)) / 2,
                g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX=random.nextInt((int)(Screen_Width/Unit_size))*Unit_size;
        appleY=random.nextInt((int)(Screen_Height/Unit_size))*Unit_size;
    }

    public void move(){
        for(int i=bodyparts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        switch(direction){
            case 'U':y[0]-=Unit_size;break;
            case 'D':y[0]+=Unit_size;break;
            case 'L':x[0]-=Unit_size;break;
            case 'R':x[0]+=Unit_size;break;
        }
    }
    public void checkApple(){
        if((x[0]==appleX) && (y[0]==appleY)){
            bodyparts++;
            appleeaten++;
            newApple();
        }
    }
    
    public void checkCollisions(){
        for(int i=bodyparts;i>0;i--){
            if((x[0]==x[i]) && (y[0] == y[i])){
                running=false;
            }
        }
         if (x[0] < 0 || x[0] >= Screen_Width || y[0] < 0 || y[0] >= Screen_Height) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        // Score show karo
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + appleeaten,
            (Screen_Width- metrics1.stringWidth("Score: " + appleeaten)) / 2,
            g.getFont().getSize());

        // Game Over likho
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over",
            (Screen_Width - metrics2.stringWidth("Game Over")) / 2,
            Screen_Height / 2);
    }
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint(); // screen redraw karo
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') direction = 'D';
                    break;
            }
        }
    }
}

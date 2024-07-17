import java.util.Random;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener , KeyListener{

    private class Tile {
        int x;
        int y;
        
        Tile(int x ,int y){
            this.x=x;
            this.y=y;
        }
        
    }

    int boardHeight;
    int boardWidth;
    int tileSize =25;

    //sneak
    Tile sneakHead;
    ArrayList<Tile> sneakBody;

    //food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver =false;

    //construtor
    SnakeGame(int boardHeight,int boardWidth) {
       this.boardHeight=boardHeight;
       this.boardWidth=boardWidth;
       setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
       setBackground(Color.black);
       setFocusable(true);

       addKeyListener(this);

       sneakHead=new Tile(5, 5);
       sneakBody = new ArrayList<Tile>();

       food=new Tile(10, 10);

       random=new Random();
       placeFood(); 

       velocityX=0;
       velocityY=1;

       gameLoop =new Timer(100,this);
       gameLoop.start();
    }

    //methods below

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {

        //grid
        // for(int i=0;i<boardHeight/tileSize;i++)
        // {
        //     g.drawLine(i*tileSize,0,i*tileSize,boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize); 
        // }

        //food
        g.setColor(Color.red);
       // g.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);
        g.fill3DRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize,true);

        //snake
        g.setColor(Color.green);
       // g.fillRect(sneakHead.x*tileSize, sneakHead.y*tileSize,tileSize,tileSize);
        g.fill3DRect(sneakHead.x*tileSize, sneakHead.y*tileSize,tileSize,tileSize,true);

        //sneak body
        for(int i=0;i<sneakBody.size();i++)
        {
            Tile  sneakPart = sneakBody.get(i);
            //g.fillRect(sneakPart.x * tileSize, sneakPart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(sneakPart.x * tileSize, sneakPart.y * tileSize, tileSize, tileSize,true);
        }

        //game over and score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameOver)
        {
            g.setColor(Color.red);
            g.drawString("Game Over" + String.valueOf(sneakBody.size()),tileSize-16,tileSize);
        }
        else{
            g.drawString("Score " + String.valueOf(sneakBody.size()),tileSize-16,tileSize);
        }
    }

    private void placeFood() {
       food.x=random.nextInt(boardWidth/tileSize); //600/25 =24 (0-23)
       food.y=random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1 ,Tile tile2)
    {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    //this runn again and again
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if(gameOver)
        {
            gameLoop.stop();
        }
    }

    private void move() {

        if(collision(sneakHead, food))
        {
            sneakBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        for(int i=sneakBody.size()-1;i>=0;i--)
        {
            Tile snakePart = sneakBody.get(i);
            if(i==0)
            {
                snakePart.x=sneakHead.x;
                snakePart.y=sneakHead.y;
            }
            else{
                Tile preSnakePart = sneakBody.get(i-1);
                snakePart.x=preSnakePart.x;
                snakePart.y=preSnakePart.y;
            }
        }

        //sneak head
        sneakHead.x+=velocityX;
        sneakHead.y+=velocityY;

        for(int i=0;i<sneakBody.size();i++)
        {
            Tile checkPart = sneakBody.get(i);
            if(collision(sneakHead, checkPart))
            {
                gameOver=true;
            }
        }

        if(sneakHead.x*tileSize<0 || sneakHead.x*tileSize>boardWidth || sneakHead.y*tileSize<0 || sneakHead.y*tileSize>boardHeight)
        {
            gameOver=true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode()==KeyEvent.VK_UP && velocityY != 1)
       {
         velocityX=0;
         velocityY=-1;
       }
       else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY != -1)
       {
         velocityX=0;
         velocityY=1;
       }
       else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX != 1 )
       {
         velocityX=-1;
         velocityY=0;
       }
       else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX != -1)
       {
         velocityX=1;
         velocityY=0;
       }
       
    }

    //not use for this game
    @Override
    public void keyTyped(KeyEvent e) { 
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}

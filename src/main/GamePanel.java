package main;
/*import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;

import static java.awt.Color.white;



public class main.GamePanel extends JPanel implements Runnable{
    private static final int PWIDTH =500; //width of panel
    private static final int PHEIGHT = 400; //height of panel

    private Thread animator; //for animation
    private volatile boolean running = false; //stop animation

    private volatile boolean gameOver = false; //terminate game

    private static Graphics dbg;  //istead of global variable
    private static Image dbImage = null;

    public main.GamePanel(){
        setBackground(white); //white background
        setPreferredSize(new Dimension(PWIDTH,PHEIGHT));

        setFocusable(true);
        requestFocus(); //JPanel receives key events
        readyForTermination();
        //game components

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                testPress(e.getX(),e.getY());
                //super.mousePressed(e);
            }
        });
    }
    public void addNotify(){
        super.addNotify(); //creates peer
        startGame(); //start thread
    }
    private void startGame(){
        if(animator == null || !running){
            animator = new Thread(this);
            animator.start();
        }
    }
    private volatile boolean isPaused = false;
    public void pauseGame(){ isPaused = true;}
    public void stopGame(){running = false;} //called to stop execution

    private static final int NO_DELAYS_PER_YIELD = 16;
    public void run(){
        long beforeTime;
        long afterTime;
        long timeDiff;
        long sleepTime;
        long overSleepTime =0L;
        int noDelays = 0;
        long period=1000000L ;  //how much an iteration should take period = 10ms //initially of period type

        beforeTime = System.nanoTime();

        running = true;
        while(running){
            gameUpdate(); //game state is updated
            gameRender(); //render to buffer
            paintScreen(); //draw buffer to screen

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime; //time left in loop

            if (sleepTime > 0) { //some time left in cycle
                try {
                    Thread.sleep(sleepTime/1000000L); //(ms) if while running takes too long it will jump frames
                }
                catch (InterruptedException ex) {}
                overSleepTime = (System.nanoTime()-afterTime) -sleepTime;
            }
            else{ //fram took longer than period
                overSleepTime = 0L;

                if(++noDelays >= NO_DELAYS_PER_YIELD){
                    Thread.yield(); //give another thread a chance to run
                    noDelays = 0;
                }
            }
            beforeTime = System.nanoTime();
        }
        System.exit(0);
    }
    private void paintScreen(){
        Graphics g;
        try {
            g = this.getGraphics(); //get the panel graphics context
            if((g != null) && (dbImage != null))
                g.drawImage(dbImage,0,0,null);
            Toolkit.getDefaultToolkit().sync(); //sync the display
            g.dispose();
        }
        catch (Exception e){
            System.out.println("Graphics context error: " + e);
        }
    }
    private void readyForTermination(){
        addKeyListener(new KeyAdapter() {
            //listen for esc,q,end,ctrl-c
            public void KeyPressed(KeyEvent e){
                int keyCode = e.getKeyCode();
                if((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
                        (keyCode == KeyEvent.VK_END) || (keyCode == KeyEvent.VK_C) || e.isControlDown())
                {
                    running = false;
                }
            }
        });
    }
    private void testPress(int x,int y){
        //if x,y is important to the game
        if(!isPaused && !gameOver){
            //do smthg
        }
    }
    private void gameUpdate(){
        if(!isPaused && !gameOver) {
            //update
        }
    }

    //private static Graphics dbg;  //istead of global variable
    //private static Image dbImage = null;
    private void gameRender(){
        if(dbImage == null){
            dbImage = createImage(PWIDTH,PHEIGHT);
            if(dbImage == null){
                System.out.println("dbImage is null");
                return;
            }
            else
                dbg = dbImage.getGraphics();
        }
        //clear background
        dbg.setColor(white);
        dbg.fillRect(0,0,PWIDTH,PHEIGHT);

        //draw game elem

        if(gameOver){
            gameOverMessage(dbg);
        }
    }

    private void gameOverMessage(Graphics g){
        //code to calculate x and y
        String msg="game over";
        g.drawString(msg,0,0);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (dbImage != null)
            g.drawImage(dbImage, 0, 0, null);
    }
}
*/
import javax.swing.JPanel;
import java.awt.*;

import entity.*;
import object.SuperObject;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable{
     final int originalTileSize = 32; //32x32 tiles
     final int scale = 2; //scaling the tiles
    public final int tileSize = originalTileSize*scale; //pixeli per tile
    public final int maxScreenCol = 16;  //dim ecran joc privita prin nr tiles
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize*maxScreenCol;  //dim ecran pixeli
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 32;  //dim harta privita prin tiles
    public final int maxWorldRow = 24;
    //public final int worldWidth = tileSize * maxWorldCol; //dim harta pixeli
    //public final int worldHeight = tileSize * maxScreenRow;

    //FPS
    int FPS = 60;
    //system
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound(); //background music
    Sound se = new Sound(); //sound effect
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    //entity and object
    public Player player= new Player(this,keyH);
    public SuperObject[] obj = new SuperObject[10]; //we prepare 10 slots of object



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame() {
        aSetter.setObject();

        //playMusic(0); //background music
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while(gameThread != null) { //while gamethread exists run this
            //long currentTime = System.nanoTime();
            double drawInterval = 1000000000/FPS; //one interval per frame
            double nextDrawTime = System.nanoTime() + drawInterval;

            update();
            repaint(); //calls paintComponent

            double remainingTime = nextDrawTime - System.nanoTime(); //how much time remaining until next draw time
            remainingTime = remainingTime/1000000; //convert to milliseconds
            if(remainingTime < 0){ //it doesn't need sleep
                remainingTime = 0;
            }
            try {
                Thread.sleep((long) remainingTime);
                nextDrawTime = nextDrawTime + drawInterval;
            } catch (InterruptedException e){
                System.exit(0);
            }
        }
    }
    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //tiles
        Graphics2D g2 = (Graphics2D)g;
        //objects
        tileM.draw(g2);  //before player cuz they layer
        for(int i = 0;i < obj.length;i++){  //drawing all the objects
            if(obj[i] != null){ //make sure obj exists
                obj[i].draw(g2,this);
            }
        }
        //player
        player.draw(g2);

        //ui
        ui.draw(g2);

        g2.dispose();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i) { //sound effect
        se.setFile(i);
        se.play();
    }
}

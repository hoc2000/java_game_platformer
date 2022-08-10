package main;


import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;


import java.awt.*;

//main
@SuppressWarnings("FieldCanBeLocal")
public class Game implements Runnable {
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Playing playing;
    private Menu menu;



    public final static int TILES_DEFAULT_SIZE =32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE *SCALE); //kich thuoc 1 khoi nho(tiles)
    public final static int GAME_WIDTH = TILES_SIZE *TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE *TILES_IN_HEIGHT;


    public Game() {
        //khoi tao Player
        init_Classes();
        //tao panel
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true); // focus vao player move
        gamePanel.requestFocus();
        //chay loop
        startGameLoop();

    }
    //thanh phan can khoi tao class
    public void init_Classes(){
        menu = new Menu(this);
        playing = new Playing(this);


    }

    private void startGameLoop() {
        Thread gameloopThread = new Thread(this);
        gameloopThread.start();//tao 1 thread moi va dong thoi run
    }
    public void update(){

        switch (Gamestate.state){
            case MENU:
                menu.update();

                break;
            case PLAYING:
                playing.update();

                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics g){
        switch (Gamestate.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);

                break;
            default:
                break;
        }
    }
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime =System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0 ;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1){
                update(); //capnhat
                updates++;
                deltaU --;
            }
            if(deltaF>=1){
                gamePanel.repaint();//xuatDoHoa
                frames++;
                deltaF --;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + frames+" | UPS: " +updates);
                frames = 0;
                updates = 0;
            }
        }

    }

    public void widowFocusLost(){
        if (Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().StopWhenLostFocusPos();
        }

    }
    public Menu getMenu(){
        return menu;
    }

    public Playing getPlaying(){
        return playing;

    }
}

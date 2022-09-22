package demo;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.event.KeyEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class App extends PApplet {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;

    public int leftBound = 40;
    public int rightBound = 600;

    public static final int FPS = 60;

    public String configPath;
    public Random rand = new Random();
	
    //Initialises where the player starts - pixels x pixels y
    public Player p1 = new Player(305, 20);

    //sets their initial score, similar to lives
    public int p1score = 0;
    //Initalises player two starts- symetric to p1
    public Player p2 = new Player(305, 600);
    public int p2score = 0;
    public Ball ball;

    public int initialTextTimer = FPS*3;

    public Player[] players = new Player[] {p1, p2};

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    @Override
    public void setup() {
        frameRate(FPS);

        try {
            ball = new Ball(this,
                loadImage(URLDecoder.decode(this.getClass().getResource("ball.png").getPath(), StandardCharsets.UTF_8.toString()))
            );
        } catch (UnsupportedEncodingException e) {
            System.out.println("Could not locate resource file path for ball.png");
            e.printStackTrace();
        }

        // Load images during setup
		//this.grass = loadImage(this.getClass().getResource("grass.png").getPath());
        //this.concrete = loadImage(this.getClass().getResource("concrete_tile.png").getPath());
        //this.worm = loadImage(this.getClass().getResource("worm.png").getPath());
        //this.beetle = loadImage(this.getClass().getResource("beetle.png").getPath());
        
    }
	
    /**
     * Draw all elements in the game by current frame. 
    */
    @Override
    public void draw() {
        background(0);
        textSize(40);
        fill(255);
        if (p1score >= 5) {
            //Use text to display text
            text("Player 1 wins", WIDTH*0.2f, HEIGHT*0.4f);
            stop();
            return;
        }
        if (p2score >= 5) {
            text("Player 2 wins", WIDTH*0.2f, HEIGHT*0.4f);
            stop();
            return;
        }

        // this.color(5);
        //this.fill(255);
        this.rect(0, 0, leftBound, HEIGHT);
        this.rect(rightBound, 0, (WIDTH-rightBound), HEIGHT);

        p1.draw(this);
        p2.draw(this);

        if (initialTextTimer > 0) {
            this.fill(255);
            this.text("controls: a,d and arrow keys", 45, 200);
            initialTextTimer -= 1;
        } else {
            ball.draw(this);
        }
        

        //this.fill(this.color(255, 255, 255));
        this.stroke(0);
        this.fill(0);
        textSize(24);
        //This specifies position of p1score
        this.text(String.valueOf(p1score), 605, 40);
        this.text("P1", 5, 40);
        this.text(String.valueOf(p2score), 605, 600);
        this.text("P2", 5, 600);
    }

    /**
     * Key event triggered when any key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //System.out.println(key);
        if (key == 37) { //left arrow
            p2.left();
        } else if (key == 39) { //right arrow
            p2.right();
        } else if (key == 65) { //a
            p1.left();
        } else if (key == 68) { //d
            p1.right();
        }
    }

    /**
     * Key event triggered when any key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 37 || key == 39) {
            p2.stop();
        } else if (key == 65 || key == 68) {
            p1.stop();
        }
    }

    public static void main(String[] args) {
        PApplet.main("demo.App");
    }
}

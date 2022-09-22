package demo;

import processing.core.PApplet;

public class Player {
    private int x;
    private int y;

    private int xVel = 0;

    private int speed = 2;

    public static final int HEIGHT = 10;
    public int width = 40;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void left() {
        this.xVel = -speed;
    }

    public void right() {
        this.xVel = speed;
    }

    public void stop() {
        this.xVel = 0;
    }

    public void draw(App app) {
        app.rect(x,y,width, HEIGHT);
        if (x+xVel < app.leftBound || x+xVel+width > app.rightBound) {
            this.stop();
        }
        x += xVel;
    }
}

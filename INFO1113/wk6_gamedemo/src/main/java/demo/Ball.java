package demo;

import java.util.Random;

import processing.core.PImage;

public class Ball {
    
    private int x;
    private int y;

    private int width = 20;
    private int height = 20;

    private int xVel = 0;
    private int yVel = 0;

    private int velMult = 2;

    private PImage ballSprite;

    public Ball(App app, PImage ballSprite) {
        this.ballSprite = ballSprite;
        this.respawn(app);
    }

    public Ball(int x, int y, int xVel, int yVel, PImage ballSprite) {
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
        this.ballSprite = ballSprite;
    }

    public int draw(App app) {
        app.image(ballSprite, x, y);
        this.x += xVel;
        this.y += yVel;
        return this.collision(app);
    }

    public int collision(App app) {
        if (this.y < 0) {
            app.p2score += 1;
            this.respawn(app);
            return 2;
        }
        if (this.y+this.height > App.HEIGHT) {
            app.p1score += 1;
            this.respawn(app);
            return 1;
        }

        if (this.x < app.leftBound || this.x+this.width > app.rightBound) {
            this.xVel *= -1;
        }

        if (collides(this.x, this.y+velMult, app) ||
            collides(this.x, this.y+this.height-velMult-1, app) ||
            collides(this.x+this.width-1, this.y+velMult, app) ||
            collides(this.x+this.width-1, this.y+this.height-velMult-1, app)) {
                this.xVel *= -1;
                this.x = this.x+this.xVel*velMult*2;
        }
        if (collides(this.x+velMult, this.y, app) ||
            collides(this.x+this.width-1-velMult, this.y, app) ||
            collides(this.x+velMult, this.y+this.height-1, app) ||
            collides(this.x+this.width-1-velMult, this.y+this.height-1, app)) {
                this.yVel *= -1;
                this.y = this.y+this.yVel*velMult;
        }
        return 0;
    }

    public boolean collides(int xCoord, int yCoord, App app) {
        for (Player p : app.players) {
            if (xCoord > p.getX() && xCoord < p.getX()+p.width && yCoord > p.getY() && yCoord < p.getY()+Player.HEIGHT) {
                return true;
            }
        }
        return false;
    }

    public void respawn(App app) {
        this.x = app.rand.nextInt(app.rightBound-25-app.leftBound)+app.leftBound+5;
        this.y = app.rand.nextInt(20)+App.HEIGHT/2-20;
        this.xVel = (app.rand.nextInt(2)*2-1)*velMult;
        this.yVel = (app.rand.nextInt(2)*2-1)*velMult;
    }
}

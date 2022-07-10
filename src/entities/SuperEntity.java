package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class SuperEntity {

    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;

    public SuperEntity(float x , float y,int width,int height){
        this.x = x;
        this.y = y;
        this.width =width;
        this.height =height;


    }
    protected void initHitBox(float x, float y, int width, int height){
        hitBox = new Rectangle2D.Float(x,y,width, height);
    }

    protected void drawHitBox(Graphics g, int x){
        //debug hitbox
        g.setColor(Color.PINK);
        g.drawRect((int)hitBox.x,(int) hitBox.y, (int)hitBox.width, (int)hitBox.height);
    }





    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }
}

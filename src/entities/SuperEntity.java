package entities;

import java.awt.*;

public abstract class SuperEntity {
    protected float x,y;
    int width, height;
    protected Rectangle hitBox;

    public SuperEntity(float x , float y,int width,int height){
        this.x = x;
        this.y = y;
        this.width =width;
        this.height =height;

        initHitBox();
    }
    private void initHitBox(){
        hitBox = new Rectangle((int)x,(int)y,width,height);
    }

    protected void drawHitBox(Graphics g){
        //debug hitbox
        g.setColor(Color.RED);
        g.drawRect(hitBox.x,hitBox.y,hitBox.width,hitBox.height);
    }


    public void updateHitBox(){
        hitBox.x = (int) x ;
        hitBox.y = (int) y ;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}

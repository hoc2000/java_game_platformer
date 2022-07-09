package entities;
import static utilz.Constant.EnemyConstants.*;

public abstract class Enemy extends SuperEntity{
    private int aniIndex, enemyState;
    private int aniTick, aniSpeed = 25;

    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitBox(x,y,width,height);
    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick>=aniSpeed){
            aniTick =0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(enemyState)){
                aniIndex  = 0;
            }
        }
    }
    public void update(){
        updateAnimationTick();
    }
    public int getAniIndex(){
        return aniIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
}

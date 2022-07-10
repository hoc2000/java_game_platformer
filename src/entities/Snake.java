package entities;
import main.Game;

import static utilz.Constant.EnemyConstants.*;

public class Snake extends Enemy{
    public Snake(float x, float y, int width, int height) {
        super(x, y, SNAKE_WIDTH, SNAKE_HEIGHT);
        initHitBox(x,y,(int)(32* Game.SCALE),(int)(19*Game.SCALE));


    }
}

package entities;
import static utilz.Constant.EnemyConstants.*;

public class Snake extends Enemy{
    public Snake(float x, float y, int width, int height) {
        super(x, y, SNAKE_WIDTH, SNAKE_HEIGHT);
    }
}

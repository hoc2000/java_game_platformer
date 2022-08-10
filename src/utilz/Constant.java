package utilz;

import main.Game;

public class Constant {
    public static class EnemyConstants{
        public static final int SNAKE = 0;
        public static final int IDLE = 0;
        public static final int RUNNING = 2;
        public static final int ATTACK = 3;
        public static final int DEAD = 4;

        public static final int SNAKE_WIDTH_DEFAULT = 32;
        public static final int SNAKE_HEIGHT_DEFAULT = 32;

        public static final int SNAKE_WIDTH = (int) (SNAKE_WIDTH_DEFAULT*Game.SCALE*1.5);
        public static final int SNAKE_HEIGHT = (int)(SNAKE_HEIGHT_DEFAULT*Game.SCALE*1.5);
        public static int GetSpriteAmount( int enemy_state){
            return switch (enemy_state) {
                case IDLE -> 10;
                case RUNNING -> 6;
                case ATTACK -> 7;
                case DEAD -> 5;
                default -> 0;
            };
        }
    }
    public static class Environment{
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT* Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT* Game.SCALE);
    }
    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 10;
        public static final int RUNNING = 1;
        public static final int JUMP = 0;
        public static final int FALLING = 8;
        public static final int ATTACK = 2;
        public final static int DEAD =6;

        public static int GetSpriteAmount(int player_action) {
            return switch (player_action) {
                case RUNNING, ATTACK, DEAD -> 5;
                case IDLE, JUMP -> 1;
                case FALLING -> 3;
                default -> 0;
            };
        }
    }

}

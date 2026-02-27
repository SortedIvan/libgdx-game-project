package utils;

import com.badlogic.gdx.math.Vector2;

public class PlayerUtility {
    public static final int START_HEALTH = 100;
    public static final Vector2 INITIAL_SPAWN_POSITION = new Vector2(
        ScreenUtility.SCREEN_DIMENSION_X / 2, ScreenUtility.SCREEN_DIMENSION_Y / 2);
    public static final Vector2 INITIAL_FACING_DIRECTION = new Vector2(1, 1);
}

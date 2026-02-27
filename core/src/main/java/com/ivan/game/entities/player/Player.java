package com.ivan.game.entities.player;
import com.badlogic.gdx.math.Vector2;
import com.ivan.game.entities.Entity;
import utils.PlayerUtility;


public class Player extends Entity {
    private Vector2 position;
    private Vector2 facingDirection;
    private int health;

    @Override
    public void initialize() {
        health = PlayerUtility.START_HEALTH;
        position = PlayerUtility.INITIAL_SPAWN_POSITION;
        facingDirection = PlayerUtility.INITIAL_FACING_DIRECTION;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }

    private void followMouse() {

    }
}

package com.ivan.game.entities.player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.ivan.game.entities.Entity;
import utils.PlayerUtility;


public class Player extends Entity {
    private ShapeRenderer renderer;
    private SpriteBatch batch;

    private Vector2 position;
    private Vector2 facingDirection;
    private int health;
    private Color playerColor;
    private int playerSpeed = 100;
    private float acceleration = 0;
    private float accelerateBy = 0.2f;
    private float maxAcceleration = 2;

    private final Vector2 playerSize = new Vector2(30, 30);

    @Override
    public void initialize(ShapeRenderer renderer, SpriteBatch batch) {
        this.renderer = renderer;
        this.batch = batch;

        health = PlayerUtility.START_HEALTH;
        // offset so the player is perfectly centered initially
        position = new Vector2(
            PlayerUtility.INITIAL_SPAWN_POSITION.x - playerSize.x / 2,
            PlayerUtility.INITIAL_SPAWN_POSITION.y - playerSize.y / 2
        );
        facingDirection = PlayerUtility.INITIAL_FACING_DIRECTION;
        playerColor = new Color(196, 180, 84, 100);
    }

    @Override
    public void update(float deltaTime) {
        followMouse();
        var displacement = getInputForMovement(deltaTime);
        move(displacement);
    }

    @Override
    public void draw() {
        drawPlayer();
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void move(Vector2 displacement) {
        this.position = new Vector2(position.x + displacement.x,
            position.y + displacement.y);
    }

    @Override
    public void move(Vector2 displacement, float deltaTime) {

    }

    private void drawPlayer() {
        renderer.begin(ShapeType.Filled); //I'm using the Filled ShapeType, but remember you have three of them
        renderer.rect(position.x, position.y, playerSize.x, playerSize.y,
                      playerColor,playerColor,playerColor,playerColor);
        renderer.end();
    }

    private void followMouse() {

    }

    private void accelerate() {
        if (acceleration >= maxAcceleration) {
            acceleration = maxAcceleration;
            return;
        }

        acceleration += accelerateBy;
    }

    private void deAccelerate() {
        if (acceleration - accelerateBy <= 1) {
            acceleration = 1;
            return;
        }

        acceleration -= accelerateBy;
    }

    private Vector2 getInputForMovement(float dt) {
        Vector2 displacement = new Vector2(0,0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            displacement.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            displacement.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            displacement.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            displacement.x += 1;
        }

        if (displacement.x != 0 || displacement.y != 0) {
            accelerate();
        }
        else {
            deAccelerate();
        }

        // Normalize first to avoid diagonal movement being faster
        displacement.nor();

        displacement.x = displacement.x * dt * playerSpeed * acceleration;
        displacement.y = displacement.y * dt * playerSpeed * acceleration;

        return displacement;
    }
}

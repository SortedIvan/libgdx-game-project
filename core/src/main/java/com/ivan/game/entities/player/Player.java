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
import utils.Matrix2;

public class Player extends Entity {
    private ShapeRenderer renderer;
    private SpriteBatch batch;

    private Vector2 position;
    private Vector2 facingDirection;
    private Vector2 mouseIndicatorLeftNormal;
    private Vector2 mouseIndicatorRightNormal;
    private Vector2 mouseIndicatorLeftPos;
    private Vector2 mouseIndicatorRightPos;
    private Vector2 mouseIndicatorStartPos;
    private boolean followMouseDir = true;

    private final float indicatorLength = 10.f;
    private final float indicatorSizeAwayFromPlayer = 50.f;

    private int prevMousPosX = 0;
    private int prevMousPosY = 0;

    private int prevPlayerPosX = 0;
    private int prevPlayerPosY = 0;

    private int health;
    private Color playerColor;
    private int playerSpeed = 40;
    private float acceleration = 1;
    private float accelerateBy = 3.5f;
    private float maxAcceleration = 10;
    private float accelerationFraction = 0.2f;
    private float playerSizeRadius = 30.f;

    @Override
    public void initialize(ShapeRenderer renderer, SpriteBatch batch) {
        this.renderer = renderer;
        this.batch = batch;

        health = PlayerUtility.START_HEALTH;
        // offset so the player is perfectly centered initially
        position = new Vector2(
            PlayerUtility.INITIAL_SPAWN_POSITION.x - playerSizeRadius / 2,
            PlayerUtility.INITIAL_SPAWN_POSITION.y - playerSizeRadius / 2
        );
        facingDirection = PlayerUtility.INITIAL_FACING_DIRECTION;
        playerColor = new Color(196, 180, 84, 100);

        mouseIndicatorLeftNormal = new Vector2(-1, 0).rotateDeg(30);
        mouseIndicatorRightNormal = new Vector2(1, 0).rotateDeg(-30);
        mouseIndicatorLeftPos = new Vector2(0,0);
        mouseIndicatorRightPos = new Vector2(0,0);
        mouseIndicatorStartPos = new Vector2(0,0);
    }

    @Override
    public void update(float deltaTime) {
        var displacement = getInputForMovement(deltaTime);
        move(displacement);
        followMouse();
    }

    @Override
    public void draw() {
        drawPlayer();
        drawMouseIndicator();
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void move(Vector2 displacement) {
        prevPlayerPosX = (int)position.x;
        prevPlayerPosY = (int)position.y;

        this.position = new Vector2(position.x + displacement.x,
            position.y + displacement.y);

        this.mouseIndicatorStartPos = mouseIndicatorStartPos.add(displacement);
        this.mouseIndicatorLeftPos = mouseIndicatorLeftPos.add(displacement);
        this.mouseIndicatorRightPos = mouseIndicatorRightPos.add(displacement);
    }

    @Override
    public void move(Vector2 displacement, float deltaTime) {

    }

    private void drawPlayer() {
        renderer.begin(ShapeType.Filled);
        renderer.setColor(playerColor);
        renderer.circle(position.x, position.y, playerSizeRadius);
        renderer.end();
    }

    private void drawMouseIndicator() {
        renderer.begin(ShapeType.Line);
        renderer.setColor(playerColor);
        renderer.line(mouseIndicatorStartPos, mouseIndicatorLeftPos);
        renderer.line(mouseIndicatorStartPos, mouseIndicatorRightPos);
        renderer.end();
    }

    private void followMouse() {
        if (!followMouseDir) {
            return;
        }

        int currMousePosX = Gdx.input.getX();
        int currMousePosY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (currMousePosX == prevMousPosX && currMousePosY == prevMousPosY) {
            return; // no need to change anything
        }

        prevMousPosX = currMousePosX;
        prevMousPosY = currMousePosY;

        Vector2 mousePosVec = new Vector2(currMousePosX, currMousePosY);
        Vector2 dirVecFromPlayerToMouse = mousePosVec.sub(position).nor();
        Vector2 positionForIndicator = new Vector2(
            dirVecFromPlayerToMouse.x * indicatorSizeAwayFromPlayer + position.x,
            dirVecFromPlayerToMouse.y * indicatorSizeAwayFromPlayer + position.y
        );

        this.facingDirection = dirVecFromPlayerToMouse;
        this.mouseIndicatorStartPos = positionForIndicator;

        Matrix2 transformForIndicators = getTransformMatrixOfPositionIndicator(dirVecFromPlayerToMouse);
        var leftIndicator = transformForIndicators.applyTransform(mouseIndicatorLeftNormal);
        var rightIndicator = transformForIndicators.applyTransform(mouseIndicatorRightNormal);

        mouseIndicatorLeftPos = new Vector2(mouseIndicatorStartPos).mulAdd(leftIndicator, indicatorLength);
        mouseIndicatorRightPos = new Vector2(mouseIndicatorStartPos).mulAdd(rightIndicator, indicatorLength);

    }

    private Matrix2 getTransformMatrixOfPositionIndicator(Vector2 dir) {
        return new Matrix2(
            new Vector2(dir).rotateDeg(-90),
            dir
        );
    }

    private void accelerate() {
        if (acceleration >= maxAcceleration) {
            acceleration = maxAcceleration;
            return;
        }

        acceleration = lerpAcceleration(acceleration);
    }

    private float lerpAcceleration(float acceleration) {
        acceleration += (maxAcceleration - acceleration) * accelerationFraction;
        return acceleration;
    }

    private void deAccelerate() {
        if (acceleration <= 1) {
            acceleration = 1;
            return;
        }

        acceleration += (1 - acceleration) * accelerationFraction;
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

package com.ivan.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected int entityId;
    public abstract void initialize(ShapeRenderer renderer, SpriteBatch batch);
    public abstract void update(float deltaTime);
    public abstract void draw();

    public abstract Vector2 getPosition();
    public abstract void move(Vector2 displacement);
    public abstract void move(Vector2 displacement, float deltaTime);

    public void setEntityId(int id) {
        this.entityId = id;
    }
}

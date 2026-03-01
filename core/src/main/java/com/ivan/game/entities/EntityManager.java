package com.ivan.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EntityManager {
    // CPU cache hitting is whenever you have a long consecutive array of the same type
    // Resizing the list dynamically is not optimal
    private SpriteBatch batch;
    private ShapeRenderer renderer;

    private List<Entity> entities;
    private Stack<Integer> freeIds;

    private final int ENTITY_RESIZE_CHUNK = 100;
    private int entityIdCounter = 0;

    public EntityManager(SpriteBatch batch, ShapeRenderer renderer) {
        entities = new ArrayList<Entity>();
        freeIds = new Stack<>();

        this.batch = batch;
        this.renderer = renderer;
    }

    public void addEntityToList(Entity entity) {
        int entityId = 0;

        if (!freeIds.empty()) {
            entityId = freeIds.pop();
        }
        else {
            entityId = entityIdCounter++;
        }

        entity.setEntityId(entityId);
        entity.initialize(renderer, batch);

        if (entities.size() <= entityId) {
            resize();
        }

        entities.set(entityId, entity);
    }

    public void removeEntityFromList(int entityId) {
        if (entityId < 0 || entities.size() <= entityId){
            return;
        }

        entities.set(entityId, null);
        freeIds.add(entityId);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    private void resize() {
        for (int i = 0; i < ENTITY_RESIZE_CHUNK; ++i) {
            entities.add(null);
        }
    }
}

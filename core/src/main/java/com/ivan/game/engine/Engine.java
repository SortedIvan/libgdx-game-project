package com.ivan.game.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ivan.game.entities.EntityManager;
import com.ivan.game.entities.player.Player;

public class Engine {
    private EntityManager entityManager;
    private SpriteBatch batch;
    private ShapeRenderer renderer;

    public Engine(SpriteBatch batch, ShapeRenderer renderer) {
        this.batch = batch;
        this.renderer = renderer;

        entityManager = new EntityManager(batch, renderer);

        initializePlayer();
    }

    public void create() {

    }

    public void draw() {
        for (int i = 0; i < entityManager.getEntities().size(); ++i) {
            var entity = entityManager.getEntities().get(i);

            if (entity != null) {
                entity.draw();
            }
        }
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entityManager.getEntities().size(); ++i) {
            var entity = entityManager.getEntities().get(i);

            if (entity != null) {
                entity.update(deltaTime);
            }
        }
    }

    private void initializePlayer() {
        entityManager.addEntityToList(
            new Player()
        );
    }
}

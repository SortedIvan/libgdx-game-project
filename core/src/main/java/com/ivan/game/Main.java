package com.ivan.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ivan.game.engine.Engine;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer renderer;

    private Engine engine;

    @Override
    public void create() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        engine = new Engine(batch, renderer);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // organize code into three methods
        //input();
        //logic();
        //draw();
        engine.update(deltaTime);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        engine.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

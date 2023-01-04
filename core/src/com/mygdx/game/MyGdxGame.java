package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.mygdx.managers.GameManager;
import com.mygdx.managers.InputManager;
import com.mygdx.managers.TextManager;

public class MyGdxGame extends Game {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private float width;
    private float height;

    @Override
    public void create() {
        width = (float) Gdx.graphics.getWidth();
        height = (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false);
        batch = new SpriteBatch();
        TextManager.initialize(width, height);
        GameManager.initialize(width, height);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        batch.setProjectionMatrix(camera.combined);
        InputManager.handleInput(camera);
        batch.begin();
        GameManager.renderGame(batch);
        TextManager.renderText(batch);
        batch.end();

        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();

        if (Gdx.input.isKeyJustPressed(Keys.F)) {
            if (Gdx.graphics.isFullscreen()) {
                System.out.println("going windowed");
                Gdx.graphics.setWindowedMode(500, 500);
            } else {
                System.out.println("going fullscreen");
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        // TODO - gamemanager
        GameManager.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}

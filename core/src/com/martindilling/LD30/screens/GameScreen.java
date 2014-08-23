package com.martindilling.LD30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.martindilling.LD30.gameworld.GameRenderer;
import com.martindilling.LD30.gameworld.GameWorld;
import com.martindilling.LD30.helpers.InputHandler;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.screens
 * Author:  Martin
 * Date:    23-08-2014
 */
public class GameScreen implements Screen
{
    private GameWorld world;
    private GameRenderer renderer;

    public GameScreen() {
        Gdx.app.log("GameScreen", "Constructed");

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointY);
        renderer = new GameRenderer(world);

        Gdx.input.setInputProcessor(new InputHandler(world.getBall()));
    }

    @Override
    public void render(float delta) {
//        Gdx.app.log("GameScreen FPS", (1/delta) + "");

        world.update(delta);
        renderer.render();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resize() called");

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show() called");

    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide() called");

    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause() called");

    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume() called");

    }

    @Override
    public void dispose() {
        Gdx.app.log("GameScreen", "dispose() called");

    }
}

package com.martindilling.LD30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.martindilling.LD30.LD30;
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
    private final Rectangle pauseBounds;

    protected LD30 game;
    private GameWorld world;
    private GameRenderer renderer;

    public GameScreen(LD30 game) {
        this.game = game;
        Gdx.app.log("GameScreen", "Constructed");

        LD30.state = LD30.GAME_RUNNING;

        world = new GameWorld();
        renderer = new GameRenderer(world);

        pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);

        Gdx.input.setInputProcessor(new InputHandler(world.getBall(), renderer));
    }

    @Override
    public void render(float delta) {
//        Gdx.app.log("GameScreen FPS", (1/delta) + "");

        switch (LD30.state) {
            case LD30.GAME_READY:
                updateReady();
                break;
            case LD30.GAME_RUNNING:
                updateGame(delta);
                break;
            case LD30.GAME_PAUSED:
                pauseGame();
                break;
            case LD30.GAME_OVER:
                gameOver();
                break;
        }
    }

    private void updateReady () {
        renderer.render();
        if (Gdx.input.justTouched()) {
            LD30.state = LD30.GAME_RUNNING;
        }
    }

    private void updateGame(float delta) {
        world.update(delta);
        renderer.render();
    }

    private void pauseGame() {


    }

    private void gameOver() {

    }

    public void startGame() {
        if (LD30.state == LD30.GAME_READY) {
            LD30.state = LD30.GAME_RUNNING;
        }
    }


    @Override
    public void dispose() {
        Gdx.app.log("GameScreen", "dispose() called");
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
}

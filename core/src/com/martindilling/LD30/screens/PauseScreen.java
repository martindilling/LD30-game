package com.martindilling.LD30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.buttons.ExitButton;
import com.martindilling.LD30.buttons.StartGameButton;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.screens
 * Author:  Martin
 * Date:    25-08-2014
 */
public class PauseScreen implements Screen
{
    private final LD30 game;
    protected Stage stage;

    public PauseScreen(LD30 game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(1024, 640));
    }

    @Override
    public void render(float delta) {
        Gdx.app.log("GameScreen", "Constructed");

//        Gdx.input.setInputProcessor(new InputHandler(world.getBall(), renderer));

        stage.act(delta);

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        StartGameButton startGameButton = new StartGameButton(game);
        startGameButton.setTouchable(Touchable.enabled);
        startGameButton.setXY(30, 550);
        stage.addActor(startGameButton);

        ExitButton exitButton = new ExitButton(game);
        exitButton.setTouchable(Touchable.enabled);
        exitButton.setXY(30, 500);
        stage.addActor(exitButton);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

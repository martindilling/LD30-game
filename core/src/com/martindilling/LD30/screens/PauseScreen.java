package com.martindilling.LD30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.buttons.ExitButton;
import com.martindilling.LD30.buttons.StartButton;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.screens
 * Author:  Martin
 * Date:    25-08-2014
 */
public class PauseScreen extends BaseScreen
{
    public PauseScreen(LD30 game) {
        super(game);
    }

    @Override
    public void render(float delta) {
//        Gdx.input.setInputProcessor(new InputHandler(world.getBall(), renderer));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        StartButton startButton = new StartButton(game);
        startButton.setTouchable(Touchable.enabled);
        startButton.setXY(30, 550);
        stage.addActor(startButton);

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

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    protected InputAdapter InputHandler() {
        return null;
    }

    @Override
    public void dispose() {

    }
}

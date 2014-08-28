package com.martindilling.LD30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.assets.Assets;
import com.martindilling.LD30.loaders.Screens;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.screens
 * Author:  Martin
 * Date:    25-08-2014
 */
public class StartScreen extends BaseScreen
{
    private final ImageButton startButton;
    private final ImageButton helpButton;
    private final ImageButton quitButton;

    public StartScreen(LD30 game) {
        super(game);

        startButton = CreateStartButton(96, 512);
        helpButton = CreateHelpButton(384, 512);
        quitButton = CreateQuitButton(672, 512);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        DrawBackground();
        DrawBorder();
        DrawHeader();
        batch.end();

        stage.addActor(startButton);
        stage.addActor(helpButton);
        stage.addActor(quitButton);
        stage.draw();
    }

    @Override
    public void show() {
        super.show();


        StartMusic();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void hide() {
        super.hide();

    }

    @Override
    public void pause() {
        super.pause();

    }

    @Override
    public void resume() {
        super.resume();

    }

    @Override
    public void dispose() {
        super.dispose();

    }

    protected InputAdapter InputHandler() {
        return new InputAdapter()
        {
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        Gdx.app.exit();
                        break;
                }
                return true;
            }
        };
    }

    private void DrawHeader() {
        batch.draw(Assets.instance.images.logoPastel, 96, 96);
        batch.draw(Assets.instance.images.logoBlocks, 192, 288);
    }

    private ImageButton CreateStartButton(float x, float y) {
        ImageButton button = CreateImageButton(Assets.instance.images.btnStart, Assets.instance.images.btnStartHover);
        button.setPosition(x, y);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                        LD30.log("Goto Screen", "Level");
                        game.setScreen(Screens.level);
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                    }
                }
        );

        return button;
    }

    private ImageButton CreateHelpButton(float x, float y) {
        ImageButton button = CreateImageButton(Assets.instance.images.btnHelp, Assets.instance.images.btnHelpHover);
        button.setPosition(x, y);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                        LD30.log("Goto Screen", "Help");
                        game.setScreen(Screens.help);
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                    }
                }
        );

        return button;
    }

    private ImageButton CreateQuitButton(float x, float y) {
        ImageButton button = CreateImageButton(Assets.instance.images.btnQuit, Assets.instance.images.btnQuitHover);
        button.setPosition(x, y);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.exit();
                    }

                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                    }
                }
        );

        return button;
    }

}

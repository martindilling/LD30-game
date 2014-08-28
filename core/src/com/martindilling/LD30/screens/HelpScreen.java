package com.martindilling.LD30.screens;

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
public class HelpScreen extends BaseScreen
{
    private final ImageButton backButton;

    public HelpScreen(LD30 game) {
        super(game);

        backButton = CreateBackButton(384, 512);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        DrawBackground();
        DrawBorder();
        DrawHeader();
        batch.end();

        stage.addActor(backButton);
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
//                        LD30.log("Goto Screen", "Start");
                        game.setScreen(Screens.start);
                        break;
                }
                return true;
            }
        };
    }

    private void DrawHeader() {
        batch.draw(Assets.instance.images.info, 96, 96);
    }

    private ImageButton CreateBackButton(float x, float y) {
        ImageButton button = CreateImageButton(Assets.instance.images.btnBack, Assets.instance.images.btnBackHover);
        button.setPosition(x, y);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        LD30.log("Goto Screen", "Level");
//                        LD30.state = LD30.GAME_READY;
                        game.setScreen(Screens.start);
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

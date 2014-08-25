package com.martindilling.LD30.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.screens.GameScreen;

public class StartGameButton extends AbstractButton
{
    public StartGameButton(LD30 game) {
        super(game);
        textureDefault = new Texture("buttons/start_normal.png");
        textureHover   = new Texture("buttons/start_hover.png");
        texturePressed = new Texture("buttons/start_hover.png");

        Init();
    }

    protected void action(float delta) {
        game.setScreen(new GameScreen(game));
    }
}

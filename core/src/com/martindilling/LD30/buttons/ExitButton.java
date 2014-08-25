package com.martindilling.LD30.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.martindilling.LD30.LD30;

public class ExitButton extends AbstractButton
{
    public ExitButton(LD30 game) {
        super(game);
        textureDefault = new Texture("buttons/quit_normal.png");
        textureHover   = new Texture("buttons/quit_hover.png");
        texturePressed = new Texture("buttons/quit_hover.png");

        Init();
    }

    protected void action(float delta) {
        Gdx.app.exit();
    }
}

package com.martindilling.LD30;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.martindilling.LD30.screens.GameScreen;

/**
 * Project: LD30
 * Package: com.martindilling.LD30
 * Author:  Martin
 * Date:    23-08-2014
 */
public class LD30 extends Game
{
    @Override
    public void create() {
        Gdx.app.log("LD30", "Created");
        setScreen(new GameScreen());
    }
}

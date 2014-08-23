package com.martindilling.LD30;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.martindilling.LD30.helpers.AssetLoader;
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
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}

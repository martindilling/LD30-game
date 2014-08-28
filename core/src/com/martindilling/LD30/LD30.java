package com.martindilling.LD30;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Json;
import com.martindilling.LD30.assets.Assets;
import com.martindilling.LD30.loaders.Screens;
import com.martindilling.LD30.preferences.LevelStatus;

import java.util.logging.Level;

/**
 * Project: LD30
 * Package: com.martindilling.LD30
 * Author:  Martin
 * Date:    23-08-2014
 */
public class LD30 extends Game
{
    public static final int GAME_READY = 0;
    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 2;
    public static final int GAME_OVER = 3;
    public static final int GAME_COMPLETE = 4;

    public static int state;

    public LevelStatus levelStatus = new LevelStatus();

    public int width;
    public int height;
    private Preferences preferences;

    @Override
    public void create() {
        preferences = Gdx.app.getPreferences("LD30SaveData");
        load();


        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
//        AssetLoader.load();
        Assets.instance.init(new AssetManager());
        Screens.load(this);

        state = GAME_READY;

//        setScreen(new GameScreen(this));
        setScreen(Screens.start);
    }

    public static void log(String key, String value) {
        Gdx.app.log(key, value);
    }

    public void levelDone(int activeLevel) {
        switch (activeLevel) {
            case 1:
                levelStatus.done01 = true;
                break;
            case 2:
                levelStatus.done02 = true;
                break;
            case 3:
                levelStatus.done03 = true;
                break;
            case 4:
                levelStatus.done04 = true;
                break;
            case 5:
                levelStatus.done05 = true;
                break;
            case 6:
                levelStatus.done06 = true;
                break;
        }
        save();
    }

    public void save() {
        Json json = new Json();
        log("Saving String to LevelStatus", json.toJson(levelStatus));
        preferences.putString("LevelStatus", json.toJson(levelStatus));
        preferences.flush();
    }

    public void load() {
        Json json = new Json();
        levelStatus = json.fromJson(LevelStatus.class, preferences.getString("LevelStatus", "{}"));
        log("Loading String to LevelStatus", preferences.getString("LevelStatus", "{}"));


    }

    @Override
    public void render() {
        super.render();


    }




    @Override
    public void dispose() {
        super.dispose();
//        AssetLoader.dispose();
        Assets.instance.dispose();
        Screens.dispose();
    }
}

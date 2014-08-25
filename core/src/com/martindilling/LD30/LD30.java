package com.martindilling.LD30;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.martindilling.LD30.helpers.AssetLoader;
import com.martindilling.LD30.screens.GameScreen;
import com.martindilling.LD30.screens.StartScreen;

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

    public static int state;

    @Override
    public void create() {
        Gdx.app.log("LD30", "Created");
        AssetLoader.load();

        state = GAME_READY;

        setScreen(new GameScreen(this));
//        setScreen(new StartScreen(this));
    }

    @Override
    public void render() {
        super.render();



//        switch (state) {
//            case GAME_READY:
//                updateReady();
//                break;
//            case GAME_RUNNING:
//                updateGame();
//                break;
//            case GAME_PAUSED:
//                pauseGame();
//                break;
//            case GAME_OVER:
//                gameOver();
//                break;
//        }
    }

//    private void updateReady () {
//        if (Gdx.input.justTouched()) {
//            state = GAME_RUNNING;
//        }
//    }
//
//    private void updateGame(float delta) {
//        runTime += delta;
//        world.update(delta);
//        renderer.render(runTime);
//    }
//
//    private void pauseGame() {
//
//
//    }
//
//    private void gameOver() {
//
//    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}

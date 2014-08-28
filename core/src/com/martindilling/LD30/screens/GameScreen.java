package com.martindilling.LD30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.gameworld.GameRenderer;
import com.martindilling.LD30.gameworld.GameWorld;
import com.martindilling.LD30.helpers.InputHandler;
import com.martindilling.LD30.loaders.Screens;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.screens
 * Author:  Martin
 * Date:    23-08-2014
 */
public class GameScreen extends BaseScreen
{

    public LD30 game;
    public GameWorld world;
    public GameRenderer renderer;

    public GameScreen(LD30 game) {
        super(game);
//        LD30.log("GameScreen", "Constructed");
        this.game = game;

        LD30.state = LD30.GAME_READY;

        world = new GameWorld(this);
        renderer = new GameRenderer(this);

//        pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);

//        Gdx.input.setInputProcessor(new InputHandler(world.getBall(), renderer));
    }

    public void loadLevel(int level) {
        renderer.loadMap(level);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        Gdx.app.log("GameScreen FPS", (1/delta) + "");

        switch (LD30.state) {
            case LD30.GAME_READY:
                updateReady();
                break;
            case LD30.GAME_RUNNING:
                updateGame(delta);
                break;
            case LD30.GAME_PAUSED:
                pauseGame();
                break;
            case LD30.GAME_OVER:
                gameOver();
                break;
            case LD30.GAME_COMPLETE:
                gameComplete();
                break;
        }
    }

    public void restartLevel() {
        renderer.resetMap();
    }

    private void gameComplete() {

    }

    private void updateReady () {
        renderer.render();
        stage.draw();
//        if (Gdx.input.justTouched()) {
//            LD30.state = LD30.GAME_RUNNING;
//            LD30.log("State", "Starting...");
//        }
    }

    private void updateGame(float delta) {
        world.update(delta);
        renderer.render();
        stage.draw();
    }

    private void pauseGame() {


    }

    private void gameOver() {
        LD30.state = LD30.GAME_OVER;
        renderer.resetMap();
    }

    public void startGame() {
        if (LD30.state == LD30.GAME_READY) {
            LD30.log("State", "Starting...");
            PlayStartLevelSound();
            LD30.state = LD30.GAME_RUNNING;
        }
    }


    @Override
    public void dispose() {
        super.dispose();
//        Gdx.app.log("GameScreen", "dispose() called");
    }

    @Override
    protected InputAdapter InputHandler() {
        return new InputAdapter()
        {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode)
                {
                    case Input.Keys.ESCAPE:
//                        LD30.log("Goto Screen", "Level");
                        game.setScreen(Screens.level);
                        break;
                    case Input.Keys.SPACE:
                        if (LD30.state == LD30.GAME_RUNNING)
                            renderer.invert();
                        break;
                    case Input.Keys.LEFT:
                        GameReady();
                        if (LD30.state == LD30.GAME_RUNNING)
                            world.getBall().startMoving("left");
                        break;
                    case Input.Keys.RIGHT:
                        GameReady();
                        if (LD30.state == LD30.GAME_RUNNING)
                            world.getBall().startMoving("right");
                        break;
                    case Input.Keys.UP:
                        GameReady();
                        if (LD30.state == LD30.GAME_RUNNING)
                            world.getBall().startMoving("up");
                        break;
                    case Input.Keys.DOWN:
                        GameReady();
                        if (LD30.state == LD30.GAME_RUNNING)
                            world.getBall().startMoving("down");
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode)
                {
                    case Input.Keys.LEFT:
                        if (LD30.state == LD30.GAME_RUNNING)
                            world.getBall().stopMoving();
                        break;
                    case Input.Keys.RIGHT:
                        if (LD30.state == LD30.GAME_RUNNING)
                            world.getBall().stopMoving();
                        break;
                    case Input.Keys.UP:
                        if (LD30.state == LD30.GAME_RUNNING)
                            world.getBall().stopMoving();
                        break;
                    case Input.Keys.DOWN:
                        if (LD30.state == LD30.GAME_RUNNING)
                            world.getBall().stopMoving();
                        break;
                }
                return true;
            }
        };
    }

    public void GameReady() {
        if (LD30.state == LD30.GAME_READY) {
            PlayStartLevelSound();
            LD30.state = LD30.GAME_RUNNING;
        }
    }
}

package com.martindilling.LD30.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.gameobjects.Ball;
import com.martindilling.LD30.gameworld.GameRenderer;
import com.martindilling.LD30.screens.GameScreen;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.helpers
 * Author:  Martin
 * Date:    23-08-2014
 */
public class InputHandler implements InputProcessor
{
    private Ball ball;
    private GameRenderer renderer;

    public InputHandler(Ball ball, GameRenderer renderer) {
        this.ball = ball;
        this.renderer = renderer;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode)
        {
            case Keys.ESCAPE:
//                Gdx.app.log("InputHandler", "Keys.SPACE pressed");
                LD30.state = LD30.GAME_PAUSED;
                break;
            case Keys.SPACE:
//                Gdx.app.log("InputHandler", "Keys.SPACE pressed");
                startGame();
                renderer.invert();
                break;
            case Keys.LEFT:
//                Gdx.app.log("InputHandler", "Keys.LEFT pressed");
                startGame();
                ball.startMoving("left");
                break;
            case Keys.RIGHT:
//                Gdx.app.log("InputHandler", "Keys.RIGHT pressed");
                startGame();
                ball.startMoving("right");
                break;
            case Keys.UP:
//                Gdx.app.log("InputHandler", "Keys.UP pressed");
                startGame();
                ball.startMoving("up");
                break;
            case Keys.DOWN:
//                Gdx.app.log("InputHandler", "Keys.DOWN pressed");
                startGame();
                ball.startMoving("down");
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode)
        {
            case Keys.LEFT:
//                Gdx.app.log("InputHandler", "Keys.LEFT released");
                ball.stopMoving();
                break;
            case Keys.RIGHT:
//                Gdx.app.log("InputHandler", "Keys.RIGHT released");
                ball.stopMoving();
                break;
            case Keys.UP:
                Gdx.app.log("InputHandler", "Keys.UP released");
                ball.stopMoving();
                break;
            case Keys.DOWN:
                Gdx.app.log("InputHandler", "Keys.DOWN released");
                ball.stopMoving();
                break;
        }
        return true;
    }

    public void startGame() {
        if (LD30.state == LD30.GAME_READY) {
            LD30.state = LD30.GAME_RUNNING;
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

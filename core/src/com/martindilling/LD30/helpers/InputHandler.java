package com.martindilling.LD30.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.martindilling.LD30.gameobjects.Ball;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.helpers
 * Author:  Martin
 * Date:    23-08-2014
 */
public class InputHandler implements InputProcessor
{
    private Ball ball;

    public InputHandler(Ball ball) {
        this.ball = ball;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ball.onClick();
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode)
        {
            case Keys.LEFT:
                Gdx.app.log("InputHandler", "Keys.LEFT pressed");
                ball.startMoving("left");
                break;
            case Keys.RIGHT:
                Gdx.app.log("InputHandler", "Keys.RIGHT pressed");
                ball.startMoving("right");
                break;
            case Keys.UP:
                Gdx.app.log("InputHandler", "Keys.UP pressed");
                ball.startMoving("up");
                break;
            case Keys.DOWN:
                Gdx.app.log("InputHandler", "Keys.DOWN pressed");
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
                Gdx.app.log("InputHandler", "Keys.LEFT released");
                ball.stopMoving();
                break;
            case Keys.RIGHT:
                Gdx.app.log("InputHandler", "Keys.RIGHT released");
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

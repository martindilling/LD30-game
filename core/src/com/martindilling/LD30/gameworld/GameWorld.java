package com.martindilling.LD30.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.gameobjects.Ball;
import com.martindilling.LD30.screens.GameScreen;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.gameworld
 * Author:  Martin
 * Date:    23-08-2014
 */
public class GameWorld
{
    public int width = Gdx.graphics.getWidth();
    public int height = Gdx.graphics.getHeight();
    private Ball ball;
    private boolean inverted = false;

    public GameWorld(GameScreen screen) {
        LD30.log("Game", "Creating Ball");
        ball = new Ball(512-8, 320-8, 16, 16);
    }

//    public void se

    public void update(float delta) {
//        Gdx.app.log("GameWorld", "update() called");
        ball.update(delta);
    }

    public Ball getBall() {
        return ball;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void invert() {
        inverted = !inverted;
    }
}

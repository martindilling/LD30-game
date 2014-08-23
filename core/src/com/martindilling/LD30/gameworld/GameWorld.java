package com.martindilling.LD30.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.martindilling.LD30.gameobjects.Ball;

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

    public GameWorld() {
        ball = new Ball(512, 320, 16, 16, 0);
    }

    public void update(float delta) {
//        Gdx.app.log("GameWorld", "update() called");
        ball.update(delta);
    }

    public Ball getBall() {
        return ball;
    }
}

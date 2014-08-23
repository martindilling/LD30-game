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
    private Ball ball;

    public GameWorld(int midPointY) {
        ball = new Ball(33, midPointY - 5, 17, 12);
    }

    public void update(float delta) {
//        Gdx.app.log("GameWorld", "update() called");
        ball.update(delta);
    }

    public Ball getBall() {
        return ball;
    }
}

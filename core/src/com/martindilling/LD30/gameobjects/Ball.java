package com.martindilling.LD30.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.gameobjects
 * Author:  Martin
 * Date:    23-08-2014
 */
public class Ball
{
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    public Ball(float x, float y, int height, int width) {
        this.height = height;
        this.width = width;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        position.add(velocity.cpy().scl(delta));
    }

    public void onClick() {
        velocity.y = -140;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
}

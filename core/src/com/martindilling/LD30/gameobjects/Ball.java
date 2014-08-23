package com.martindilling.LD30.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.martindilling.LD30.helpers.AssetLoader;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.gameobjects
 * Author:  Martin
 * Date:    23-08-2014
 */
public class Ball
{
    private Vector2 position;
    private Vector2 oldPosition;
    private Vector2 velocity;
    private Vector2 acceleration;
    private int maxVelocity = 200;
    private int startAcceleration = 100;
    private String orientation = "horizontal";
    private String direction = "left";

    private int width;
    private int height;

    private TextureRegion color = AssetLoader.ballWhite;

    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    private Rectangle boundingBox;

    private boolean isFalling = false;

    public Ball(float x, float y, int height, int width, int angle) {
        this.height = height;
        this.width = width;
        this.position = new Vector2(x, y);
        this.oldPosition = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
        boundingBox = new Rectangle();

        fall(direction);
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));

        constrainVelocity();

        oldPosition.x = position.x;
        oldPosition.y = position.y;

        position.add(velocity.cpy().scl(delta));


        if (movingUp) {
            move("up");
        } else if (movingDown) {
            move("down");
        } else if (movingLeft) {
            move("left");
        } else if (movingRight) {
            move("right");
        } else {
            stop();
        }


        boundingBox.set(position.x + 4, position.y + 4, 8, 8);
    }

    public void bounce() {
        isFalling = false;
        if (direction.equals("up")) {
            fall("down");
        } else if (direction.equals("down")) {
            fall("up");
        } else if (direction.equals("left")) {
            fall("right");
        } else if (direction.equals("right")) {
            fall("left");
        }
    }

    private void constrainVelocity() {
        if (velocity.y > maxVelocity) {
            velocity.y = maxVelocity;
        }
        if (velocity.y < -maxVelocity) {
            velocity.y = -maxVelocity;
        }
        if (velocity.x > maxVelocity) {
            velocity.x = maxVelocity;
        }
        if (velocity.x < -maxVelocity) {
            velocity.x = -maxVelocity;
        }
    }

    public void onClick() {
        bounce();
//        velocity.y = -140;
//        velocity.x = 20;

    }

    public void setColor(String colorstr) {
        if (colorstr.equals("white")) {
            color = AssetLoader.ballWhite;
        } else if (colorstr.equals("red")) {
            color = AssetLoader.ballRed;
        } else if (colorstr.equals("green")) {
            color = AssetLoader.ballGreen;
        } else if (colorstr.equals("blue")) {
            color = AssetLoader.ballBlue;
        }
    }

    public String getColorStr() {
        if (color == AssetLoader.ballRed) {
            return "red";
        } else if (color == AssetLoader.ballGreen) {
            return "green";
        } else if (color == AssetLoader.ballBlue) {
            return "blue";
        }
        return "white";
    }

    public TextureRegion getColor() {
        return color;
    }

    public void fall(String dir) {
        if (!isFalling) {
            if (dir.equalsIgnoreCase("up")) {
                velocity.y = 80;
                acceleration.set(0, -startAcceleration);
            } else if (dir.equalsIgnoreCase("down")) {
                velocity.y = -80;
                acceleration.set(0, startAcceleration);
            } else if (dir.equalsIgnoreCase("left")) {
                velocity.x = -80;
                acceleration.set(-startAcceleration, 0);
            } else if (dir.equalsIgnoreCase("right")) {
                velocity.x = 80;
                acceleration.set(startAcceleration, 0);
            }

            direction = dir;
            isFalling = true;
        }
    }

    public void startMoving(String dir) {
        if (!movingUp && dir.equals("up")) {
            movingUp = true;
            movingDown = false;
            movingLeft = false;
            movingRight = false;
        } else if (!movingDown && dir.equals("down")) {
            movingUp = false;
            movingDown = true;
            movingLeft = false;
            movingRight = false;
        } else if (!movingLeft && dir.equals("left")) {
            movingUp = false;
            movingDown = false;
            movingLeft = true;
            movingRight = false;
        } else if (!movingRight && dir.equals("right")) {
            movingUp = false;
            movingDown = false;
            movingLeft = false;
            movingRight = true;
        }
    }

    public void stopMoving() {
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
    }

    public void move(String dir) {
        if (direction.equals("left") || direction.equals("right")) {
            if (dir.equalsIgnoreCase("up")) {
                velocity.y = 100;
                //                acceleration.set(0, -startAcceleration);
            } else if (dir.equalsIgnoreCase("down")) {
                velocity.y = -100;
                //                acceleration.set(0, startAcceleration);
            }
        }

        if (direction.equals("up") || direction.equals("down")) {
            if (dir.equalsIgnoreCase("left")) {
                velocity.x = -100;
//                acceleration.set(-startAcceleration, 0);
            } else if (dir.equalsIgnoreCase("right")) {
                velocity.x = 100;
//                acceleration.set(startAcceleration, 0);
            }
        }
    }

    public void stop() {
        if (direction.equals("left") || direction.equals("right")) {
            velocity.y = 0;
        }

        if (direction.equals("up") || direction.equals("down")) {
            velocity.x = 0;
        }
    }

    public float getOriginX() {
        return position.x + (width / 2);
    }

    public float getOriginY() {
        return position.y + (height / 2);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocityX(float x) {
        velocity.x = x;
    }

    public void setVelocityY(float y) {
        velocity.y = y;
    }

    public void setOldX() {
        position.x = oldPosition.x;
    }

    public void setOldY() {
        position.y = oldPosition.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
}

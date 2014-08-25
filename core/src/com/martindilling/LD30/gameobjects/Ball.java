package com.martindilling.LD30.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private int maxVelocity = 120;
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

    private boolean isFalling = false;
    private boolean isWallBouncing = false;
    private float bounceStartPos = 0;
    private float bounceMoveLength = 8;
    private boolean isMoving = false;
    private String wallBounceDir;
    private boolean movingPaused = false;

    public Ball(float x, float y, int height, int width, int angle) {
        this.height = height;
        this.width = width;
        this.position = new Vector2(x, y);
        this.oldPosition = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);

        fall(direction);
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));

        constrainVelocity();

        oldPosition.x = position.x;
        oldPosition.y = position.y;

        position.add(velocity.cpy().scl(delta));

        checkWallBounce();

        if (!movingUp && !movingDown && !movingLeft && !movingRight) {
            isMoving = false;
        }

        if (isMoving) {
            if (movingUp) {
                move("up");
            } else if (movingDown) {
                move("down");
            } else if (movingLeft) {
                move("left");
            } else if (movingRight) {
                move("right");
            }
        } else if (!movingPaused && !isWallBouncing) {
            stop();
        }

    }

    public void changeDirection() {
        isFalling = false;
        if (direction.equals("up")) {
            fall("right");
        } else if (direction.equals("right")) {
            fall("down");
        } else if (direction.equals("down")) {
            fall("left");
        } else if (direction.equals("left")) {
            fall("up");
        }
    }

    public String getMovingDirection() {
        if (direction.equals("up") || direction.equals("down")) {
            return "vertical";
        } else if (direction.equals("right") || direction.equals("left")) {
            return "horizontal";
        }
        return "";
    }

    public String getDirection() {
//        Gdx.app.log("Ball", "get direction: "+direction);
        if (direction.equals("up")) {
            return "up";
        } else if (direction.equals("down")) {
            return "down";
        } else if (direction.equals("left")) {
            return "left";
        } else if (direction.equals("right")) {
            return "right";
        }
        return "";
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

    public void wallBounce(String dir) {
        Gdx.app.log("WallBounce", dir);
        isWallBouncing = true;
        isMoving = false;
        movingPaused = true;
        wallBounceDir = dir;
        if (dir.equals("up")) {
            bounceStartPos = position.y;

        } else if (dir.equals("down")) {
            bounceStartPos = position.y;

        } else if (dir.equals("left")) {
            bounceStartPos = position.x;

        } else if (dir.equals("right")) {
            bounceStartPos = position.x;

        }

//        Gdx.app.log("WallBounce", "StartPos: "+bounceStartPos);

        bounceFromWall(dir);
    }

    public void checkWallBounce() {
        if (isWallBouncing) {
//            Gdx.app.log("bounceStart", bounceStartPos + "");
//            Gdx.app.log("bouncePosY", position.y + "");
//            Gdx.app.log("bounceDiffY", Math.abs(bounceStartPos-position.y) + "");
//            Gdx.app.log("bounceDiffX", Math.abs(bounceStartPos-position.x) + "");
//            Gdx.app.log("---", "---");
//            Gdx.app.log("WallBounce", "Checking...");
            if (wallBounceDir.equals("up")) {
//                Gdx.app.log("WallBounce", "Check Up");
                if (Math.abs(bounceStartPos-position.y) > bounceMoveLength) {
                    Gdx.app.log("WallBounce", "Done bouncing: up");
                    isWallBouncing = false;
                    movingPaused = false;
                    isMoving = true;
                }

            } else if (wallBounceDir.equals("down")) {
//                Gdx.app.log("WallBounce", "Check Down");
                if (Math.abs(bounceStartPos-position.y) > bounceMoveLength) {
                    Gdx.app.log("WallBounce", "Done bouncing: down");
                    isWallBouncing = false;
                    movingPaused = false;
                    isMoving = true;
                }

            } else if (wallBounceDir.equals("left")) {
//                Gdx.app.log("WallBounce", "Check Left");
                if (Math.abs(bounceStartPos-position.x) > bounceMoveLength) {
                    Gdx.app.log("WallBounce", "Done bouncing: left");
                    isWallBouncing = false;
                    movingPaused = false;
                    isMoving = true;
                }

            } else if (wallBounceDir.equals("right")) {
//                Gdx.app.log("WallBounce", "Check Right");
                if (Math.abs(bounceStartPos-position.x) > bounceMoveLength) {
                    Gdx.app.log("WallBounce", "Done bouncing: right");
                    isWallBouncing = false;
                    movingPaused = false;
                    isMoving = true;
                }

            }
        }
    }

    public void bounceFromWall(String dir) {
        if (dir.equalsIgnoreCase("up")) {
            velocity.y = 60;
        } else if (dir.equalsIgnoreCase("down")) {
            velocity.y = -60;
        } else if (dir.equalsIgnoreCase("left")) {
            velocity.x = -60;
        } else if (dir.equalsIgnoreCase("right")) {
            velocity.x = 60;
        }
    }

    public void setPos(int x, int y) {
        position.set(x, y);
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

    public void setColor(String colorstr) {
        if (colorstr.equals("white")) {
            color = AssetLoader.ballWhite;
        } else if (colorstr.equals("red")) {
            color = AssetLoader.ballRed;
        } else if (colorstr.equals("green")) {
            color = AssetLoader.ballGreen;
        } else if (colorstr.equals("blue")) {
            color = AssetLoader.ballBlue;
        } else if (colorstr.equals("purple")) {
            color = AssetLoader.ballPurple;
        }
    }

    public String getColorStr() {
        if (color == AssetLoader.ballRed) {
            return "red";
        } else if (color == AssetLoader.ballGreen) {
            return "green";
        } else if (color == AssetLoader.ballBlue) {
            return "blue";
        } else if (color == AssetLoader.ballPurple) {
            return "purple";
        }
        return "white";
    }

    public TextureRegion getColor() {
        return color;
    }

    public void fall(String dir) {
        if (!isFalling) {
//            velocity.x = 0;
//            velocity.y = 0;
            if (dir.equalsIgnoreCase("up")) {
                velocity.y = -maxVelocity;
//                acceleration.set(0, -startAcceleration);
            } else if (dir.equalsIgnoreCase("down")) {
                velocity.y = maxVelocity;
//                acceleration.set(0, startAcceleration);
            } else if (dir.equalsIgnoreCase("left")) {
                velocity.x = -maxVelocity;
//                acceleration.set(-startAcceleration, 0);
            } else if (dir.equalsIgnoreCase("right")) {
                velocity.x = maxVelocity;
//                acceleration.set(startAcceleration, 0);
            }

            direction = dir;
            isFalling = true;
        }
    }

    public void startMoving(String dir) {
        isMoving = true;
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
                velocity.y = maxVelocity;
//                acceleration.set(0, -startAcceleration);
            } else if (dir.equalsIgnoreCase("down")) {
                velocity.y = -maxVelocity;
//                acceleration.set(0, startAcceleration);
            }
        }

        if (direction.equals("up") || direction.equals("down")) {
            if (dir.equalsIgnoreCase("left")) {
                velocity.x = -maxVelocity;
//                acceleration.set(-startAcceleration, 0);
            } else if (dir.equalsIgnoreCase("right")) {
                velocity.x = maxVelocity;
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
}

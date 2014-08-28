package com.martindilling.LD30.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.assets.Assets;
import com.martindilling.LD30.gameobjects.Ball;
import com.martindilling.LD30.loaders.Screens;
import com.martindilling.LD30.screens.GameScreen;

import java.util.Iterator;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.gameworld
 * Author:  Martin
 * Date:    23-08-2014
 */
public class GameRenderer
{
    public TextureRegion bgDefault, bgInverted;
    public TextureRegion ballWhite, ballRed, ballGreen, ballBlue, ballPurple;
    public TextureRegion wallDefault, wallInverted;
    public TextureRegion colorRed, colorGreen, colorBlue, colorPurple;
    public TextureRegion brickRed, brickGreen, brickBlue, brickPurple;
    public TextureRegion dangerRed, dangerGreen, dangerBlue, dangerPurple;
    private GameScreen screen;
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private TiledMapTileLayer tiledLayer;
    private ShapeRenderer shape;
    private SpriteBatch batch;
    private BitmapFont font;
    private Ball ball;
    private float tileWidth;
    private float tileHeight;
    private Vector2 ballTopLeft = new Vector2(0, 0);
    private Vector2 ballTopRight = new Vector2(0, 0);
    private Vector2 ballBottomLeft = new Vector2(0, 0);
    private Vector2 ballBottomRight = new Vector2(0, 0);
    private String activeMap;
    private int activeLevel;
    private int bricksLeft;

    public GameRenderer(GameScreen screen) {
        this.screen = screen;

        camera = screen.camera;
        camera.setToOrtho(false, screen.world.width, screen.world.height);
        font = screen.font;
        batch = screen.batch;
        shape = screen.shape;

        initGameObjects();
        initGameAssets();

        loadMap(1);
    }

    public void loadMap(int level) {
        activeLevel = level;
        switch (activeLevel) {
            case 1:
                activeMap = "maps/map_01.tmx";
                break;
            case 2:
                activeMap = "maps/map_02.tmx";
                break;
            case 3:
                activeMap = "maps/map_03.tmx";
                break;
            case 4:
                activeMap = "maps/map_04.tmx";
                break;
            case 5:
                activeMap = "maps/map_05.tmx";
                break;
            case 6:
                activeMap = "maps/map_06.tmx";
                break;

            default:
                activeMap = "maps/map_01.tmx";
        }

        resetMap();
    }

    public void CalcBricksLeft() {
        bricksLeft = 0;

        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                Cell cell = tiledLayer.getCell(x, y);
                if (cell == null) {
                    continue;
                }
                if (cell.getTile() == null) {
                    continue;
                }

                if (shouldDestroy(cell) || brickEffect(cell).equals("die")) {
                    bricksLeft++;
                }
            }
        }
    }

    public boolean areAllBricksDestroyed() {
        if (bricksLeft < 1) {
            return true;
        }
        return false;
    }

    public void render() {
        //        Gdx.app.log("GameRenderer", "render() called");

        // Clean screen
        Gdx.gl.glClearColor(50 / 255f, 50 / 255f, 50 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        safeBallIfOut();

        if (areAllBricksDestroyed()) {
            LevelCleared();
        }

        boolean collisionUp = false;
        boolean collisionDown = false;
        boolean collisionLeft = false;
        boolean collisionRight = false;
        boolean hasSideCollision = false;
        boolean hasAnyCollision = false;


        // Vertical Check
        Cell verticalCell = null;
        if (ball.getVelocity().y < 0) {
            verticalCell = getCollidingTileUp(ball.getOriginX(), ball.getOriginY(), ball.getWidth() / 2);

            if (verticalCell != null && isSolid(verticalCell)) {
                hasAnyCollision = true;
                collisionDown = true;
                if (ball.getMovingDirection().equals("horizontal")) {
                    hasSideCollision = true;
                }
            }
        } else if (ball.getVelocity().y > 0) {
            verticalCell = getCollidingTileDown(ball.getOriginX(), ball.getOriginY(), ball.getWidth() / 2);

            if (verticalCell != null && isSolid(verticalCell)) {
                hasAnyCollision = true;
                collisionUp = true;
                if (ball.getMovingDirection().equals("horizontal")) {
                    hasSideCollision = true;
                }
            }
        }


        // Horizontal Check
        Cell horizontalCell = null;
        if (ball.getVelocity().x < 0) {
            horizontalCell = getCollidingTileLeft(ball.getOriginX(), ball.getOriginY(), ball.getWidth() / 2);

            if (horizontalCell != null && isSolid(horizontalCell)) {
                hasAnyCollision = true;
                collisionLeft = true;
                if (ball.getMovingDirection().equals("vertical")) {
                    hasSideCollision = true;
                }
            }
        } else if (ball.getVelocity().x > 0) {
            horizontalCell = getCollidingTileRight(ball.getOriginX(), ball.getOriginY(), ball.getWidth() / 2);

            if (horizontalCell != null && isSolid(horizontalCell)) {
                hasAnyCollision = true;
                collisionRight = true;
                if (ball.getMovingDirection().equals("vertical")) {
                    hasSideCollision = true;
                }
            }
        }


        // Do wall bounce
        if (hasSideCollision) {
//            Gdx.app.log("Moving Direction", ball.getMovingDirection());
            if (ball.getMovingDirection().equals("vertical") && isSolid(horizontalCell)) {
                ball.setOldX();
                if (collisionLeft) {
//                    Gdx.app.log("Bounce", "On left");
                    ball.wallBounce("right");
                } else if (collisionRight) {
                    ball.wallBounce("left");
                }
            } else if (ball.getMovingDirection().equals("horizontal") && isSolid(verticalCell)) {
                ball.setOldY();
                if (collisionUp) {
//                    Gdx.app.log("Bounce", "On up");
                    ball.wallBounce("down");
                } else if (collisionDown) {
//                    Gdx.app.log("Bounce", "On down");
                    ball.wallBounce("up");
                }
            }
        }

        if (hasAnyCollision && !hasSideCollision) {
            if (
                    (ball.getMovingDirection().equals("vertical") && isSolid(verticalCell)) ||
                    (ball.getMovingDirection().equals("horizontal") && isSolid(horizontalCell))
            ) {
//                Gdx.app.log("Collision", "Bounce");
                ball.bounce();
            }
        }

        if (verticalCell != null) {
//            Gdx.app.log("GameRenderer", "Collision Vertical!");
            if (isSolid(verticalCell)) {

                if (shouldDestroy(verticalCell)) {
                    String color = brickColor(verticalCell);
                    if (ball.getColorStr().equals(color)) {
//                        Gdx.app.log("Action", "Destroy: color -> "+color);
                        screen.PlayDestroyBrickSound();
                        verticalCell.setTile(null);
                        bricksLeft--;
                        LD30.log("BlocksLeft", bricksLeft+"");
                    }
                }

                if (brickEffect(verticalCell).equals("color")) {
                    String color = brickColor(verticalCell);
//                    Gdx.app.log("Action", "Effect: color -> "+color);
                    screen.PlayDestroyBrickSound();
                    ball.setColor(color);
                }

                if (brickEffect(verticalCell).equals("die")) {
                    String color = brickColor(verticalCell);
//                    Gdx.app.log("Action", "Effect: color -> "+color);
                    if (ball.getColorStr().equals(color)) {
//                        Gdx.app.log("Action", "Effect: die");
                        GameOver();
                    }
                }
            }
        }

        if (horizontalCell != null) {
//            Gdx.app.log("GameRenderer", "Collision Horizontal!");
            if (isSolid(horizontalCell)) {

                if (shouldDestroy(horizontalCell)) {
                    String color = brickColor(horizontalCell);
                    if (ball.getColorStr().equals(color)) {
//                        Gdx.app.log("Action", "Destroy: color -> "+color);
                        screen.PlayDestroyBrickSound();
                        horizontalCell.setTile(null);
                        bricksLeft--;
                        LD30.log("BlocksLeft", bricksLeft + "");
                    }
                }

                if (brickEffect(horizontalCell).equals("color")) {
                    String color = brickColor(horizontalCell);
//                    Gdx.app.log("Action", "Effect: color -> "+color);
                    screen.PlayDestroyBrickSound();
                    ball.setColor(color);
                }

                if (brickEffect(horizontalCell).equals("die")) {
                    String color = brickColor(horizontalCell);
//                    Gdx.app.log("Action", "Effect: color -> "+color);
                    if (ball.getColorStr().equals(color)) {
//                        Gdx.app.log("Action", "Effect: die");
                        GameOver();
                    }
                }
            }
        }




        // Draw textures
        batch.begin();
        drawBackground();
        batch.end();


        screen.stage.addActor(ball);

        camera.update();
        renderTiledMap();

    }

    private void LevelCleared() {
//        LD30.log("YAAAY", "You completed the level :P");
        screen.game.levelDone(activeLevel);
        screen.game.save();
        LD30.state = LD30.GAME_COMPLETE;
        screen.PlayClearedSound();
        screen.game.setScreen(Screens.cleared);
    }

    private void GameOver() {
        LD30.state = LD30.GAME_OVER;
        screen.PlayGameOverSound();
        screen.game.setScreen(Screens.gameOver);
    }

    public void resetMap() {
        if (tiledMap != null) {
            tiledMap.dispose();
        }
        if (screen.world.isInverted()) {
            screen.world.invert();
        }

        tiledMap = new TmxMapLoader().load(activeMap);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Tiles");
        tileWidth = tiledLayer.getTileWidth();
        tileHeight = tiledLayer.getTileHeight();

        CalcBricksLeft();

        ball.setPos(512-8, 320-8);
        ball.reset();


        render();

        LD30.state = LD30.GAME_READY;
    }

    private void safeBallIfOut() {
        if (
                (ball.getOriginX() < 0) ||
                (ball.getOriginX() > screen.world.width) ||
                (ball.getOriginY() < 0) ||
                (ball.getOriginY() > screen.world.height)
        ) {
            ball.setPos(512-8, 320-8);
        }
    }

    public void invert() {
        TiledMapTileSet tileSet = tiledMap.getTileSets().getTileSet("tiles_32x32");

        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                Cell cell = tiledLayer.getCell(x, y);
                if (cell == null) {
                    continue;
                }
                if (cell.getTile() == null) {
                    continue;
                }

                inverseBricks(tileSet, cell);
            }
        }

        screen.world.invert();
        ball.changeDirection();

    }

    private void inverseBricks(TiledMapTileSet tileSet, Cell cell) {
        // Red
        if (cell.getTile().getId() == 1) {
            cell.setTile(tileSet.getTile(9));
            return;
        }
        if (cell.getTile().getId() == 9) {
            cell.setTile(tileSet.getTile(1));
        }

        // Green
        if (cell.getTile().getId() == 2) {
            cell.setTile(tileSet.getTile(10));
            return;
        }
        if (cell.getTile().getId() == 10) {
            cell.setTile(tileSet.getTile(2));
        }

        // Blue
        if (cell.getTile().getId() == 3) {
            cell.setTile(tileSet.getTile(11));
            return;
        }
        if (cell.getTile().getId() == 11) {
            cell.setTile(tileSet.getTile(3));
        }

        // Purple
        if (cell.getTile().getId() == 4) {
            cell.setTile(tileSet.getTile(12));
            return;
        }
        if (cell.getTile().getId() == 12) {
            cell.setTile(tileSet.getTile(4));
        }
    }


    private boolean isSolid(Cell cell) {
        return hasKeyValue(cell, "solid", "true");
    }

    private boolean shouldDestroy(Cell cell) {
        return hasKeyValue(cell, "destroy", "true");
    }

    private String brickColor(Cell cell) {
        return getValue(cell, "color");
    }

    private String brickEffect(Cell cell) {
        return getValue(cell, "effect");
    }

    private boolean hasKeyValue(Cell cell, String key, String value) {
        if (cell == null || cell.getTile() == null || cell.getTile().getProperties() == null) {
            return false;
        }
        if (!cell.getTile().getProperties().containsKey(key)) {
            return false;
        }
        Object property = cell.getTile().getProperties().get(key);
        if (property != null) {
            return property.equals(value);
        }
        return false;
    }

    private String getValue(Cell cell, String key) {
        if (cell == null || cell.getTile() == null || cell.getTile().getProperties() == null) {
            return "";
        }
        if (!cell.getTile().getProperties().containsKey(key)) {
            return "";
        }
        Object property = cell.getTile().getProperties().get(key);
        if (property != null) {
            return property.toString();
        }
        return "";
    }


    private Cell getCollidingTileLeft(float centerX, float centerY, float halfLength) {
        int cellX = posXtoCellX(centerX - halfLength-1);
        int cellY1 = posYtoCellY(centerY - halfLength+1);
        int cellYc = posYtoCellY(centerY);
        int cellY3 = posYtoCellY(centerY + halfLength-1);
        int cellMaxY;
        int cellMinY;

        if (cellYc == cellY1) {
            cellMaxY = cellY1;
            cellMinY = cellY3;
        } else {
            cellMaxY = cellY3;
            cellMinY = cellY1;
        }

        Cell maxCell = getMapCell(cellX, cellMaxY);
        Cell minCell = getMapCell(cellX, cellMinY);

        if (maxCell != null && maxCell.getTile() != null) {
//            Gdx.app.log("CellLeft", "X:"+cellX+" Y:"+cellMaxY);
            return maxCell;
        }

        if (minCell != null && minCell.getTile() != null) {
//            Gdx.app.log("CellLeft", "X:"+cellX+" Y:"+cellMinY);
            return minCell;
        }

        return null;
    }

    private Cell getCollidingTileRight(float centerX, float centerY, float halfLength) {
        int cellX = posXtoCellX(centerX + halfLength+1);
        int cellY1 = posYtoCellY(centerY - halfLength+1);
        int cellYc = posYtoCellY(centerY);
        int cellY3 = posYtoCellY(centerY + halfLength-1);
        int cellMaxY;
        int cellMinY;

        if (cellYc == cellY1) {
            cellMaxY = cellY1;
            cellMinY = cellY3;
        } else {
            cellMaxY = cellY3;
            cellMinY = cellY1;
        }

        Cell maxCell = getMapCell(cellX, cellMaxY);
        Cell minCell = getMapCell(cellX, cellMinY);

        if (maxCell != null && maxCell.getTile() != null) {
//            Gdx.app.log("CellRight", "X:"+cellX+" Y:"+cellMaxY);
            return maxCell;
        }

        if (minCell != null && minCell.getTile() != null) {
//            Gdx.app.log("CellRight", "X:"+cellX+" Y:"+cellMinY);
            return minCell;
        }

        return null;
    }

    private Cell getCollidingTileUp(float centerX, float centerY, float halfLength) {
        int cellY = posYtoCellY(centerY - halfLength-1);
        int cellX1 = posXtoCellX(centerX - halfLength+1);
        int cellXc = posXtoCellX(centerX);
        int cellX3 = posXtoCellX(centerX + halfLength-1);
        int cellMinX;
        int cellMaxX;

        if (cellXc == cellX3) {
            cellMaxX = cellX3;
            cellMinX = cellX1;
        } else {
            cellMaxX = cellX1;
            cellMinX = cellX3;
        }

        Cell maxCell = getMapCell(cellMaxX, cellY);
        Cell minCell = getMapCell(cellMinX, cellY);

        if (maxCell != null && maxCell.getTile() != null) {
//            Gdx.app.log("CellUp", "X:"+cellMaxX+" Y:"+cellY);
            return maxCell;
        }

        if (minCell != null && minCell.getTile() != null) {
//            Gdx.app.log("CellUp", "X:"+cellMinX+" Y:"+cellY);
            return minCell;
        }

        return null;
    }

    private Cell getCollidingTileDown(float centerX, float centerY, float halfLength) {
        int cellY = posYtoCellY(centerY + halfLength+1);
        int cellX1 = posXtoCellX(centerX - halfLength+1);
        int cellXc = posXtoCellX(centerX);
        int cellX3 = posXtoCellX(centerX + halfLength-1);
        int cellMinX;
        int cellMaxX;

        if (cellXc == cellX3) {
            cellMaxX = cellX3;
            cellMinX = cellX1;
        } else {
            cellMaxX = cellX1;
            cellMinX = cellX3;
        }

        Cell maxCell = getMapCell(cellMaxX, cellY);
        Cell minCell = getMapCell(cellMinX, cellY);

        if (maxCell != null && maxCell.getTile() != null) {
//            Gdx.app.log("CellDown", "X:"+cellMaxX+" Y:"+cellY);
            return maxCell;
        }

        if (minCell != null && minCell.getTile() != null) {
//            Gdx.app.log("CellDown", "X:"+cellMinX+" Y:"+cellY);
            return minCell;
        }

        return null;
    }



    private int posXtoCellX(float posX) {
        return (int) (posX / tileWidth);
    }

    private int posYtoCellY(float posY) {
        return (int) (posY / tileHeight);
    }

    private Cell getMapCell(int x, int y) {
        return tiledLayer.getCell(x, y);
    }

    private boolean isCellSolid(float x, float y) {
        return false;
//        return checkCellProperty(getMapCell((int) (x / tileWidth), (int) (y / tileHeight)), "solid", "true");
    }

    private boolean checkCellProperty(Cell cell, String key, String value) {


        return  cell != null &&
                cell.getTile() != null &&
                cell.getTile().getProperties().get(key) != null &&
                cell.getTile().getProperties().get(key).equals(value);
    }

    private void drawBall() {
//        batch.draw(
//                ball.getBallColor(),
//                ball.getX(), ball.getY(),
//                ball.getWidth(), ball.getHeight()
//        );
    }

    private void drawWalls() {
        // Draw Walls
        //        TiledDrawable wallTile = new TiledDrawable(wallDefault);
        //        // Top
        //        wallTile.draw(
        //                batch,
        //            /* X, Y */ 0, 0,
        //            /* w, h */ screen.width, wallTile.getRegion().getRegionHeight()
        //        );
        //        // Bottom
        //        wallTile.draw(
        //                batch,
        //            /* X, Y */ 0, screen.height - wallTile.getRegion().getRegionHeight(),
        //            /* w, h */ screen.width, wallTile.getRegion().getRegionHeight()
        //        );
        //        // Left
        //        wallTile.draw(
        //                batch,
        //            /* X, Y */ 0, 0,
        //            /* w, h */ wallTile.getRegion().getRegionWidth(), screen.height
        //        );
        //        // Right
        //        wallTile.draw(
        //                batch,
        //            /* X, Y */ screen.width - wallTile.getRegion().getRegionWidth(), 0,
        //            /* w, h */ wallTile.getRegion().getRegionWidth(), screen.height
        //        );
    }

    private void drawBackground() {
        if (!screen.world.isInverted()) {
            TiledDrawable bgTile = new TiledDrawable(bgDefault);
            bgTile.draw(batch, 0, 0, 1024, 640);
        } else {
            TiledDrawable bgTile = new TiledDrawable(bgInverted);
            bgTile.draw(batch, 0, 0, 1024, 640);
        }
    }

    private void renderTiledMap() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    private void initGameObjects() {
        ball = screen.world.getBall();
    }

    private void initGameAssets() {
        bgDefault = Assets.instance.images.bgDefault;
        bgInverted = Assets.instance.images.bgInverted;

        wallDefault = Assets.instance.images.wallDefault;
        wallInverted = Assets.instance.images.wallInverted;

        ballWhite = Assets.instance.images.ballWhite;
        ballRed = Assets.instance.images.ballRed;
        ballGreen = Assets.instance.images.ballGreen;
        ballBlue = Assets.instance.images.ballBlue;
        ballPurple = Assets.instance.images.ballPurple;

        colorRed = Assets.instance.images.colorRed;
        colorGreen = Assets.instance.images.colorGreen;
        colorBlue = Assets.instance.images.colorBlue;
        colorPurple = Assets.instance.images.colorPurple;

        brickRed = Assets.instance.images.brickRed;
        brickGreen = Assets.instance.images.brickGreen;
        brickBlue = Assets.instance.images.brickBlue;
        brickPurple = Assets.instance.images.brickPurple;

        dangerRed = Assets.instance.images.dangerRed;
        dangerGreen = Assets.instance.images.dangerGreen;
        dangerBlue = Assets.instance.images.dangerBlue;
        dangerPurple = Assets.instance.images.dangerPurple;
    }
}

package com.martindilling.LD30.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.martindilling.LD30.gameobjects.Ball;
import com.martindilling.LD30.helpers.AssetLoader;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.gameworld
 * Author:  Martin
 * Date:    23-08-2014
 */
public class GameRenderer
{
    public TextureRegion bg;
    public TextureRegion ballWhite, ballRed, ballGreen, ballBlue;
    public TextureRegion wallDefault, wallRed, wallGreen, wallBlue;
    public TextureRegion brickRed, brickGreen, brickBlue;
    public TextureRegion brickDangerRed, brickDangerGreen, brickDangerBlue;
    public TextureRegion portalRed, portalGreen, portalBlue;
    private GameWorld world;
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private TiledMapTileLayer tiledLayer;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private Ball ball;
    private float tileWidth;
    private float tileHeight;
    private Vector2 ballTopLeft = new Vector2(0, 0);
    private Vector2 ballTopRight = new Vector2(0, 0);
    private Vector2 ballBottomLeft = new Vector2(0, 0);
    private Vector2 ballBottomRight = new Vector2(0, 0);

    public GameRenderer(GameWorld world) {
        this.world = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, world.width, world.height);

        tiledMap = new TmxMapLoader().load("levels/level1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Tiles");
        tileWidth = tiledLayer.getTileWidth();
        tileHeight = tiledLayer.getTileHeight();


        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initGameAssets();
    }

    public void render(float runTime) {
        //        Gdx.app.log("GameRenderer", "render() called");

        // Clean screen
        Gdx.gl.glClearColor(50 / 255f, 50 / 255f, 50 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boolean collisionX = false;
        boolean collisionTop = false;
        boolean collisionBottom = false;

        ballTopLeft.x     = ball.getX() - ball.getWidth();
        ballTopLeft.y     = ball.getY();

        ballTopRight.x    = ball.getX() + ball.getWidth();
        ballTopRight.y    = ball.getY();

        ballBottomLeft.x  = ball.getX() - ball.getWidth();
        ballBottomLeft.y  = ball.getY() + ball.getHeight();

        ballBottomRight.x = ball.getX() + ball.getWidth();
        ballBottomRight.y = ball.getY() + ball.getHeight();



        // Vertical Check
        Cell verticalCell = null;
        if (ball.getVelocity().y < 0) {
            verticalCell = getCollidingTileUp(ball.getOriginX(), ball.getOriginY(), ball.getWidth() / 2);

        } else if (ball.getVelocity().y > 0) {
            verticalCell = getCollidingTileDown(ball.getOriginX(), ball.getOriginY(), ball.getWidth() / 2);

        }


        // Horizontal Check
        Cell horizontalCell = null;
        if (ball.getVelocity().x < 0) {
            horizontalCell = getCollidingTileLeft(ball.getOriginX(), ball.getOriginY(), ball.getWidth() / 2);

        } else if (ball.getVelocity().x > 0) {
            horizontalCell = getCollidingTileRight(ball.getOriginX(), ball.getOriginY(), ball.getWidth() / 2);

        }


        if (verticalCell != null) {
//            Gdx.app.log("GameRenderer", "Collision Vertical!");
            if (isSolid(verticalCell)) {
                ball.setOldY();
                ball.setVelocityY(0);

                if (shouldDestroy(verticalCell)) {
                    String color = brickColor(verticalCell);
                    if (ball.getColorStr().equals(color)) {
                        Gdx.app.log("Action", "Destroy: color -> "+color);
                        verticalCell.setTile(null);
                    }
                }

                if (brickEffect(verticalCell).equals("color")) {
                    String color = brickColor(verticalCell);
                    Gdx.app.log("Action", "Effect: color -> "+color);
                    ball.setColor(color);
                }
            }
        }

        if (horizontalCell != null) {
//            Gdx.app.log("GameRenderer", "Collision Horizontal!");
            if (isSolid(horizontalCell)) {
                ball.setOldX();
                ball.bounce();

                if (shouldDestroy(horizontalCell)) {
                    String color = brickColor(horizontalCell);
                    if (ball.getColorStr().equals(color)) {
                        Gdx.app.log("Action", "Destroy: color -> "+color);
                        horizontalCell.setTile(null);
                    }
                }

                if (brickEffect(horizontalCell).equals("color")) {
                    String color = brickColor(horizontalCell);
                    Gdx.app.log("Action", "Effect: color -> "+color);
                    ball.setColor(color);
                }
            }
        }




        // Draw textures
        batcher.begin();
        batcher.disableBlending(); // Disable transparency
        drawBackground();
        drawWalls();
        batcher.enableBlending(); // Enable transparency
        drawBall();
        batcher.end();

        camera.update();
        renderTiledMap();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 0.5f);
        shapeRenderer.rect(ball.getX(), ball.getY(), 1, 1);
        shapeRenderer.end();
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
        int cellX = posXtoCellX(centerX - (halfLength * 3));
        int cellY1 = posYtoCellY(centerY - halfLength);
        int cellYc = posYtoCellY(centerY);
        int cellY3 = posYtoCellY(centerY + halfLength);
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
        int cellX = posXtoCellX(centerX + halfLength);
        int cellY1 = posYtoCellY(centerY - halfLength);
        int cellYc = posYtoCellY(centerY);
        int cellY3 = posYtoCellY(centerY + halfLength);
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
        int cellY = posYtoCellY(centerY - halfLength);
        int cellX1 = posXtoCellX(centerX - halfLength);
        int cellXc = posXtoCellX(centerX);
        int cellX3 = posXtoCellX(centerX + halfLength);
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
        int cellY = posYtoCellY(centerY + halfLength);
        int cellX1 = posXtoCellX(centerX - halfLength);
        int cellXc = posXtoCellX(centerX);
        int cellX3 = posXtoCellX(centerX + halfLength);
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
        batcher.draw(
                ball.getColor(),
                ball.getX(), ball.getY(),
                ball.getWidth(), ball.getHeight()
        );
    }

    private void drawWalls() {
        // Draw Walls
        //        TiledDrawable wallTile = new TiledDrawable(wallDefault);
        //        // Top
        //        wallTile.draw(
        //                batcher,
        //            /* X, Y */ 0, 0,
        //            /* w, h */ world.width, wallTile.getRegion().getRegionHeight()
        //        );
        //        // Bottom
        //        wallTile.draw(
        //                batcher,
        //            /* X, Y */ 0, world.height - wallTile.getRegion().getRegionHeight(),
        //            /* w, h */ world.width, wallTile.getRegion().getRegionHeight()
        //        );
        //        // Left
        //        wallTile.draw(
        //                batcher,
        //            /* X, Y */ 0, 0,
        //            /* w, h */ wallTile.getRegion().getRegionWidth(), world.height
        //        );
        //        // Right
        //        wallTile.draw(
        //                batcher,
        //            /* X, Y */ world.width - wallTile.getRegion().getRegionWidth(), 0,
        //            /* w, h */ wallTile.getRegion().getRegionWidth(), world.height
        //        );
    }

    private void drawBackground() {
        // Draw Background
        TiledDrawable bgTile = new TiledDrawable(bg);
        bgTile.draw(batcher, 0, 0, 1024, 640);
    }

    private void renderTiledMap() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    private void initGameObjects() {
        ball = world.getBall();
    }

    private void initGameAssets() {
        bg = AssetLoader.bg;
        ballWhite = AssetLoader.ballWhite;
        ballRed = AssetLoader.ballRed;
        ballGreen = AssetLoader.ballGreen;
        ballBlue = AssetLoader.ballBlue;
        wallDefault = AssetLoader.wallDefault;
        wallRed = AssetLoader.wallRed;
        wallGreen = AssetLoader.wallGreen;
        wallBlue = AssetLoader.wallBlue;
        brickRed = AssetLoader.brickRed;
        brickGreen = AssetLoader.brickGreen;
        brickBlue = AssetLoader.brickBlue;
        brickDangerRed = AssetLoader.brickDangerRed;
        brickDangerGreen = AssetLoader.brickDangerGreen;
        brickDangerBlue = AssetLoader.brickDangerBlue;
        portalRed = AssetLoader.portalRed;
        portalGreen = AssetLoader.portalGreen;
        portalBlue = AssetLoader.portalBlue;
    }
}

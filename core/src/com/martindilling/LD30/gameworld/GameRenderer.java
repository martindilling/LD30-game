package com.martindilling.LD30.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.martindilling.LD30.gameobjects.Ball;
import com.martindilling.LD30.helpers.AssetLoader;

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
    public TextureRegion portalRed, portalGreen, portalBlue;
    private GameWorld world;
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private TiledMapTileLayer tiledLayer;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private BitmapFont font;
    private Array<String> consoleLines;
    private int consoleLineCount = 1;
    private int consoleLineHeight = 17;
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

        tiledMap = new TmxMapLoader().load("maps/map_01.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Tiles");
        tileWidth = tiledLayer.getTileWidth();
        tileHeight = tiledLayer.getTileHeight();

        font = new BitmapFont();
        font.setColor(Color.RED);

        consoleLines = new Array<String>();

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initGameAssets();
    }

    private void consoleAdd(String txt) {
        consoleLines.add(txt);
    }

    private void consoleWriteLines() {
        batcher.begin();
        for (Iterator<String> line = consoleLines.iterator(); line.hasNext();) {
            font.draw(batcher, line.next(), 10, consoleLineCount*consoleLineHeight);
            consoleLineCount++;
        }
        batcher.end();
    }

    public void render() {
        //        Gdx.app.log("GameRenderer", "render() called");

        // Clean screen
        Gdx.gl.glClearColor(50 / 255f, 50 / 255f, 50 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        safeBallIfOut();

        consoleLineCount = 1;
        consoleLines.clear();

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
                        verticalCell.setTile(null);
                    }
                }

                if (brickEffect(verticalCell).equals("color")) {
                    String color = brickColor(verticalCell);
//                    Gdx.app.log("Action", "Effect: color -> "+color);
                    ball.setColor(color);
                }

                if (brickEffect(verticalCell).equals("die")) {
                    String color = brickColor(verticalCell);
//                    Gdx.app.log("Action", "Effect: color -> "+color);
                    if (ball.getColorStr().equals(color)) {
                        Gdx.app.log("Action", "Effect: die");
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
                        horizontalCell.setTile(null);
                    }
                }

                if (brickEffect(horizontalCell).equals("color")) {
                    String color = brickColor(horizontalCell);
//                    Gdx.app.log("Action", "Effect: color -> "+color);
                    ball.setColor(color);
                }

                if (brickEffect(horizontalCell).equals("die")) {
                    String color = brickColor(horizontalCell);
//                    Gdx.app.log("Action", "Effect: color -> "+color);
                    if (ball.getColorStr().equals(color)) {
                        Gdx.app.log("Action", "Effect: die");
                        GameOver();
                    }
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

        consoleWriteLines();

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(1, 0, 0, 0.5f);
//        shapeRenderer.rect(ball.getX(), ball.getY(), 1, 1);
//        shapeRenderer.end();
    }

    private void GameOver() {
        tiledMap = new TmxMapLoader().load("maps/map_01.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Tiles");
        tileWidth = tiledLayer.getTileWidth();
        tileHeight = tiledLayer.getTileHeight();

        ball.setPos(512-8, 320-8);
        ball.setColor("white");

        render();

        LD30.state = LD30.GAME_READY;
    }

    private void safeBallIfOut() {
        if (
                (ball.getOriginX() < 0) ||
                (ball.getOriginX() > world.width) ||
                (ball.getOriginY() < 0) ||
                (ball.getOriginY() > world.height)
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

        world.invert();
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
        if (world.isInverted()) {
            TiledDrawable bgTile = new TiledDrawable(bgDefault);
            bgTile.draw(batcher, 0, 0, 1024, 640);
        } else {
            TiledDrawable bgTile = new TiledDrawable(bgInverted);
            bgTile.draw(batcher, 0, 0, 1024, 640);
        }
    }

    private void renderTiledMap() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    private void initGameObjects() {
        ball = world.getBall();
    }

    private void initGameAssets() {
        bgDefault = AssetLoader.bgDefault;
        bgInverted = AssetLoader.bgInverted;

        wallDefault = AssetLoader.wallDefault;
        wallInverted = AssetLoader.wallInverted;

        ballWhite = AssetLoader.ballWhite;
        ballRed = AssetLoader.ballRed;
        ballGreen = AssetLoader.ballGreen;
        ballBlue = AssetLoader.ballBlue;
        ballPurple = AssetLoader.ballPurple;

        colorRed = AssetLoader.colorRed;
        colorGreen = AssetLoader.colorGreen;
        colorBlue = AssetLoader.colorBlue;
        colorPurple = AssetLoader.colorPurple;

        brickRed = AssetLoader.brickRed;
        brickGreen = AssetLoader.brickGreen;
        brickBlue = AssetLoader.brickBlue;
        brickPurple = AssetLoader.brickPurple;

        dangerRed = AssetLoader.dangerRed;
        dangerGreen = AssetLoader.dangerGreen;
        dangerBlue = AssetLoader.dangerBlue;
        dangerPurple = AssetLoader.dangerPurple;

        portalRed = AssetLoader.portalRed;
        portalGreen = AssetLoader.portalGreen;
        portalBlue = AssetLoader.portalBlue;
    }
}

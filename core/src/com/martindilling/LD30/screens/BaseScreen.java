package com.martindilling.LD30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.assets.Assets;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.screens
 * Author:  Martin
 * Date:    25-08-2014
 */
public abstract class BaseScreen implements Screen
{
    public LD30 game;
    public Stage stage;
    public OrthographicCamera camera;
    public FitViewport fitViewport;
    public ShapeRenderer shape;
    public SpriteBatch batch;
    public BitmapFont font;

    public BaseScreen(LD30 game) {
//        LD30.log("Construct Screen", getClass().toString());
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, game.width, game.height);

        fitViewport = new FitViewport(game.width, game.height, camera);

        font = new BitmapFont(true);
        font.setColor(Color.WHITE);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        shape = new ShapeRenderer();
        shape.setProjectionMatrix(camera.combined);

        stage = new Stage(fitViewport, batch);

        camera.update();

        // Debug the Stage
//        stage.setDebugAll(true);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta) {
        // Clean screen
        Gdx.gl.glClearColor(50 / 255f, 50 / 255f, 50 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when this screen should release all resources.
     */
    public void dispose() {
//        LD30.log("Dispose Screen", getClass().toString());
        stage.dispose();
        font.dispose();
        batch.dispose();
        shape.dispose();

    }

    /**
     * Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}.
     */
    public void show() {
        setInputProcessor();

    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    public void resize(int width, int height) {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}.
     */
    public void hide() {

    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#pause()
     */
    public void pause() {

    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#resume()
     */
    public void resume() {

    }


    protected void DrawBackground() {
        TiledDrawable bgTile = new TiledDrawable(Assets.instance.images.bgDefault);
        bgTile.draw(batch, 0, 0, game.width, game.height);
    }

    protected void DrawBorder() {
        TiledDrawable borderTile = new TiledDrawable(Assets.instance.images.wallDefault);
        float gridSize = borderTile.getMinHeight();
        borderTile.draw(batch, 0,                   0,                    game.width, gridSize);
        borderTile.draw(batch, 0,                   game.height-gridSize, game.width, gridSize);
        borderTile.draw(batch, 0,                   0,                    gridSize, game.height);
        borderTile.draw(batch, game.width-gridSize, 0,                    gridSize, game.height);
    }

    protected ImageButton CreateImageButton(TextureRegion up, TextureRegion over) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(up);
        style.imageOver = new TextureRegionDrawable(over);

        return new ImageButton(style);
    }

    protected void StartMusic() {
        if (!Assets.instance.music.crossedStars.isPlaying()) {
            Assets.instance.music.crossedStars.setLooping(true);
            Assets.instance.music.crossedStars.setVolume(0.50f);
            Assets.instance.music.crossedStars.play();
        }
    }

    protected void setInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(InputHandler());
        Gdx.input.setInputProcessor(multiplexer);
    }

    protected abstract InputAdapter InputHandler();

    public static void PlayHoverSound() {
        Assets.instance.sounds.buttonHover.play(0.2f);
    }

    public void PlayClickSound() {
        Assets.instance.sounds.click.play();
    }

    public void PlayClearedSound() {
        Assets.instance.sounds.levelCleared.play();
    }

    public void PlayDestroyBrickSound() {
        Assets.instance.sounds.destroyBrick.play(0.2f);
    }

    public void PlayGameOverSound() {
        Assets.instance.sounds.ballDie.play(0.5f);
    }

    public void PlayStartLevelSound() {
        Assets.instance.sounds.startLevel.play(0.2f);
    }
}

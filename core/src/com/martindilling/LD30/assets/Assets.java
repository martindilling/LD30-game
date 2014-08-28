package com.martindilling.LD30.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.assets
 * Author:  Martin
 * Date:    26-08-2014
 */
public class Assets implements Disposable, AssetErrorListener
{
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    public AssetImages images;
    public AssetSounds sounds;
    public AssetMusic music;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);


        // Load texture
        assetManager.load("screens/logoPastel.png", Texture.class);
        assetManager.load("screens/logoBlocks.png", Texture.class);
        assetManager.load("screens/gameOverText.png", Texture.class);
        assetManager.load("screens/clearedText.png", Texture.class);
        assetManager.load("screens/info.png", Texture.class);
        assetManager.load("tiles_16x16.png", Texture.class);
        assetManager.load("tiles_32x32.png", Texture.class);
        assetManager.load("buttons/buttons.png", Texture.class);
        assetManager.load("map_previews/map_select.png", Texture.class);
        assetManager.load("map_previews/done_sign.png", Texture.class);
        assetManager.load("map_previews/comingSoon.png", Texture.class);
        assetManager.load("map_previews/map_01_preview.png", Texture.class);
        assetManager.load("map_previews/map_02_preview.png", Texture.class);
        assetManager.load("map_previews/map_03_preview.png", Texture.class);
        assetManager.load("map_previews/map_04_preview.png", Texture.class);
        assetManager.load("map_previews/map_05_preview.png", Texture.class);
        assetManager.load("map_previews/map_06_preview.png", Texture.class);

        // Load sounds
        assetManager.load("sounds/ballDie.wav", Sound.class);
        assetManager.load("sounds/click.wav", Sound.class);
        assetManager.load("sounds/destroyBrick.wav", Sound.class);
        assetManager.load("sounds/levelCleared.wav", Sound.class);
        assetManager.load("sounds/startLevel.wav", Sound.class);
        assetManager.load("sounds/buttonHover.wav", Sound.class);

        // Load music
        assetManager.load("music/DST-CrossedStars.mp3", Music.class);

        // start loading assets and wait until finished
        assetManager.finishLoading();

        Gdx.app.log("Assets", "# of assets loaded: " + assetManager.getAssetNames().size);
//        for (String a : assetManager.getAssetNames()) {
//            Gdx.app.log("Assets", "asset: " + a);
//        }

        // create game resource objects
        images = new AssetImages();
        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);
    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error("Assets", "Couldn't load asset '" + asset + "'", throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}

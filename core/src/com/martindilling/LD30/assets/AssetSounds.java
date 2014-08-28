package com.martindilling.LD30.assets;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.assets
 * Author:  Martin
 * Date:    26-08-2014
 */
public class AssetSounds
{
    public final Sound ballDie;
    public final Sound click;
    public final Sound destroyBrick;
    public final Sound levelCleared;
    public final Sound startLevel;
    public final Sound buttonHover;

    public AssetSounds(AssetManager assetManager) {
        ballDie      = assetManager.get("sounds/ballDie.wav", Sound.class);
        click        = assetManager.get("sounds/click.wav", Sound.class);
        destroyBrick = assetManager.get("sounds/destroyBrick.wav", Sound.class);
        levelCleared = assetManager.get("sounds/levelCleared.wav", Sound.class);
        startLevel   = assetManager.get("sounds/startLevel.wav", Sound.class);
        buttonHover  = assetManager.get("sounds/buttonHover.wav", Sound.class);
    }
}

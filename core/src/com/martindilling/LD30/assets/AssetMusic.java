package com.martindilling.LD30.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.assets
 * Author:  Martin
 * Date:    26-08-2014
 */
public class AssetMusic
{
    public final Music crossedStars;

    public AssetMusic(AssetManager assetManager) {
        crossedStars = assetManager.get("music/DST-CrossedStars.mp3", Music.class);
    }
}

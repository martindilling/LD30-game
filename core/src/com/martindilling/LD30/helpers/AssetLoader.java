package com.martindilling.LD30.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.helpers
 * Author:  Martin
 * Date:    23-08-2014
 */
public class AssetLoader
{
    public static Texture texture;
    public static TextureRegion bg;

    public static void load() {
        texture = new Texture("tileset.png");
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        bg = new TextureRegion(texture, 0, 64, 16, 16);
        bg.flip(false, true);


    }
}

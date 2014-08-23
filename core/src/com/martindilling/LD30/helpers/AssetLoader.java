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
    public static Texture texture16;
    public static TextureRegion bg;
    public static TextureRegion ballWhite, ballRed, ballGreen, ballBlue;
    public static TextureRegion wallDefault, wallRed, wallGreen, wallBlue;
    public static TextureRegion brickRed, brickGreen, brickBlue;
    public static TextureRegion brickDangerRed, brickDangerGreen, brickDangerBlue;
    public static TextureRegion portalRed, portalGreen, portalBlue;

    public static void load() {
        texture16 = new Texture("tiles_16x16.png");
        texture16.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        bg = new TextureRegion(texture16, 0, 16, 16, 16);
//        bg.flip(false, true);

        ballWhite = new TextureRegion(texture16, 0, 0, 16, 16);
//        ballWhite.flip(false, true);

        ballRed = new TextureRegion(texture16, 16, 0, 16, 16);
//        ballWhite.flip(false, true);

        ballGreen = new TextureRegion(texture16, 32, 0, 16, 16);
//        ballWhite.flip(false, true);

        ballBlue = new TextureRegion(texture16, 48, 0, 16, 16);
//        ballWhite.flip(false, true);

//        wallDefault = new TextureRegion(texture16, 0, 16, 32, 16);
//        wallDefault.flip(false, true);


    }

    public static void dispose() {
        texture16.dispose();
    }
}

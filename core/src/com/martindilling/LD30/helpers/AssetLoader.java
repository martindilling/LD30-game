package com.martindilling.LD30.helpers;

import com.badlogic.gdx.Gdx;
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
    public static Texture texture32;
    public static Texture buttons;
    public static TextureRegion btnStart, btnStartHover;
    public static TextureRegion btnQuit, btnQuitHover;
    public static TextureRegion btnResume, btnResumeHover;
    public static TextureRegion btnRestart, btnRestartHover;
    public static TextureRegion bgDefault, bgInverted;
    public static TextureRegion ballWhite, ballRed, ballGreen, ballBlue, ballPurple;
    public static TextureRegion wallDefault, wallInverted;
    public static TextureRegion colorRed, colorGreen, colorBlue, colorPurple;
    public static TextureRegion brickRed, brickGreen, brickBlue, brickPurple;
    public static TextureRegion dangerRed, dangerGreen, dangerBlue, dangerPurple;
    public static TextureRegion portalRed, portalGreen, portalBlue;

    public static void load() {
        Gdx.app.log("Loading", "Assets");

        texture16 = new Texture("tiles_16x16.png");
        texture16.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        ballWhite    = new TextureRegion(texture16, 0, 0, 16, 16);
        ballRed      = new TextureRegion(texture16, 16, 0, 16, 16);
        ballGreen    = new TextureRegion(texture16, 32, 0, 16, 16);
        ballBlue     = new TextureRegion(texture16, 48, 0, 16, 16);
        ballPurple   = new TextureRegion(texture16, 64, 0, 16, 16);



        texture32 = new Texture("tiles_32x32.png");
        texture32.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        bgDefault    = new TextureRegion(texture32, 64, 96, 32, 32);
        bgInverted   = new TextureRegion(texture32, 96, 96, 32, 32);

        wallDefault  = new TextureRegion(texture32, 0, 96, 32, 32);
        wallInverted = new TextureRegion(texture32, 32, 96, 32, 32);

        brickRed     = new TextureRegion(texture32, 0, 0, 32, 32);
        brickGreen   = new TextureRegion(texture32, 32, 0, 32, 32);
        brickBlue    = new TextureRegion(texture32, 64, 0, 32, 32);
        brickPurple  = new TextureRegion(texture32, 96, 0, 32, 32);

        colorRed     = new TextureRegion(texture32, 0, 32, 32, 32);
        colorGreen   = new TextureRegion(texture32, 32, 32, 32, 32);
        colorBlue    = new TextureRegion(texture32, 64, 32, 32, 32);
        colorPurple  = new TextureRegion(texture32, 96, 32, 32, 32);



        buttons = new Texture("buttons/buttons.png");
        buttons.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        int btnWidth = 256;
        int btnHeight = 64;

        btnStart        = new TextureRegion(buttons, 0, btnHeight*0, btnWidth, btnHeight);
        btnStartHover   = new TextureRegion(buttons, 0, btnHeight*1, btnWidth, btnHeight);

        btnQuit         = new TextureRegion(buttons, 0, btnHeight*2, btnWidth, btnHeight);
        btnQuitHover    = new TextureRegion(buttons, 0, btnHeight*3, btnWidth, btnHeight);

        btnResume       = new TextureRegion(buttons, 0, btnHeight*4, btnWidth, btnHeight);
        btnResumeHover  = new TextureRegion(buttons, 0, btnHeight*5, btnWidth, btnHeight);

        btnRestart      = new TextureRegion(buttons, 0, btnHeight*6, btnWidth, btnHeight);
        btnRestartHover = new TextureRegion(buttons, 0, btnHeight*7, btnWidth, btnHeight);


        btnStart.flip(false, true);
        btnStartHover.flip(false, true);
        btnQuit.flip(false, true);
        btnQuitHover.flip(false, true);
        btnResume.flip(false, true);
        btnResumeHover.flip(false, true);
        btnRestart.flip(false, true);
        btnRestartHover.flip(false, true);

    }

    public static void dispose() {
        Gdx.app.log("Disposing", "Assets");
        texture16.dispose();
        texture32.dispose();
        buttons.dispose();
    }
}

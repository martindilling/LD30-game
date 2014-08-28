package com.martindilling.LD30.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.assets
 * Author:  Martin
 * Date:    26-08-2014
 */
public class AssetImages
{
    // 16x16 Tiles
    public Texture tilemap16;
    public TextureRegion ballWhite, ballRed, ballGreen, ballBlue, ballPurple;

    // 32x32 Tiles
    public Texture tilemap32;
    public TextureRegion bgDefault, bgInverted;
    public TextureRegion wallDefault, wallInverted;
    public TextureRegion colorRed, colorGreen, colorBlue, colorPurple;
    public TextureRegion brickRed, brickGreen, brickBlue, brickPurple;
    public TextureRegion dangerRed, dangerGreen, dangerBlue, dangerPurple;

    // Buttons
    public Texture buttons;
    public TextureRegion btnStart;
    public TextureRegion btnStartHover;
    public TextureRegion btnQuit, btnQuitHover;
    public TextureRegion btnResume, btnResumeHover;
    public TextureRegion btnRestart, btnRestartHover;
    public TextureRegion btnBack, btnBackHover;
    public TextureRegion btnHelp, btnHelpHover;

    // Big texts
    public TextureRegion logoPastel;
    public TextureRegion logoBlocks;
    public TextureRegion gameOverText;
    public TextureRegion clearedText;
    public TextureRegion info;

    // Map previews
    public TextureRegion mapHoverFrame;
    public TextureRegion mapDone, mapNotDone;
    public TextureRegion comingSoon;
    public TextureRegion map01Preview, map01PreviewHover;
    public TextureRegion map02Preview, map02PreviewHover;
    public TextureRegion map03Preview, map03PreviewHover;
    public TextureRegion map04Preview, map04PreviewHover;
    public TextureRegion map05Preview, map05PreviewHover;
    public TextureRegion map06Preview, map06PreviewHover;

    // Constructor
    public AssetImages() {
        CreateTiles16();
        CreateTiles32();
        CreateButtons();
        CreateHeaderLogo();
        CreateGameOverText();
        CreateClearedText();
        CreateInfo();

        Texture mapStatus = new Texture("map_previews/done_sign.png");
        mapStatus.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        mapDone = new TextureRegion(mapStatus, 0, 0, 32, 32);
        mapNotDone = new TextureRegion(mapStatus, 0, 32, 32, 32);
        mapDone.flip(false, true);
        mapNotDone.flip(false, true);


        mapHoverFrame = new TextureRegion(new Texture("map_previews/map_select.png"));
        mapHoverFrame.flip(false, true);

        comingSoon = new TextureRegion(new Texture("map_previews/comingSoon.png"));
        comingSoon.flip(false, true);

        CreateMap01();
        CreateMap02();
        CreateMap03();
        CreateMap04();
        CreateMap05();
        CreateMap06();

    }

    private void CreateInfo() {
        info = new TextureRegion(new Texture("screens/info.png"));
        info.flip(false, true);
    }

    private void CreateClearedText() {
        clearedText = new TextureRegion(new Texture("screens/clearedText.png"));
        clearedText.flip(false, true);
    }

    private void CreateGameOverText() {
        gameOverText = new TextureRegion(new Texture("screens/gameOverText.png"));
        gameOverText.flip(false, true);
    }

    private void CreateMap01() {
        Texture map = new Texture("map_previews/map_01_preview.png");
        map.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        map01Preview = new TextureRegion(map, 0, 0, 256, 160);
        map01PreviewHover = new TextureRegion(map, 0, 160, 256, 160);
        map01Preview.flip(false, true);
        map01PreviewHover.flip(false, true);
    }

    private void CreateMap02() {
        Texture map = new Texture("map_previews/map_02_preview.png");
        map.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        map02Preview = new TextureRegion(map, 0, 0, 256, 160);
        map02PreviewHover = new TextureRegion(map, 0, 160, 256, 160);
        map02Preview.flip(false, true);
        map02PreviewHover.flip(false, true);
    }

    private void CreateMap03() {
        Texture map = new Texture("map_previews/map_03_preview.png");
        map.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        map03Preview = new TextureRegion(map, 0, 0, 256, 160);
        map03PreviewHover = new TextureRegion(map, 0, 160, 256, 160);
        map03Preview.flip(false, true);
        map03PreviewHover.flip(false, true);
    }

    private void CreateMap04() {
        Texture map = new Texture("map_previews/map_04_preview.png");
        map.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        map04Preview = new TextureRegion(map, 0, 0, 256, 160);
        map04PreviewHover = new TextureRegion(map, 0, 160, 256, 160);
        map04Preview.flip(false, true);
        map04PreviewHover.flip(false, true);
    }

    private void CreateMap05() {
        Texture map = new Texture("map_previews/map_05_preview.png");
        map.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        map05Preview = new TextureRegion(map, 0, 0, 256, 160);
        map05PreviewHover = new TextureRegion(map, 0, 160, 256, 160);
        map05Preview.flip(false, true);
        map05PreviewHover.flip(false, true);
    }

    private void CreateMap06() {
        Texture map = new Texture("map_previews/map_06_preview.png");
        map.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        map06Preview = new TextureRegion(map, 0, 0, 256, 160);
        map06PreviewHover = new TextureRegion(map, 0, 160, 256, 160);
        map06Preview.flip(false, true);
        map06PreviewHover.flip(false, true);
    }


    private void CreateHeaderLogo() {
        logoBlocks = new TextureRegion(new Texture("screens/logoBlocks.png"));
        logoPastel = new TextureRegion(new Texture("screens/logoPastel.png"));
        logoBlocks.flip(false, true);
        logoPastel.flip(false, true);
    }

    private void CreateButtons() {
        buttons = new Texture("buttons/buttons.png");
        buttons.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        CreateButtonRegions();
        FlipButtonRegions();
    }

    private void CreateTiles32() {
        tilemap32 = new Texture("tiles_32x32.png");
        tilemap32.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        CreateBackgroundRegions();
        CreateWallRegions();
        CreateBrickRegions();
    }

    private void CreateTiles16() {
        tilemap16 = new Texture("tiles_16x16.png");
        tilemap16.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        CreateBallRegions();
    }

    private void CreateButtonRegions() {
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

        btnBack         = new TextureRegion(buttons, 0, btnHeight*8, btnWidth, btnHeight);
        btnBackHover    = new TextureRegion(buttons, 0, btnHeight*9, btnWidth, btnHeight);

        btnHelp         = new TextureRegion(buttons, 0, btnHeight*10, btnWidth, btnHeight);
        btnHelpHover    = new TextureRegion(buttons, 0, btnHeight*11, btnWidth, btnHeight);
    }

    private void FlipButtonRegions() {
        btnStart.flip(false, true);
        btnStartHover.flip(false, true);
        btnQuit.flip(false, true);
        btnQuitHover.flip(false, true);
        btnResume.flip(false, true);
        btnResumeHover.flip(false, true);
        btnRestart.flip(false, true);
        btnRestartHover.flip(false, true);
        btnBack.flip(false, true);
        btnBackHover.flip(false, true);
        btnHelp.flip(false, true);
        btnHelpHover.flip(false, true);
    }

    private void CreateBrickRegions() {
        brickRed     = new TextureRegion(tilemap32, 0, 0, 32, 32);
        brickGreen   = new TextureRegion(tilemap32, 32, 0, 32, 32);
        brickBlue    = new TextureRegion(tilemap32, 64, 0, 32, 32);
        brickPurple  = new TextureRegion(tilemap32, 96, 0, 32, 32);

        dangerRed    = new TextureRegion(tilemap32, 0, 32, 32, 32);
        dangerGreen  = new TextureRegion(tilemap32, 32, 32, 32, 32);
        dangerBlue   = new TextureRegion(tilemap32, 64, 32, 32, 32);
        dangerPurple = new TextureRegion(tilemap32, 96, 32, 32, 32);

        colorRed     = new TextureRegion(tilemap32, 0, 64, 32, 32);
        colorGreen   = new TextureRegion(tilemap32, 32, 64, 32, 32);
        colorBlue    = new TextureRegion(tilemap32, 64, 64, 32, 32);
        colorPurple  = new TextureRegion(tilemap32, 96, 64, 32, 32);
    }

    private void CreateWallRegions() {
        wallDefault  = new TextureRegion(tilemap32, 0, 96, 32, 32);
        wallInverted = new TextureRegion(tilemap32, 32, 96, 32, 32);
    }

    private void CreateBackgroundRegions() {
        bgDefault    = new TextureRegion(tilemap32, 64, 96, 32, 32);
        bgInverted   = new TextureRegion(tilemap32, 96, 96, 32, 32);
    }

    private void CreateBallRegions() {
        ballWhite    = new TextureRegion(tilemap16, 0, 0, 16, 16);
        ballRed      = new TextureRegion(tilemap16, 16, 0, 16, 16);
        ballGreen    = new TextureRegion(tilemap16, 32, 0, 16, 16);
        ballBlue     = new TextureRegion(tilemap16, 48, 0, 16, 16);
        ballPurple   = new TextureRegion(tilemap16, 64, 0, 16, 16);
    }

}

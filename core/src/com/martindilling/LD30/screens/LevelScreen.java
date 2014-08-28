package com.martindilling.LD30.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.martindilling.LD30.LD30;
import com.martindilling.LD30.assets.Assets;
import com.martindilling.LD30.loaders.Screens;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.screens
 * Author:  Martin
 * Date:    26-08-2014
 */
public class LevelScreen extends BaseScreen
{
    private final ImageButton backButton;

    private ImageButton map01Button;
    private ImageButton map02Button;
    private ImageButton map03Button;
    private ImageButton map04Button;
    private ImageButton map05Button;
    private ImageButton map06Button;

    private Image mapHoverFrame;
    private Image map01Done, map01NotDone;
    private Image map02Done, map02NotDone;
    private Image map03Done, map03NotDone;
    private Image map04Done, map04NotDone;
    private Image map05Done, map05NotDone;
    private Image map06Done, map06NotDone;

    public LevelScreen(LD30 game) {
        super(game);
        backButton = CreateBackButton(384, 512);

        map01Button = CreateMap01Button(96, 96);
        map02Button = CreateMap02Button(384, 96);
        map03Button = CreateMap03Button(672, 96);
        map04Button = CreateMap04Button(96, 320);
        map05Button = CreateMap05Button(384, 320);
        map06Button = CreateMap06Button(672, 320);

        mapHoverFrame = new Image(Assets.instance.images.mapHoverFrame);
        mapHoverFrame.setVisible(false);

        CreateLevelStatusIcons();

    }

    @Override
    public void show() {
        super.show();
        AddLevelStatusIcons();
    }

    private void AddLevelStatusIcons() {
        if (game.levelStatus.done01) {
            LevelDone(1);
        }
        if (game.levelStatus.done02) {
            LevelDone(2);
        }
        if (game.levelStatus.done03) {
            LevelDone(3);
        }
        if (game.levelStatus.done04) {
            LevelDone(4);
        }
        if (game.levelStatus.done05) {
            LevelDone(5);
        }
        if (game.levelStatus.done06) {
            LevelDone(6);
        }
    }

    public void LevelDone(int level) {
        switch (level) {
            case 1:
                map01NotDone.setVisible(false);
                break;
            case 2:
                map02NotDone.setVisible(false);
                break;
            case 3:
                map03NotDone.setVisible(false);
                break;
            case 4:
                map04NotDone.setVisible(false);
                break;
            case 5:
                map05NotDone.setVisible(false);
                break;
            case 6:
                map06NotDone.setVisible(false);
                break;
        }
    }

    public void LevelNotDone(int level) {
        switch (level) {
            case 1:
                map01NotDone.setVisible(true);
                break;
            case 2:
                map02NotDone.setVisible(true);
                break;
            case 3:
                map03NotDone.setVisible(true);
                break;
            case 4:
                map04NotDone.setVisible(true);
                break;
            case 5:
                map05NotDone.setVisible(true);
                break;
            case 6:
                map06NotDone.setVisible(true);
                break;
        }
    }

    private void CreateLevelStatusIcons() {
        map01Done    = new Image(Assets.instance.images.mapDone);
        map01NotDone = new Image(Assets.instance.images.mapNotDone);
        map02Done    = new Image(Assets.instance.images.mapDone);
        map02NotDone = new Image(Assets.instance.images.mapNotDone);
        map03Done    = new Image(Assets.instance.images.mapDone);
        map03NotDone = new Image(Assets.instance.images.mapNotDone);
        map04Done    = new Image(Assets.instance.images.mapDone);
        map04NotDone = new Image(Assets.instance.images.mapNotDone);
        map05Done    = new Image(Assets.instance.images.mapDone);
        map05NotDone = new Image(Assets.instance.images.mapNotDone);
        map06Done    = new Image(Assets.instance.images.mapDone);
        map06NotDone = new Image(Assets.instance.images.mapNotDone);

        float posX = map01Button.getWidth() - (map01Done.getWidth() / 2);
        float posY = map01Button.getHeight() - (map01Done.getHeight() / 2);

        map01Done.setPosition(posX, posY);
        map02Done.setPosition(posX, posY);
        map03Done.setPosition(posX, posY);
        map04Done.setPosition(posX, posY);
        map05Done.setPosition(posX, posY);
        map06Done.setPosition(posX, posY);

        map01NotDone.setPosition(posX, posY);
        map02NotDone.setPosition(posX, posY);
        map03NotDone.setPosition(posX, posY);
        map04NotDone.setPosition(posX, posY);
        map05NotDone.setPosition(posX, posY);
        map06NotDone.setPosition(posX, posY);

        map01Button.addActor(map01Done);
        map01Button.addActor(map01NotDone);
        map02Button.addActor(map02Done);
        map02Button.addActor(map02NotDone);
        map03Button.addActor(map03Done);
        map03Button.addActor(map03NotDone);
        map04Button.addActor(map04Done);
        map04Button.addActor(map04NotDone);
        map05Button.addActor(map05Done);
        map05Button.addActor(map05NotDone);
        map06Button.addActor(map06Done);
        map06Button.addActor(map06NotDone);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        DrawBackground();
        DrawBorder();
        batch.end();


        stage.addActor(backButton);

        stage.addActor(mapHoverFrame);
        stage.addActor(map01Button);
        stage.addActor(map02Button);
        stage.addActor(map03Button);
        stage.addActor(map04Button);
        stage.addActor(map05Button);
        stage.addActor(map06Button);
        stage.draw();

    }

    @Override
    protected InputAdapter InputHandler() {
        return new InputAdapter()
        {
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
//                        LD30.log("Goto Screen", "Start");
                        game.setScreen(Screens.start);
                        break;
                }
                return true;
            }
        };
    }


    private ImageButton CreateBackButton(float x, float y) {
    ImageButton button = CreateImageButton(Assets.instance.images.btnBack, Assets.instance.images.btnBackHover);
    button.setPosition(x, y);

    button.addListener(
            new InputListener()
            {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    PlayClickSound();
                    return true;
                }

                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                    LD30.log("Goto Screen", "Start");
                    game.setScreen(Screens.start);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    PlayHoverSound();
                }
            }
    );

    return button;
}



    private ImageButton CreateMap01Button(final float btnX, final float btnY) {
        final ImageButton button = CreateImageButton(Assets.instance.images.map01Preview, Assets.instance.images.map01PreviewHover);
        button.setPosition(btnX, btnY);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        LD30.log("Goto Level", "1");
                        game.setScreen(Screens.getLevel(1));
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                        ShowMapFrame(btnX, btnY);
                    }

                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        HideMapFrame();
                    }
                }
        );

        return button;
    }

    private ImageButton CreateMap02Button(final float btnX, final float btnY) {
        ImageButton button = CreateImageButton(Assets.instance.images.map02Preview, Assets.instance.images.map02PreviewHover);
        button.setPosition(btnX, btnY);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        LD30.log("Goto Level", "2");
                        game.setScreen(Screens.getLevel(2));
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                        ShowMapFrame(btnX, btnY);
                    }

                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        HideMapFrame();
                    }
                }
        );

        return button;
    }

    private ImageButton CreateMap03Button(final float btnX, final float btnY) {
        ImageButton button = CreateImageButton(Assets.instance.images.map03Preview, Assets.instance.images.map03PreviewHover);
        button.setPosition(btnX, btnY);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        LD30.log("Goto Level", "3");
                        game.setScreen(Screens.getLevel(3));
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                        ShowMapFrame(btnX, btnY);
                    }

                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        HideMapFrame();
                    }
                }
        );

        return button;
    }

    private ImageButton CreateMap04Button(final float btnX, final float btnY) {
        ImageButton button = CreateImageButton(Assets.instance.images.map04Preview, Assets.instance.images.map04PreviewHover);
        button.setPosition(btnX, btnY);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        LD30.log("Goto Level", "4");
                        game.setScreen(Screens.getLevel(4));
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                        ShowMapFrame(btnX, btnY);
                    }

                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        HideMapFrame();
                    }
                }
        );

        return button;
    }

    private ImageButton CreateMap05Button(final float btnX, final float btnY) {
        ImageButton button = CreateImageButton(Assets.instance.images.map05Preview, Assets.instance.images.map05PreviewHover);
        button.setPosition(btnX, btnY);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        LD30.log("Goto Level", "5");
                        game.setScreen(Screens.getLevel(5));
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                        ShowMapFrame(btnX, btnY);
                    }

                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        HideMapFrame();
                    }
                }
        );

        return button;
    }

    private ImageButton CreateMap06Button(final float btnX, final float btnY) {
        ImageButton button = CreateImageButton(Assets.instance.images.map06Preview, Assets.instance.images.map06PreviewHover);
        button.setPosition(btnX, btnY);

        button.addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        PlayClickSound();
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        LD30.log("Goto Level", "6");
                        game.setScreen(Screens.getLevel(6));
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        PlayHoverSound();
                        ShowMapFrame(btnX, btnY);
                    }

                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        HideMapFrame();
                    }
                }
        );

        return button;
    }

    private void ShowMapFrame(float btnX, float btnY) {
        mapHoverFrame.setPosition(btnX-8, btnY-8);
        mapHoverFrame.setVisible(true);
    }

    private void HideMapFrame() {
        mapHoverFrame.setVisible(false);
    }

}

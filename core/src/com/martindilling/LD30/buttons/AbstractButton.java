package com.martindilling.LD30.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.martindilling.LD30.LD30;

public abstract class AbstractButton extends Actor
{
    protected LD30 game;
    public boolean started = false;
    public boolean hover = false;
    public boolean pressed = false;
    protected Texture textureDefault;
    protected Texture textureHover;
    protected Texture texturePressed;
    private float actorX = 0;
    private float actorY = 0;

    protected AbstractButton(LD30 game) {
        this.game = game;
    }

    public void Init() {
        setBounds(actorX, actorY, textureDefault.getWidth(), textureDefault.getHeight());
        addListener(
                new InputListener()
                {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        pressed = true;
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        pressed = false;
                        started = true;
                    }

                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        hover = true;
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        hover = false;
                    }
                }
        );
    }

    public void setXY(float X, float Y) {
        this.actorX = X;
        this.actorY = Y;
        setPosition(X, Y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (pressed) {
            batch.draw(texturePressed, actorX, actorY);
        } else if (hover) {
            batch.draw(textureHover, actorX, actorY);
        } else {
            batch.draw(textureDefault, actorX, actorY);
        }
    }

    @Override
    public void act(float delta) {
        if (started) {
            action(delta);
        }
    }

    protected abstract void action(float delta);
}

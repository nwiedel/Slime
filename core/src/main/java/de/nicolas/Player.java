package de.nicolas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends GameObject {

    private static final float SCALE = 1 /32f;
    private static final int LIFE = 5;

    private final Viewport gameViewport;
    private float life = LIFE;

    public Player(float x, float y, Viewport gameViewport, Texture texture){
        super(x, y, texture.getWidth() * SCALE, texture.getHeight() * SCALE, texture);
        this.gameViewport = gameViewport;
    }

    public void reset(float x, float y){
        rect.setPosition(x, y);
        life = LIFE;
    }

    @Override
    void update(float delta) {

    }
}

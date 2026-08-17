package de.nicolas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;

    private final GdxGame game;
    private final Batch batch;
    private final Texture bgdTexture = new Texture(Gdx.files.internal("bgd.png"));
    private final Texture playerTexture = new Texture(Gdx.files.internal("player.png"));

    private final Viewport gameViewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
    private final Vector2 inputMovement = new Vector2();

    private final Player player = new Player(
        WORLD_WIDTH / 2, WORLD_HEIGHT / 2,
        gameViewport,
        playerTexture
        );

    public GameScreen(GdxGame game) {
        this.game = game;
        batch = game.getBatch();

        bgdTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
    }

    @Override
    public void show() {
        resetGame();
    }

    private void resetGame(){
        player.reset(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
    }

    private void processInput(){
        inputMovement.setZero();

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            inputMovement.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            inputMovement.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            inputMovement.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            inputMovement.x += 1;
        }

        inputMovement.nor();
        player.changeDirection(inputMovement);
    }

    @Override
    public void render(float delta) {
        processInput();
        updateLogic(delta);

        ScreenUtils.clear(Color.BLACK);

        gameViewport.apply();
        batch.setProjectionMatrix(gameViewport.getCamera().combined);
        batch.begin();

        drawBackground();
        player.draw(batch);

        batch.end();
    }

    private void updateLogic(float delta){
        player.update(delta);
    }

    private void drawBackground(){
        float u2 = gameViewport.getWorldWidth() / WORLD_WIDTH;
        float v2 = gameViewport.getWorldHeight() / WORLD_HEIGHT;
        batch.draw(bgdTexture,
            0, 0,
            gameViewport.getWorldWidth(), gameViewport.getWorldHeight(),
            0, 0,
            u2, v2
        );
    }

    @Override
    public void dispose() {
        bgdTexture.dispose();
        playerTexture.dispose();
    }
}

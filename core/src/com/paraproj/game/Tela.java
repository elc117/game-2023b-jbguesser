package com.paraproj.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Tela extends ScreenAdapter {

    private final Viewport viewport = new FitViewport(200, 200);
    private final SpriteBatch batch = new SpriteBatch();

    private final Sprite sprite = new Sprite(new Texture("MapaUFSM.png"));

    private final Vector2 cursorPos = new Vector2();

    public Tela() {
        sprite.setOriginCenter();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if (Gdx.input.isTouched()) {
            cursorPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(cursorPos);
            sprite.setOriginBasedPosition(cursorPos.x, cursorPos.y);
        }

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}

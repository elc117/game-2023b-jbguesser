package com.paraproj.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import jdk.javadoc.internal.doclets.toolkit.util.DocFinder;

public class Silveira extends Game {
    static final int WORLD_WIDTH = 12;
    static final int WORLD_HEIGHT = 15;

    private Viewport viewport;
    private SpriteBatch batch;

    Vector2 worldCoordinates;

    private Sprite mapSprite;
    private Sprite lastPositionSprite;
    @Override
    public void create() {
        mapSprite = new Sprite(new Texture("MapaUFSM.png"));
        mapSprite.setPosition(8, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        lastPositionSprite = new Sprite(new Texture("16x16.png"));
        lastPositionSprite.setSize(1f, 1f);
        lastPositionSprite.setOriginCenter();
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                worldCoordinates = new Vector2(screenX, screenY);
                viewport.unproject(worldCoordinates);

                lastPositionSprite.setOriginBasedPosition(worldCoordinates.x, worldCoordinates.y);
                System.out.println(lastPositionSprite.getX() + " " + lastPositionSprite.getY());
                return true;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();

                if(amountY > 0.1f)
                    cam.zoom -= 0.02f;
                else if(cam.zoom < 1.0f)
                    cam.zoom += 0.02f;
                System.out.println(lastPositionSprite.getX() + " " + lastPositionSprite.getY());
                return false;
            }
        });
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
        handleInput();

        lastPositionSprite.setBounds(lastPositionSprite.getX(), lastPositionSprite.getY(), cam.zoom, cam.zoom);
        lastPositionSprite.setOriginCenter();
        if(worldCoordinates != null) {
            lastPositionSprite.setOriginBasedPosition(worldCoordinates.x, worldCoordinates.y);
        }

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        mapSprite.draw(batch);
        lastPositionSprite.draw(batch);
        batch.end();
    }

    private void handleInput() {
        OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
        float delta = Gdx.graphics.getDeltaTime();

        float zoomSpeed = 1f;
        float speed = 10;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += zoomSpeed * delta;
            lastPositionSprite.setOriginCenter();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= zoomSpeed * delta;
            lastPositionSprite.setOriginCenter();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-speed * delta, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(speed * delta, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -speed * delta, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, speed * delta, 0);
        }

        cam.position.x = MathUtils.clamp(cam.position.x, 0, 100);
        cam.position.y = MathUtils.clamp(cam.position.y, 0, 100);

        cam.zoom = MathUtils.clamp(cam.zoom, 0.2f, 1f);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}


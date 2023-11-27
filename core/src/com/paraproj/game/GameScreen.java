package com.paraproj.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.paraproj.game.GameConstante;
import com.paraproj.game.Silveira;
import com.paraproj.game.SettingsScreen;
import com.paraproj.game.MenuScreen;
import com.paraproj.game.GameScreen;


public class GameScreen implements Screen {
    final Silveira game;
    private Stage stage;
    private Skin mySkin;

    static final int WORLD_WIDTH = 12;
    static final int WORLD_HEIGHT = 15;
    public Viewport screenPort;

    private Viewport viewport;
    public MyAssetManager myAssetManager = new MyAssetManager();
    private SpriteBatch batch;

    private Sprite mapSprite;
    private Sprite lastPositionSprite;
    private MenuScreen menuScreen;  // Variável para a tela de menu

    Vector2 worldCoordinates;
    Vector2 localPosicao;

    public GameScreen(final Silveira game){
        this.game = game;
        batch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false);
        screenPort = new ScreenViewport();
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        stage = new Stage(game.screenPort);
        mySkin = new Skin(Gdx.files.internal(GameConstante.skin));
        Gdx.input.setInputProcessor(stage);

        Button homeBtn = new TextButton("HOME", mySkin, "small");
        homeBtn.setSize(200, 50);
        homeBtn.setPosition(50,50);
        homeBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.gotoMenuScreen();
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                super.touchUp(event,x,y,pointer,button);
            }

        });

        stage.addActor(homeBtn);


    }
    Vector2 posicaoReal(Vector2 posicao, float zoom) {
        float particoes = 1.0f - zoom ;
        float x = posicao.x;
        float y = posicao.y;
        while((particoes > 0.0f)){;
            particoes -= 0.02f;
            x -= 0.01f;
            y -= 0.01f;
        }
        return new Vector2(x, y);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false);
        screenPort = new ScreenViewport();
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);

        // Definir a tela de menu como inicial

        mapSprite = new Sprite(new Texture("MapaUFSM.png"));
        mapSprite.setPosition(8, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        lastPositionSprite = new Sprite(new Texture("16x16.png"));
        lastPositionSprite.setSize(1f, 1f);
        lastPositionSprite.setOriginCenter();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
                worldCoordinates = new Vector2(screenX, screenY);
                viewport.unproject(worldCoordinates);

                lastPositionSprite.setOriginBasedPosition(worldCoordinates.x, worldCoordinates.y);
                localPosicao = posicaoReal(new Vector2(lastPositionSprite.getX(), lastPositionSprite.getY()), cam.zoom);
                System.out.println("Posição X: " + worldCoordinates.x + ", Posição Y: " + worldCoordinates.y);
                return true;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
                if(amountY > 0.1f)
                    cam.zoom -= 0.02f;
                else if(cam.zoom < 1.0f)
                    cam.zoom += 0.02f;

                return false;
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
        if (viewport != null) {
            viewport.update(width, height, true);
        }
        if (game.screenPort != null) {
            game.screenPort.update(width, height);
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapSprite.getTexture().dispose();
        lastPositionSprite.getTexture().dispose();
        batch.dispose();
        mySkin.dispose();
        stage.dispose();

    }
}

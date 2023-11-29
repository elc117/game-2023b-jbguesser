package com.paraproj.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class GameScreen implements Screen {
    final Silveira game;
    private final Stage stage;
    private final Skin mySkin;

    static final int WORLD_WIDTH = 12;
    static final int WORLD_HEIGHT = 15;
    public Viewport screenPort;

    private Viewport viewport;
    public MyAssetManager myAssetManager = new MyAssetManager();
    private SpriteBatch batch;
    private final ImageButton botaoMapa;
    private final ImageButton botaoSeta;
    private Stage cena;
    public boolean mapaRevelado = false;
    private Sprite mapSprite;
    private Sprite lastPositionSprite;
    private MenuScreen menuScreen;  // Variável para a tela de menu
    private InputMultiplexer multiplexadorInput = new InputMultiplexer();
    Vector2 worldCoordinates;
    Vector2 localPosicao;


    //setando as fotos
    public LocalFoto[] locais = {new LocalFoto (new Texture(Gdx.files.internal("fotos-ufsm/1.jpg")), 14.6785f, 15.5375f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/4.jpg")), 19.7897f, 20.4567f)};
    


    Texture atual = locais[MathUtils.random(0, 1)].foto;

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
        cena = new Stage();
        Button homeBtn = new TextButton("HOME", mySkin, "small");
        homeBtn.setSize(200, 50);
        homeBtn.setPosition(50,50);
        homeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.gotoMenuScreen();
            }
        });



        //Parte debaixo é para o botão de mapa
        Skin skin = new Skin();
        skin.add("botaoImagem", new Texture("MapaUFSM.png"));
        skin.setScale(.08f);
        ImageButton.ImageButtonStyle estilo = new ImageButton.ImageButtonStyle();
        estilo.imageUp = skin.getDrawable("botaoImagem");
        botaoMapa = new ImageButton(estilo);

        botaoMapa.setPosition(Gdx.graphics.getWidth() - botaoMapa.getWidth(), 0);

        botaoMapa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!mapaRevelado)
                    mostrarMapa();
            }
        });
        stage.addActor(botaoMapa);
        stage.addActor(homeBtn);

        //Botao de voltar
        skin.add("botaoSeta", new Texture("seta.png"));
        skin.setScale(.07f);
        ImageButton.ImageButtonStyle estiloBotao = new ImageButton.ImageButtonStyle();
        estiloBotao.imageUp = skin.getDrawable("botaoSeta");
        botaoSeta = new ImageButton(estiloBotao);

        botaoSeta.setPosition(Gdx.graphics.getWidth() / 2 - botaoMapa.getWidth() - 80, Gdx.graphics.getHeight() / 2 + 250);

        botaoSeta.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicado.");
                if(mapaRevelado)
                    mapaRevelado = false;
                Gdx.input.setInputProcessor(stage);
            }
        });
        cena.addActor(botaoSeta);
        multiplexadorInput.addProcessor(cena);
        multiplexadorInput.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
                worldCoordinates = new Vector2(screenX, screenY);
                viewport.unproject(worldCoordinates);
                lastPositionSprite.setOriginBasedPosition(worldCoordinates.x, worldCoordinates.y);
                System.out.println(lastPositionSprite.getX() + " " + lastPositionSprite.getY());
                localPosicao = posicaoReal(new Vector2(lastPositionSprite.getX(), lastPositionSprite.getY()), cam.zoom);
                System.out.println("Posição Real: " + localPosicao);
                return true;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
                System.out.println(lastPositionSprite.getX() + " " + lastPositionSprite.getY() + " cam zoom: " + cam.zoom);
                if(amountY > 0.1f)
                    cam.zoom -= 0.02f;
                else if(cam.zoom < 1.0f )
                    cam.zoom += 0.02f;
                if(cam.zoom == 1.0f)
                    System.out.println("Posicao Real: " + localPosicao);
                return true;
            }


        });

    }
    Vector2 posicaoReal(Vector2 posicao, float zoom) {
        float particoes = 1.0f - zoom ;
        float x = posicao.x;
        float y = posicao.y;
        while((particoes > 0.0f)){
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
        lastPositionSprite.setPosition(-5f, -5f);




    }

    public void mostrarMapa(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
                worldCoordinates = new Vector2(screenX, screenY);
                viewport.unproject(worldCoordinates);
                lastPositionSprite.setOriginBasedPosition(worldCoordinates.x, worldCoordinates.y);
                System.out.println(lastPositionSprite.getX() + " " + lastPositionSprite.getY());
                localPosicao = posicaoReal(new Vector2(lastPositionSprite.getX(), lastPositionSprite.getY()), cam.zoom);
                System.out.println("Posição Real: " + localPosicao);
                return true;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
                System.out.println(lastPositionSprite.getX() + " " + lastPositionSprite.getY() + " cam zoom: " + cam.zoom);
                if(amountY > 0.1f)
                    cam.zoom -= 0.02f;
                else if(cam.zoom < 1.0f )
                    cam.zoom += 0.02f;
                if(cam.zoom == 1.0f)
                    System.out.println("Posicao Real: " + localPosicao);
                return true;
            }


        });
        OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
        cam.zoom = 1f;
        mapaRevelado = true;
        Gdx.input.setInputProcessor(multiplexadorInput);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        if (mapaRevelado) {
            handleInput();
            lastPositionSprite.setBounds(lastPositionSprite.getX(), lastPositionSprite.getY(), cam.zoom, cam.zoom);
            lastPositionSprite.setOriginCenter();

            if (worldCoordinates != null) {
                lastPositionSprite.setOriginBasedPosition(worldCoordinates.x, worldCoordinates.y);
            }
            batch.draw(atual, 0,0, 27, WORLD_HEIGHT);
            mapSprite.draw(batch);
            lastPositionSprite.draw(batch);
            batch.end();
            cena.act(Gdx.graphics.getDeltaTime());
            cena.draw();
            //stage.act();
            //stage.draw();
        }
        else{
            batch.draw(atual, 0,0, 27, WORLD_HEIGHT);
            batch.end();
            stage.act();
            stage.draw();
            //cena.act(Gdx.graphics.getDeltaTime());
            //cena.draw();
        }
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
        cena.dispose();
    }
}

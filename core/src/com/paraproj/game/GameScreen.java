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
      public LocalFoto[] locais = {new LocalFoto (new Texture(Gdx.files.internal("fotos-ufsm/1.jpg")), 14.196047f, 7.209469f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/2.jpg")), 14.482136f, 4.342502f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/3.jpg")), 16.00464f ,5.042774f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/4.jpg")), 14.545418f, 3.2544363f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/5.jpg")), 14.071419f, 3.1552982f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/6.jpg")), 14.459236f, 3.0604706f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/7.jpg")), 14.57989f, 3.2501256f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/8.jpg")), 13.100085f, 3.2587466f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/9.jpg")), 13.058951f, 5.988091f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/10.jpg")), 13.535332f, 1.3667885f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/11.jpg")), 13.35866f, 0.20730548f), /*new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/12.jpg")), 14.129921f, 4.08052f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/13.jpg")), 13.2433405f, 7.489337f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/14.jpg")), 14.357611f, 6.0986986f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/15.jpg")), 13.199366f, 3.3805082f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/16.jpg")), 14.586755f, 3.1709447f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/17.jpg")), 13.013941f, 4.420935f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/18.jpg")), 14.082592f, 4.0890384f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/19.jpg")), 15.98805f ,7.48321f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/20.jpg")), 15.14347f, 1.9388535f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/21.jpg")), 8.98085f, 11.857717f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/22.jpeg")), 12.028959f, 4.152355f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/23.jpeg")), 13.034683f, 2.037026f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/24.jpg")), 14.366313f, 6.0190115f)*/};
   
      //public LocalFoto[] locais = {new LocalFoto (new Texture(Gdx.files.internal("fotos-ufsm/1.jpg")), 14.196047f, 7.209469f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/2.jpg")), 14.482136f, 4.342502f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/3.jpg")), 16.00464f ,5.042774f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/4.jpg")), 14.545418f, 3.2544363f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/5.jpg")), 14.071419f, 3.1552982f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/6.jpg")), 14.459236f, 3.0604706f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/7.jpg")), 14.57989f, 3.2501256f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/8.jpg")), 13.100085f, 3.2587466f), new LocalFoto(new Texture(Gdx.files.internal("fotos-ufsm/9.jpg")), 13.058951f, 5.988091f)};
      public int[] rpt = new int[4];
      public int numRod = 1;
      int reserva;

    Texture atual;

    public float Pontuacao(){
        float deltaX = localPosicao.x - locais[reserva].x;
        float deltaY = localPosicao.y - locais[reserva].y;
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);
        float media = (deltaX + deltaY) / 2f;
        float pontuacao = 5000f - (media * 1000f);
        if(pontuacao > 4950f)
            return 5000f;
        else if(pontuacao <= 0)
            return 0f;
        System.out.println("DeltaX = " + deltaX + " DeltaY = " + deltaY + " Media = " + media + " Pontuacao = " + pontuacao);
        return pontuacao;
    }

    public void Rodada(){ //Serve pra ver se é repetida ou não
        boolean repetido;
        reserva = MathUtils.random(0, 10);
        do {
            repetido = false;
            for (int i = 0; i < numRod; i++) {
                if (reserva == rpt[i])
                    repetido = true;
            }
            atual = locais[reserva].foto;
        }while(repetido);
        numRod++;
    }
    public GameScreen(final Silveira game){
        this.game = game;
        batch = new SpriteBatch();
        final OrthographicCamera camera = new OrthographicCamera();
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
                OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
                System.out.println("Clicado.");
                if (mapaRevelado)
                    mapaRevelado = false;
                cam.zoom = 1f;
                Gdx.input.setInputProcessor(stage);
            }
        });

        //Botao de confirmar
        Button confirmBtn = new TextButton("Confirmar", mySkin, "small");
        confirmBtn.setSize(200, 50);
        confirmBtn.setPosition(50,50);
        confirmBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(localPosicao != null)
                    game.gotoPontuacaoScreen(Pontuacao());
            }
        });
        cena.addActor(confirmBtn);
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
        Rodada();
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
        //lastPositionSprite.setSize(1, 50);
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
            lastPositionSprite.setBounds(lastPositionSprite.getX(), lastPositionSprite.getY(), cam.zoom, cam.zoom * 2.9f);
            lastPositionSprite.setOriginCenter();

            if (worldCoordinates != null) {
                lastPositionSprite.setOriginBasedPosition(worldCoordinates.x, worldCoordinates.y);
            }
            batch.draw(atual, 0,0, 27, WORLD_HEIGHT);
            mapSprite.draw(batch);
            //batch.draw(lastPositionSprite, 0, 0, 1 , 2);
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
	atual.dispose();
        mapSprite.getTexture().dispose();
        lastPositionSprite.getTexture().dispose();
        batch.dispose();
        mySkin.dispose();
        stage.dispose();
        cena.dispose();
    }
}

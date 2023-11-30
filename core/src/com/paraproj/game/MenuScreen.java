package com.paraproj.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import javax.print.attribute.TextSyntax;

public class MenuScreen implements Screen {

    final Silveira game;
    private Skin mySkin;
    private Stage stage;
    private Texture TelaFundo;
    float centerX = Gdx.graphics.getWidth() / 2f;
    float centerY = Gdx.graphics.getHeight() / 2f;

    public MenuScreen(final Silveira game) {
        this.game = game;
        TelaFundo = new Texture(Gdx.files.internal("MenuJogo.png"));
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json")); // Caminho do arquivo de skin pode variar
        stage = new Stage(game.screenPort);

        Label gameTitle = new Label("", mySkin, "big");
        gameTitle.setSize(200, 100);
        gameTitle.setPosition(400, 300); // Posicione conforme necessário
        gameTitle.setAlignment(Align.center);

        Button startBtn = new TextButton("Investigar", mySkin, "small");
        startBtn.setSize(200, 50);
        startBtn.setPosition(10, Gdx.graphics.getHeight() -  startBtn.getHeight()-200); // Posicione conforme necessário


        startBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoIntermediateScreen(); // Chama o método para iniciar o jogo
                return true;
            }
        });

        Button settingsBtn = new TextButton("Config", mySkin, "small");
        settingsBtn.setSize(200, 50);
        settingsBtn.setPosition(10, Gdx.graphics.getHeight() - startBtn.getHeight() - settingsBtn.getHeight() - 210); // Posicione conforme necessário
        settingsBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Implemente a lógica para ir para a tela de configurações
                return true;
            }
        });

        stage.addActor(gameTitle);
        stage.addActor(startBtn);
        stage.addActor(settingsBtn);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        game.batch.begin();
        game.batch.draw(TelaFundo, 0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        mySkin.dispose();
        stage.dispose();
    }
}

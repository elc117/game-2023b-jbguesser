package com.paraproj.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PontuacaoScreen implements Screen {
    final  Silveira game;
    private  Stage stage;
    private Texture img;
    private BitmapFont fonte;
    private Texture carta;
    private Skin mySkin;
    private SpriteBatch spriteBatch = new SpriteBatch();
    private CharSequence strVitoria;
    CharSequence str;
    public PontuacaoScreen(final Silveira game, final float pontuacao){
        this.game = game;
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        stage = new Stage(game.screenPort);
        img = new Texture(Gdx.files.internal("telapontuacao.png"));
        fonte = new BitmapFont();
        str = "   " +String.valueOf(pontuacao) + "\n\nPontuação Máxima:\n  5000";
        fonte.getData().setScale(2f);
        Button nextBnt = new TextButton("Next", mySkin, "small");
        nextBnt.setSize(200, 50);
        nextBnt.setPosition((float) Gdx.graphics.getWidth() /2, 10);
        strVitoria = "Você foi vitorioso!";
        nextBnt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(pontuacao > 4500f && !(Silveira.numRod >= 4))
                    game.gotoGameScreen();
                else
                    game.gotoMenuScreen();
            }
        });
        stage.addActor(nextBnt);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        System.out.println(str);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Cor de fundo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fonte.draw(game.batch, str,  Gdx.graphics.getWidth() / 2  - 90, Gdx.graphics.getHeight() / 2);
        if(GameScreen.numRod >= 4) {
            fonte.draw(game.batch, strVitoria, Gdx.graphics.getWidth() / 2 - 90, Gdx.graphics.getHeight() / 2 + 50);
            System.out.println("Sucesso");
        }
        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}

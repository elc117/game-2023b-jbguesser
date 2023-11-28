package com.paraproj.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class IntermediateScreen implements  Screen {
    final  Silveira game;
    private  Stage stage;
    private Texture img;

    private Texture carta;
    private Skin mySkin;

    public IntermediateScreen(final Silveira  game){
        this.game = game;
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        stage = new Stage(game.screenPort);
        img = new Texture(Gdx.files.internal("telacarta.jpg"));
         carta = new Texture((Gdx.files.internal("cartaSilveira.png")));

        Button nextBnt = new TextButton("NEXT", mySkin, "small");
        nextBnt.setSize(200, 50);
        nextBnt.setPosition((float) Gdx.graphics.getWidth() /2 + 90, 10);
        nextBnt.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.gotoGameScreen();
                return true;

            }
        });
        stage.addActor(nextBnt);

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Cor de fundo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float cartaX = (float) (Gdx.graphics.getWidth() - carta.getWidth()) / 2;
        float cartaY = (float) (Gdx.graphics.getHeight() - carta.getHeight()) / 2;
        game.batch.draw(carta, cartaX, cartaY);

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

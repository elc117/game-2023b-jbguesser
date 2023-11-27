package com.paraproj.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.paraproj.game.GameConstante;
import com.paraproj.game.Silveira;

public class SettingsScreen implements Screen {

    final Silveira game;
    private Skin mySkin;
    private Stage stage;

    public SettingsScreen(final Silveira game){
        this.game = game;
        mySkin = new Skin(Gdx.files.internal(GameConstante.skin));
        stage = new Stage(game.screenPort);
        Gdx.input.setInputProcessor(stage);

        Button homeBtn = new TextButton("HOME",mySkin,"small");
        homeBtn.setSize(GameConstante.col_width, GameConstante.row_heigth);
        homeBtn.setPosition(0,GameConstante.screenHeight - homeBtn.getHeight());
        homeBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoMenuScreen();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        stage.addActor(homeBtn);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        game.screenPort.update(width,height);

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
        mySkin.dispose();
        stage.dispose();

    }
}



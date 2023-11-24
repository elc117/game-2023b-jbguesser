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
import com.paraproj.game.GameConstante;
import com.paraproj.game.Silveira;

import java.awt.*;

public class MenuScreen implements Screen {

    final Silveira game;
    private Skin mySkin;
    private Stage stage;

    public MenuScreen(final Silveira game){
        this.game = game;
        mySkin = new Skin(Gdx.files.internal(GameConstante.skin));
        stage = new Stage(game.screenPort);
        Label gameTitulo = new Label("GAME MENU", mySkin, "big");
        gameTitulo.setSize(GameConstante.col_width*2, GameConstante.row_heigth*2);
        gameTitulo.setPosition(0, GameConstante.centerY);
        gameTitulo.setAlignment(Align.center);

        stage.addActor(gameTitulo);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,0,0);
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
     game.dispose();
    }
}

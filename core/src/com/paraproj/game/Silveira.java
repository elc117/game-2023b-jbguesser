package com.paraproj.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paraproj.game.SettingsScreen;
import com.paraproj.game.MenuScreen;
import com.paraproj.game.GameScreen;
import  com.paraproj.game.IntermediateScreen;


public class Silveira extends Game {
    public static int numRod = 0;
    SpriteBatch batch;
    public Viewport screenPort;
    public MyAssetManager myAssetManager = new MyAssetManager();

    @Override
    public void create () {
        batch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false);
        screenPort = new ScreenViewport();
        this.setScreen(new MenuScreen(this));
    }

    public void gotoMenuScreen(){
        numRod = 0;
        MenuScreen menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    public void gotoSettingsScreen(){
        SettingsScreen settingsScreen = new SettingsScreen(this);
        setScreen(settingsScreen);
    }

    public void gotoGameScreen(){
        GameScreen gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }

    public void gotoIntermediateScreen(){
        IntermediateScreen intermediateScreen = new IntermediateScreen(this);
        setScreen(intermediateScreen);
    }

    public void gotoPontuacaoScreen(float pontuacao) {
        PontuacaoScreen pontuacaoScreen = new PontuacaoScreen(this, pontuacao);
        setScreen(pontuacaoScreen);
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        super.dispose();
    }

}

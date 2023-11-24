package com.paraproj.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.paraproj.game.GameConstante;
public class MyAssetManager {
    public final AssetManager manager = new AssetManager();
    public void filaAddSkin(){
        SkinParameter parameter = new SkinParameter(GameConstante.skinAtlas);
        manager.load(GameConstante.skin, Skin.class,parameter);
    }
}

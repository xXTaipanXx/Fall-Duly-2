package com.darkbrokengames.fallduly2.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.GameData;

import java.util.Stack;

/*
КЛАСС МЕНЕДЖЕРА СЦЕН
*/
public class GameSceneManager {

    public Assets assets;
    private AdHandler handler;

    private Stack<Scene> scenes;

    public Array<String> sceneTextureNames;
    public Array<String> sceneSoundNames;
    public Array<String> sceneMusicNames;
    public Array<String> sceneAtlasNames;
    public Array<String> sceneMapNames;

    public GameSceneManager(AdHandler handler) {
        this.handler = handler;
        assets = new Assets();
        sceneTextureNames = new Array<String>();
        sceneSoundNames = new Array<String>();
        sceneMusicNames = new Array<String>();
        sceneAtlasNames = new Array<String>();
        sceneMapNames = new Array<String>();
        scenes = new Stack<Scene>();
        System.out.close();
    }

    public GameSceneManager() {
        assets = new Assets();
        sceneTextureNames = new Array<String>();
        sceneSoundNames = new Array<String>();
        sceneMusicNames = new Array<String>();
        sceneAtlasNames = new Array<String>();
        sceneMapNames = new Array<String>();
        scenes = new Stack<Scene>();
    }

    // Добавляем сцену.
    public void push(Scene scene){
        scenes.push(scene);
    }
    public void pop(){
        scenes.pop().dispose();

    }
    public void set(Scene scene){
        scenes.pop().dispose();
        scenes.push(scene);
    }

    public void loadScene(int scene){
        if (!scenes.empty())
            scenes.pop().dispose();
        assets.unload(sceneTextureNames);
        assets.unload(sceneSoundNames);
        assets.unload(sceneMusicNames);
        assets.unload(sceneAtlasNames);
        assets.unload(sceneMapNames);
        scenes.push(new LoadingScene(this, scene));
    }

    public void update(float delta){
        scenes.peek().update(delta);

    }

    public void render(SpriteBatch batch){
        scenes.peek().render(batch);

    }

    public void showBanner(boolean show){
        if (GameData.mode == 0)
            handler.showBanner(show);
    }
    public void showInterstitialAd(){
        if (GameData.mode == 0)
            handler.showInterstitialAd();
    }
    public void showRewardedAd(){
        if (GameData.mode == 0)
            handler.showRewardedAd();
    }
    public boolean interstitialAdIsLoaded(){
        if (GameData.mode == 0)
            return handler.interstitialAdIsLoaded();
        else
            return false;
    }
    public boolean rewardedAdIsLoaded(){
        if (GameData.mode == 0)
            return handler.rewardedAdIsLoaded();
        else
            return false;
    }

}

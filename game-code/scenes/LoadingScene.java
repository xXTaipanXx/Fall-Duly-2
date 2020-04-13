package com.darkbrokengames.fallduly2.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Json;
import com.darkbrokengames.fallduly2.utils.SceneResourceDataList;

public class LoadingScene extends Scene {

    private String sceneName;
    private int sceneId;
    private int sceneType;

    private int loadingStep = 0;
    private String loadingResourceName;


    public LoadingScene(GameSceneManager gsm, int scene) {
        super(gsm);
        sceneId = scene;
        loadResources();
    }

    public void loadResources(){
        Json json = new Json();
        FileHandle sceneResourceDataFile = Gdx.files.internal("json/SceneResourceDataList.json");
        SceneResourceDataList sceneResourceDataList = json.fromJson(SceneResourceDataList.class, sceneResourceDataFile);
        gsm.sceneTextureNames.clear();
        gsm.sceneTextureNames.addAll(sceneResourceDataList.SCENE_RESOURCE_DATA.get(sceneId).TEXTURE_NAMES);
        gsm.sceneSoundNames.clear();
        gsm.sceneSoundNames.addAll(sceneResourceDataList.SCENE_RESOURCE_DATA.get(sceneId).SOUND_NAMES);
        gsm.sceneMusicNames.clear();
        gsm.sceneMusicNames.addAll(sceneResourceDataList.SCENE_RESOURCE_DATA.get(sceneId).MUSIC_NAMES);
        gsm.sceneAtlasNames.clear();
        gsm.sceneAtlasNames.addAll(sceneResourceDataList.SCENE_RESOURCE_DATA.get(sceneId).ATLAS_NAMES);
        gsm.sceneMapNames.clear();
        gsm.sceneMapNames.addAll(sceneResourceDataList.SCENE_RESOURCE_DATA.get(sceneId).MAP_NAMES);
        sceneType = sceneResourceDataList.SCENE_RESOURCE_DATA.get(sceneId).SCENE_ID;
        sceneName = sceneResourceDataList.SCENE_RESOURCE_DATA.get(sceneId).SCENE_NAME;
        loadingResourceName = "Textures";
        gsm.assets.load(gsm.sceneTextureNames, Texture.class);
    }

    @Override
    public void update(float deltaTime) {

        switch (loadingStep){
            case 0:
                if (gsm.assets.manager.update()){
                    gsm.assets.manager.finishLoading();
                    loadingResourceName = "Sounds";
                    gsm.assets.load(gsm.sceneSoundNames, Sound.class);
                    loadingStep++;
                }
                break;
            case 1:
                if (gsm.assets.manager.update()){
                    gsm.assets.manager.finishLoading();
                    loadingResourceName = "Music";
                    gsm.assets.load(gsm.sceneMusicNames, Music.class);
                    loadingStep++;
                }
                break;
            case 2:
                if (gsm.assets.manager.update()){
                    gsm.assets.manager.finishLoading();
                    loadingResourceName = "Other";
                    gsm.assets.load(gsm.sceneAtlasNames, TextureAtlas.class);
                    loadingStep++;
                }
                break;
            case 3:
                if (gsm.assets.manager.update()){
                    gsm.assets.manager.finishLoading();
                    gsm.assets.load("maps/" + gsm.sceneMapNames.get(0) + "/" + gsm.sceneMapNames.get(0) + ".tmx", TiledMap.class);
                    loadingStep++;
                }
                break;
            case 4:
                if (gsm.assets.manager.update()){
                    gsm.assets.manager.finishLoading();
                    switch (sceneId){
                      //  case Scene.startScene:
                       //     gsm.set(new StartScene(gsm));
                       //     break;
                       // case Scene.playScene:
                       //     gsm.set(new PlayScene(gsm));
                       //     break;
                       // case Scene.mapEditorScene:
                       //     gsm.set(new MapEditorScene(gsm));
                          //  break;
                    }
                    loadingStep++;
                }
                break;
        }

     //   if (!gsm.assets.manager.update()) {
//
       // } else {
        //    gsm.assets.manager.finishLoading();
       //     switch (sceneId){
        //        case Scene.startScene:
        //            gsm.set(new StartScene(gsm));
        //            break;
        //        case Scene.playScene:
        //            gsm.set(new PlayScene(gsm));
        //            break;
        //    }
        //}
    }

    @Override
    public void render(SpriteBatch batch) {

    }

   // @Override
   // public void renderUI(SpriteBatch batch) {
        //gsm.assets.scoreFont.draw(batch, "Loading " + loadingResourceName + "... " + gsm.assets.manager.getProgress() * 100f + "%", 0,0, 0, 1, true);
   // }

   // @Override
   // public void renderBackGroundUI(SpriteBatch batch) {

   // }

    @Override
    public void dispose() {

    }
}

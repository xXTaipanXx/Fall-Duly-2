package com.darkbrokengames.fallduly2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.darkbrokengames.fallduly2.utils.PhysicalObjectData;
import com.darkbrokengames.fallduly2.utils.PhysicalObjectDataList;
import com.darkbrokengames.fallduly2.utils.Translation;
import com.darkbrokengames.fallduly2.utils.Translations;

import java.util.Locale;

public class Assets {
    public AssetManager manager;

    public final static float CAMERA_VIEW_SCALE = 32f;
    public static float UnitScale;

    public BitmapFont scoreFont;
    public BitmapFont messageFont;
    public BitmapFont messageFont2;
    public BitmapFont bestScoreFont;
    public BitmapFont lifeFont;
    public BitmapFont moneyFont;
    public BitmapFont bonusMoneyFont;
    public BitmapFont pageFont;
    public BitmapFont backPageFont;
    public BitmapFont priceFont;
    public BitmapFont shopFont;

    public Translation translation;

    public Assets() {
        manager = new AssetManager();
    }

    public void firstLoad() {

        load("textures/DarkBrokenGamesA.png", Texture.class);
        load("textures/DarkBrokenGamesB.png", Texture.class);
        load("textures/FallDuly2.png", Texture.class);
        load("textures/ButtonPlay.png", Texture.class);
        load("textures/ButtonExit.png", Texture.class);
        load("textures/ButtonShop.png", Texture.class);
        load("textures/ButtonSoundOn.png", Texture.class);
        load("textures/ButtonMusicOn.png", Texture.class);
        load("textures/ButtonSoundOff.png", Texture.class);
        load("textures/ButtonMusicOff.png", Texture.class);
        load("textures/MessageBox.png", Texture.class);
        load("textures/ButtonPause.png", Texture.class);
        load("textures/ButtonYes.png", Texture.class);
        load("textures/ButtonNo.png", Texture.class);
        load("textures/ButtonOk.png", Texture.class);
        load("textures/ButtonBack.png", Texture.class);
        load("textures/ButtonBackPage.png", Texture.class);
        load("textures/ButtonNextPage.png", Texture.class);
        load("textures/ButtonWatchTheVideo.png", Texture.class);
        load("textures/ButtonItemShop.png", Texture.class);
        load("textures/ButtonCurrentItemShop.png", Texture.class);
        load("textures/BuyImage.png", Texture.class);
        load("textures/SpawnBox.png", Texture.class);
        load("textures/SpawnBoxLine.png", Texture.class);

        load("textures/flags/USA.png", Texture.class);
        load("textures/flags/Russia.png", Texture.class);
        load("textures/flags/Germany.png", Texture.class);
        load("textures/flags/France.png", Texture.class);
        load("textures/flags/Spain.png", Texture.class);


        load("audio/Sound01.wav", Sound.class);
        load("audio/Sound02.wav", Sound.class);
        load("audio/Sound03.wav", Sound.class);
        load("audio/Music.wav", Music.class);
        load("textures/Square16x16.png", Texture.class);
        load("skins/PlayerSkins.atlas", TextureAtlas.class);
        load("skins/BlockSkins.atlas", TextureAtlas.class);
        while (!manager.update()) {
        }
        manager.finishLoading();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters += "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.size = (int) (5f * Assets.UnitScale);
        parameter.color = Color.WHITE;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        scoreFont = generator.generateFont(parameter);
        parameter.size = (int) (2.5f * Assets.UnitScale);
        parameter.color = Color.BLACK;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = new Color(0f, 0f, 0f, 0.25f);
        messageFont = generator.generateFont(parameter);
        parameter.size = (int) (2f * Assets.UnitScale);
        parameter.color = Color.GRAY;
        messageFont2 = generator.generateFont(parameter);
        parameter.size = (int) (2f * Assets.UnitScale);
        parameter.color = Color.ORANGE;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = new Color(0f, 0f, 0f, 0.75f);
        bestScoreFont = generator.generateFont(parameter);
        parameter.size = (int) (1.5f * Assets.UnitScale);
        parameter.color = Color.WHITE;
        lifeFont = generator.generateFont(parameter);
        parameter.size = (int) (2f * Assets.UnitScale);
        moneyFont = generator.generateFont(parameter);
        parameter.size = (int) (1f * Assets.UnitScale);
        parameter.color = Color.GREEN;
        bonusMoneyFont = generator.generateFont(parameter);
        parameter.size = (int) (3.25f * Assets.UnitScale);
        parameter.color = Color.WHITE;
        pageFont = generator.generateFont(parameter);
        parameter.size = (int) (1.75f * Assets.UnitScale);
        parameter.color = Color.WHITE;
        priceFont = generator.generateFont(parameter);
        parameter.size = (int) (2.5f * Assets.UnitScale);
        parameter.color = Color.GRAY;
        backPageFont = generator.generateFont(parameter);
        parameter.size = (int) (3f * Assets.UnitScale);
        parameter.color = Color.WHITE;
        shopFont = generator.generateFont(parameter);
        generator.dispose();

        changeTranslation(GameData.getLanguage());
    }

    public void changeTranslation(String language){
        Json json = new Json();
        FileHandle file = Gdx.files.internal("json/Translations.json");
        Translations translationsList = json.fromJson(Translations.class, file);
        translation = translationsList.TRANSLATIONS.get(0);
        for (int i = 0; i < translationsList.TRANSLATIONS.size; i++) {
            if (translationsList.TRANSLATIONS.get(i).LANGUAGE.equals(language)) {
                translation = translationsList.TRANSLATIONS.get(i);
                break;
            }
        }
    }

    public void load(String resourceName, Class type) {
        if (!manager.isLoaded(resourceName) && !resourceName.equals(""))
            manager.load(resourceName, type);
        manager.finishLoading();
    }

    public void load(Array<String> resourceNames, Class type) {
        if (!resourceNames.isEmpty()) {
            for (String resourceName : resourceNames) {
                if (!manager.isLoaded(resourceName) && !resourceName.equals(""))
                    manager.load(resourceName, type);
            }
        }
    }

    public Object get(String resourceName){
        return manager.get(resourceName);
    }

    public Texture getTexture(String textureName, boolean isLinear){
        Texture texture = manager.get(textureName, Texture.class);
        if (isLinear)
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        else
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return texture;
    }
    public TextureAtlas getTextureAtlas(String textureAtlasName){
        return manager.get(textureAtlasName, TextureAtlas.class);
    }
    public Sound getSound(String soundName){
        return manager.get(soundName, Sound.class);
    }

    public void unload(Array<String> resourceNames){
        if (!resourceNames.isEmpty()) {
            for (String resourceName : resourceNames)
                if (manager.isLoaded(resourceName) && !resourceName.equals(""))
                    manager.unload(resourceName);
        }
    }

    public void dispose() {
        manager.dispose();
    }
}

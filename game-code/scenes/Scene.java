package com.darkbrokengames.fallduly2.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.FlashEffect;

public abstract class Scene {

    protected OrthographicCamera camera; // Ортографическая камера.

    protected GameSceneManager gsm; // Менеджер сцен.

    protected float aspectRatio;

    public Scene (GameSceneManager gsm){
        this.gsm = gsm;

        aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth(); // Соотношение сторон h/w.
        camera = new OrthographicCamera(Assets.CAMERA_VIEW_SCALE,  Assets.CAMERA_VIEW_SCALE * aspectRatio); // Устанавливаем размер камеры.
    }

    public abstract void update (float deltaTime);

    public abstract void render (SpriteBatch batch);

    // Удаляем ресурсы, созданные в этой сцене.
    public abstract void dispose();
}

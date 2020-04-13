package com.darkbrokengames.fallduly2.scenes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.darkbrokengames.fallduly2.FlashEffect;

public class StartScene extends Scene {

    private Sprite darkBrokenGamesA, darkBrokenGamesB;

    private FlashEffect transitBetweenScenes;

    private int step = 0;

    private Stage stage;

    public StartScene(GameSceneManager gsm) {
        super(gsm);
        //gsm.showBanner(false);

        darkBrokenGamesA = new Sprite(gsm.assets.getTexture("textures/DarkBrokenGamesA.png", true));
        darkBrokenGamesA.setSize(32f, 16f);
        darkBrokenGamesA.setCenter(0f, 0f);
        darkBrokenGamesB = new Sprite(gsm.assets.getTexture("textures/DarkBrokenGamesB.png", true));
        darkBrokenGamesB.setSize(32f, 16f);
        darkBrokenGamesB.setCenter(0f, 0f);
        darkBrokenGamesB.setColor(1f,1f,1f,0f);

        stage = new Stage();

        transitBetweenScenes = new FlashEffect(gsm.assets.getTexture("textures/Square16x16.png", false), stage);
        transitBetweenScenes.begin(FlashEffect.fadeOut, 1f);
    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);
        if (transitBetweenScenes.isEnd()) {
            switch (step) {
                case 0:
                    darkBrokenGamesB.setColor(1, 1, 1, darkBrokenGamesB.getColor().a + (deltaTime * 1f / 1.5f));
                    if (darkBrokenGamesB.getColor().a >= 1f)
                        step++;
                        break;
                case 1:
                    darkBrokenGamesB.setColor(1, 1, 1, darkBrokenGamesB.getColor().a - (deltaTime * 1f / 1.5f));
                    if (darkBrokenGamesB.getColor().a <= 0f)
                        step++;
                    break;
                case 2:
                    transitBetweenScenes.begin(FlashEffect.fadeIn, 1f);
                    step++;
                    break;
                 case 3:
                     if (transitBetweenScenes.isEnd())
                         gsm.set(new MenuScene(gsm));
                     break;

            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        darkBrokenGamesA.draw(batch);
        darkBrokenGamesB.draw(batch);
        batch.end();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}

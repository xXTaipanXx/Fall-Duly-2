package com.darkbrokengames.fallduly2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.darkbrokengames.fallduly2.objects.DefaultEffectButtonListener;

public class LanguageWindow extends Group {
    private Image background, window, selectedFlag;
    private Button button1, enButton, ruButton, deButton, frButton, esButton;
    private Label titleLabel;

    public LanguageWindow(final Assets assets, Stage stage){
        background = new Image(assets.getTexture("textures/Square16x16.png", false));
        background.setSize(stage.getWidth(), stage.getHeight());
        background.setColor(0f,0f,0f,0.5f);
        background.setOrigin(Align.center);
        background.setPosition(0f, 0f);
        addActor(background);

        window = new Image(assets.getTexture("textures/MessageBox.png", false));
        window.setSize(28f * Assets.UnitScale, 21f * Assets.UnitScale);
        window.setOrigin(Align.center);
        window.setPosition(background.getWidth() / 2f, background.getHeight() / 2f, Align.center);
        addActor(window);

        titleLabel = new Label(assets.translation.CHOOSE_YOUR_LANGUAGE, new Label.LabelStyle(assets.messageFont, Color.WHITE));
        titleLabel.setAlignment(Align.center);
        titleLabel.setPosition(background.getWidth() / 2f, background.getHeight() / 2f + 7.5f * Assets.UnitScale, Align.center);
        addActor(titleLabel);

        selectedFlag = new Image(assets.getTexture("textures/Square16x16.png", false));
        selectedFlag.setSize(5f * Assets.UnitScale, 3.333f * Assets.UnitScale);
        selectedFlag.setColor(0f,0f,1f,1f);
        selectedFlag.setOrigin(Align.center);
        selectedFlag.setPosition(stage.getWidth() / 2f - 8f * Assets.UnitScale, stage.getHeight() / 2f + 2.5f * Assets.UnitScale, Align.center);
        addActor(selectedFlag);

        enButton = new Button(new Button.ButtonStyle());
        enButton.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/flags/USA.png", true));
        enButton.setSize(4.5f * Assets.UnitScale, 3f * Assets.UnitScale);
        enButton.setPosition(stage.getWidth() / 2f - 8f * Assets.UnitScale, stage.getHeight() / 2f + 2.5f * Assets.UnitScale, Align.center);
        enButton.setOrigin(Align.center);
        enButton.setTransform(true);
        enButton.addListener(new DefaultEffectButtonListener(enButton));
        enButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                selectedFlag.setPosition(enButton.getX() + enButton.getWidth() / 2f, enButton.getY() + enButton.getHeight() / 2f, Align.center);
                assets.changeTranslation("en");
                titleLabel.setText(assets.translation.CHOOSE_YOUR_LANGUAGE);
                GameData.setLanguage("en");
            }
        });
        addActor(enButton);

        ruButton = new Button(new Button.ButtonStyle());
        ruButton.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/flags/Russia.png", true));
        ruButton.setSize(4.5f * Assets.UnitScale, 3f * Assets.UnitScale);
        ruButton.setPosition(stage.getWidth() / 2f + 0 * Assets.UnitScale, stage.getHeight() / 2f + 2.5f * Assets.UnitScale, Align.center);
        ruButton.setOrigin(Align.center);
        ruButton.setTransform(true);
        ruButton.addListener(new DefaultEffectButtonListener(ruButton));
        ruButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                selectedFlag.setPosition(ruButton.getX() + enButton.getWidth() / 2f, ruButton.getY() + enButton.getHeight() / 2f, Align.center);
                assets.changeTranslation("ru");
                titleLabel.setText(assets.translation.CHOOSE_YOUR_LANGUAGE);
                GameData.setLanguage("ru");
            }
        });
        addActor(ruButton);

        deButton = new Button(new Button.ButtonStyle());
        deButton.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/flags/Germany.png", true));
        deButton.setSize(4.5f * Assets.UnitScale, 3f * Assets.UnitScale);
        deButton.setPosition(stage.getWidth() / 2f + 8f * Assets.UnitScale, stage.getHeight() / 2f + 2.5f * Assets.UnitScale, Align.center);
        deButton.setOrigin(Align.center);
        deButton.setTransform(true);
        deButton.addListener(new DefaultEffectButtonListener(deButton));
        deButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                selectedFlag.setPosition(deButton.getX() + enButton.getWidth() / 2f, deButton.getY() + enButton.getHeight() / 2f, Align.center);
                assets.changeTranslation("de");
                titleLabel.setText(assets.translation.CHOOSE_YOUR_LANGUAGE);
                GameData.setLanguage("de");
            }
        });
        addActor(deButton);

        frButton = new Button(new Button.ButtonStyle());
        frButton.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/flags/France.png", true));
        frButton.setSize(4.5f * Assets.UnitScale, 3f * Assets.UnitScale);
        frButton.setPosition(stage.getWidth() / 2f - 8f * Assets.UnitScale, stage.getHeight() / 2f - 2.25f * Assets.UnitScale, Align.center);
        frButton.setOrigin(Align.center);
        frButton.setTransform(true);
        frButton.addListener(new DefaultEffectButtonListener(frButton));
        frButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                selectedFlag.setPosition(frButton.getX() + enButton.getWidth() / 2f, frButton.getY() + enButton.getHeight() / 2f, Align.center);
                assets.changeTranslation("fr");
                titleLabel.setText(assets.translation.CHOOSE_YOUR_LANGUAGE);
                GameData.setLanguage("fr");
            }
        });
        addActor(frButton);

        esButton = new Button(new Button.ButtonStyle());
        esButton.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/flags/Spain.png", true));
        esButton.setSize(4.5f * Assets.UnitScale, 3f * Assets.UnitScale);
        esButton.setPosition(stage.getWidth() / 2f + 0f * Assets.UnitScale, stage.getHeight() / 2f - 2.25f * Assets.UnitScale, Align.center);
        esButton.setOrigin(Align.center);
        esButton.setTransform(true);
        esButton.addListener(new DefaultEffectButtonListener(esButton));
        esButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                selectedFlag.setPosition(esButton.getX() + enButton.getWidth() / 2f, esButton.getY() + enButton.getHeight() / 2f, Align.center);
                assets.changeTranslation("es");
                titleLabel.setText(assets.translation.CHOOSE_YOUR_LANGUAGE);
                GameData.setLanguage("es");
            }
        });
        addActor(esButton);

        if (GameData.getLanguage().equals("en"))
            selectedFlag.setPosition(enButton.getX() + enButton.getWidth() / 2f, enButton.getY() + enButton.getHeight() / 2f, Align.center);
        else if (GameData.getLanguage().equals("ru"))
            selectedFlag.setPosition(ruButton.getX() + enButton.getWidth() / 2f, ruButton.getY() + enButton.getHeight() / 2f, Align.center);
        else if (GameData.getLanguage().equals("de"))
            selectedFlag.setPosition(deButton.getX() + enButton.getWidth() / 2f, deButton.getY() + enButton.getHeight() / 2f, Align.center);
        else if (GameData.getLanguage().equals("fr"))
            selectedFlag.setPosition(frButton.getX() + enButton.getWidth() / 2f, frButton.getY() + enButton.getHeight() / 2f, Align.center);
        else if (GameData.getLanguage().equals("es"))
            selectedFlag.setPosition(esButton.getX() + enButton.getWidth() / 2f, esButton.getY() + enButton.getHeight() / 2f, Align.center);

        button1 = new Button(new Button.ButtonStyle());
        button1.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/ButtonOk.png", true));
        button1.setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
        button1.setPosition(stage.getWidth() / 2f + 0 * Assets.UnitScale, stage.getHeight() / 2f - 6.75f * Assets.UnitScale, Align.center);
        button1.setOrigin(Align.center);
        button1.setTransform(true);
        button1.addListener(new DefaultEffectButtonListener(button1));
        button1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                hide(0.75f);
            }
        });
        addActor(button1);

        setVisible(false);
    }

    public Label getTitleLabel(){
        return titleLabel;
    }


    public Button getButton1(){
        return button1;
    }

    public void show(float duration){
        setVisible(true);
        setTouchable(Touchable.disabled);
        titleLabel.setVisible(false);
        background.setScale(0f, 0f);
        background.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        window.setScale(0f, 0f);
        window.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        enButton.setScale(0f, 0f);
        enButton.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        ruButton.setScale(0f, 0f);
        ruButton.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        deButton.setScale(0f, 0f);
        deButton.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        frButton.setScale(0f, 0f);
        frButton.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        esButton.setScale(0f, 0f);
        esButton.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        button1.setScale(0f, 0f);
        button1.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        selectedFlag.setScale(0f, 0f);
        selectedFlag.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                titleLabel.setVisible(true);
                setTouchable(Touchable.enabled);
            }
        }, duration * 0.75f);
        timer.start();
    }

    public void hide(float duration){
        setTouchable(Touchable.disabled);
        background.setScale(1f, 1f);
        background.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        window.setScale(1f, 1f);
        window.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        enButton.setScale(1f, 1f);
        enButton.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        ruButton.setScale(1f, 1f);
        ruButton.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        deButton.setScale(1f, 1f);
        deButton.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        frButton.setScale(1f, 1f);
        frButton.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        esButton.setScale(1f, 1f);
        esButton.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        button1.setScale(1f, 1f);
        button1.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        selectedFlag.setScale(1f, 1f);
        selectedFlag.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        titleLabel.setVisible(false);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                setVisible(false);
            }
        }, duration);
        timer.start();
    }
}

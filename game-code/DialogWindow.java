package com.darkbrokengames.fallduly2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.darkbrokengames.fallduly2.objects.DefaultEffectButtonListener;
import com.sun.org.apache.regexp.internal.RE;

public class DialogWindow extends Group{

    private Image background;
    private Image window;
    private Button button1, button2;
    private Label titleLabel;
    private Label descriptionLabel;


    static public final int justOk = 0;
    static public final int cancelAndOk = 1;

    public DialogWindow(Assets assets, Stage stage, int type){
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

        titleLabel = new Label("", new Label.LabelStyle(assets.messageFont, Color.WHITE));
        titleLabel.setAlignment(Align.center);
        titleLabel.setPosition(background.getWidth() / 2f, background.getHeight() / 2f + 7.5f * Assets.UnitScale, Align.center);
        addActor(titleLabel);

        descriptionLabel = new Label("", new Label.LabelStyle(assets.messageFont, Color.WHITE));
        descriptionLabel.setAlignment(Align.center);
        descriptionLabel.setWrap(true);
        descriptionLabel.setSize(27f * Assets.UnitScale, 10f * Assets.UnitScale);
        descriptionLabel.setPosition(background.getWidth() / 2f, background.getHeight() / 2f + 1f * Assets.UnitScale, Align.center);
        addActor(descriptionLabel);

        switch (type){
            case 0:
                button1 = new Button(new Button.ButtonStyle());
                button1.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/ButtonOk.png", true));
                button1.setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
                button1.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f - 6f * Assets.UnitScale, Align.center);
                button1.setOrigin(Align.center);
                button1.setTransform(true);
                button1.addListener(new DefaultEffectButtonListener(button1));
                addActor(button1);
                break;
            case 1:
                button1 = new Button(new Button.ButtonStyle());
                button1.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/ButtonNo.png", true));
                button1.setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
                button1.setPosition(stage.getWidth() / 2f - 7.5f * Assets.UnitScale, stage.getHeight() / 2f - 6f * Assets.UnitScale, Align.center);
                button1.setOrigin(Align.center);
                button1.setTransform(true);
                button1.addListener(new DefaultEffectButtonListener(button1));
                addActor(button1);

                button2 = new Button(new Button.ButtonStyle());
                button2.getStyle().up = new TextureRegionDrawable(assets.getTexture("textures/ButtonYes.png", true));
                button2.setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
                button2.setPosition(stage.getWidth() / 2f + 7.5f * Assets.UnitScale, stage.getHeight() / 2f - 6f * Assets.UnitScale, Align.center);
                button2.setOrigin(Align.center);
                button2.setTransform(true);
                button2.addListener(new DefaultEffectButtonListener(button2));
                addActor(button2);
                break;
        }
        setVisible(false);
    }

    public Label getTitleLabel(){
        return titleLabel;
    }

    public Label getDescriptionLabel(){
        return descriptionLabel;
    }

    public Button getButton1(){
        return button1;
    }
    public Button getButton2(){
        if (button2 != null)
            return button2;
        else
            return button1;
    }

    public void show(float duration){
        setVisible(true);
        setTouchable(Touchable.disabled);
        titleLabel.setVisible(false);
        descriptionLabel.setVisible(false);
        background.setScale(0f, 0f);
        background.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        window.setScale(0f, 0f);
        window.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        button1.setScale(0f, 0f);
        button1.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        if (button2 != null) {
            button2.setScale(0f, 0f);
            button2.addAction(Actions.scaleTo(1f, 1f, duration, Interpolation.circle));
        }
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                titleLabel.setVisible(true);
                descriptionLabel.setVisible(true);
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
        button1.setScale(1f, 1f);
        button1.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        if (button2 != null) {
            button2.setScale(1f, 1f);
            button2.addAction(Actions.scaleTo(0f, 0f, duration, Interpolation.circle));
        }
        titleLabel.setVisible(false);
        descriptionLabel.setVisible(false);
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

package com.darkbrokengames.fallduly2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class FlashEffect {

    private Image screenPanel;

    static public final int fadeIn = 0;

    static public final int fadeOut = 1;

    static public final int flash = 2;

    public FlashEffect(Texture texture, Stage stage){
        screenPanel = new Image(new TextureRegionDrawable(texture));
        screenPanel.setSize(stage.getWidth(), stage.getHeight());
        screenPanel.setColor(new Color(0, 0, 0, 0));
        screenPanel.setTouchable(Touchable.disabled);
        stage.addActor(screenPanel);
    }

    public FlashEffect(Texture texture, Color color, Stage stage){
        screenPanel = new Image(new TextureRegionDrawable(texture));
        screenPanel.setSize(stage.getWidth(), stage.getHeight());
        screenPanel.setColor(color);
        screenPanel.setTouchable(Touchable.disabled);
        stage.addActor(screenPanel);
    }

    public void setColor(Color color){
        screenPanel.setColor(color.r, color.g, color.b, screenPanel.getColor().a);
    }

    public void begin(int mode, float duration1){
        switch (mode) {
            case fadeIn:
                screenPanel.setColor(screenPanel.getColor().r, screenPanel.getColor().g, screenPanel.getColor().b, 0f);
                screenPanel.addAction(Actions.fadeIn(duration1));
                break;
            case fadeOut:
                screenPanel.setColor(screenPanel.getColor().r, screenPanel.getColor().g, screenPanel.getColor().b, 1f);
                screenPanel.addAction(Actions.fadeOut(duration1));
                break;
            case flash:
                screenPanel.setColor(screenPanel.getColor().r, screenPanel.getColor().g, screenPanel.getColor().b, 0f);
                screenPanel.addAction(Actions.sequence(Actions.fadeOut(duration1), Actions.fadeIn(duration1)));
                break;
        }
    }

    public void begin(int mode, float duration1, float duration2){
        switch (mode) {
            case fadeIn:
                screenPanel.setColor(screenPanel.getColor().r, screenPanel.getColor().g, screenPanel.getColor().b, 0f);
                screenPanel.addAction(Actions.fadeIn(duration1));
                break;
            case fadeOut:
                screenPanel.setColor(screenPanel.getColor().r, screenPanel.getColor().g, screenPanel.getColor().b, 1f);
                screenPanel.addAction(Actions.fadeOut(duration1));
                break;
            case flash:
                screenPanel.setColor(screenPanel.getColor().r, screenPanel.getColor().g, screenPanel.getColor().b, 0f);
                screenPanel.addAction(Actions.sequence(Actions.fadeIn(duration1), Actions.fadeOut(duration2)));
                break;
        }
    }

    public boolean isEnd(){
        return screenPanel.getActions().isEmpty();
    }
}

package com.darkbrokengames.fallduly2.objects;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class DefaultEffectButtonListener extends InputListener {

    private Button button1;

    public DefaultEffectButtonListener(Button button1){
        this.button1 = button1;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        button1.setColor(0.75f, 0.75f, 0.75f, 1f);
        return true;
    }
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        button1.setColor(1f, 1f, 1f, 1f);
    }
}

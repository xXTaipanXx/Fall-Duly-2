package com.darkbrokengames.fallduly2.objects;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.GameData;
import com.darkbrokengames.fallduly2.scenes.ShopMessage;

public class ShopButton extends Button {

    private int id = -1;
    private int page = 1;

    private ShopMessage shopMessage;

    public ShopButton(Drawable up, ShopMessage shopMessage, final Assets assets) {
        super(up);
        this.shopMessage = shopMessage;
        setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
        setOrigin(Align.center);
        setTransform(true);
        setRotation(45f);

        this.shopMessage = shopMessage;
        addListener(new DefaultEffectButtonListener(this));
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                buy();
            }
        });

    }

    private void buy(){
        if (!GameData.getBoughtPlayer(id)) {
            if (GameData.getMoney() >= GameData.playerPrice[id]) {
                shopMessage.setCurrentIdForBuy(id);
                shopMessage.showMessageBuy();
            } else {
                shopMessage.showMessageNoMoney();
            }
        }
        else{
            GameData.setCurrentPlayerID(id);
            GameData.setCurrentPage(page);
        }
    }

    public void setId(int id){
        this.id = id;

    }
    public void setPage(int page){
        this.page = page;

    }
}

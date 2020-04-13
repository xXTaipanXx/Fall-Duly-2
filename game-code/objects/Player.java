package com.darkbrokengames.fallduly2.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.GameData;

public class Player extends PhysicalObject {
    private int contactZone = 0; // 0 - nothing, 1 - winZone, 2 - deathZone.

    private int contactSpecialBlock = 0; // 0 - defaultBlock, 1 - greenBlock, 2 - purpleBlock, 3 - redBlock, 4 - blueBlock, 5 - goldBlock, 6 - whiteBlock.

    public Player(Vector2 position, World world, Assets assets) {
        super(assets, world, "Player");
        getBody().setTransform(new Vector2(position), 0f);
        getSprite().setRegion(assets.getTextureAtlas("skins/PlayerSkins.atlas").findRegion("Player" + GameData.getCurrentPlayerID()));
        getBody().setGravityScale(6f); //Устанавлием скорость падения игрока (гравитацию)
    }
    // Устанавлиаем контакт игрока с зоной.
    public void setContactZone(int contactZone){
        this.contactZone = contactZone;

    }
    // Получаем контакт игрока с зоной.
    public int getContactZone(){
        return contactZone;

    }
    // Устанавливаем контакт игрока с блоком.
    public void setContactSpecialBlock(int contactSpecialBlock){
        this.contactSpecialBlock = contactSpecialBlock;

    }
    // Получаем контакт игрока с блоком.
    public int getContactSpecialBlock(){
        return contactSpecialBlock;

    }
}

package com.darkbrokengames.fallduly2.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.GameData;

public class Block extends PhysicalObject{
    private int specialBlockId = 0;

    private boolean wasSound = false; // Произведен звук?
    private boolean isSpecialBlock = false; // Статус блока.

    private TextureAtlas textureAtlas;

    private Sound sound;

    public Block(Vector2 position, World world, Assets assets) {
        super(assets, world, "Block");
        setTransform(position, 45f);
        textureAtlas = assets.getTextureAtlas("skins/BlockSkins.atlas");
        sound = assets.getSound("audio/Sound01.wav");
        defaultBlock();
    }

    public void defaultBlock(){
        getSprite().setRegion(textureAtlas.findRegion("DefaultBlock"));
        specialBlockId = 0;
        isSpecialBlock = false;
    }
    public void greenBlock(){
        getSprite().setRegion(textureAtlas.findRegion("GreenBlock"));
        specialBlockId = 1;
        isSpecialBlock = true;
    }
    public void purpleBlock(){
        getSprite().setRegion(textureAtlas.findRegion("PurpleBlock"));
        specialBlockId = 2;
        isSpecialBlock = true;
    }
    public void redBlock(){
        getSprite().setRegion(textureAtlas.findRegion("RedBlock"));
        specialBlockId = 3;
        isSpecialBlock = true;
    }
    public void blueBlock(){
        getSprite().setRegion(textureAtlas.findRegion("BlueBlock"));
        specialBlockId = 4;
        isSpecialBlock = true;
    }
    public void goldBlock(){
        getSprite().setRegion(textureAtlas.findRegion("GoldBlock"));
        specialBlockId = 5;
        isSpecialBlock = true;
    }
    public void whiteBlock(){
        getSprite().setRegion(textureAtlas.findRegion("WhiteBlock"));
        specialBlockId = 6;
        isSpecialBlock = true;
    }
    public void playSound(){
        if (!wasSound){
            sound.play(GameData.volumeSound);
            wasSound = true;
        }
    }

    //Получаем статус блока.
    public int getSpecialBlockId(){
        return specialBlockId;
    }
    // Устанавливаем статус блока.
    public void setIsSpecialBlock(){
        isSpecialBlock = true;

    }
    // Получаем статус блока.
    public boolean getIsSpecialBlock(){
        return isSpecialBlock;

    }
}

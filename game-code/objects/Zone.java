package com.darkbrokengames.fallduly2.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.darkbrokengames.fallduly2.Assets;

public class Zone extends PhysicalObject{
    public Zone(Vector2 position, Vector2 size, World world, Assets assets) {
        super(world, size, 0, CollisionType.BOX, assets.getTexture("textures/Square16x16.png", false));
        setTransform(position, 0f);
    }
}

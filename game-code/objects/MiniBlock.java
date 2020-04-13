package com.darkbrokengames.fallduly2.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.darkbrokengames.fallduly2.Assets;

public class MiniBlock extends PhysicalObject{
    public MiniBlock(Vector2 position, World world, Assets assets) {
        super(assets, world, "MiniBlock");
        setTransform(position, 45f);
    }
}

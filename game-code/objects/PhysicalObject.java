package com.darkbrokengames.fallduly2.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.utils.PhysicalObjectData;
import com.darkbrokengames.fallduly2.utils.PhysicalObjectDataList;

public class PhysicalObject {

    private Sprite sprite;
    private Body body;
    public String name = "";
    public String tag = "";
    public int worldId = 0;

    public class CollisionType{
        public final static int BOX = 0;
        public final static int CIRCLE = 1;
        public final static int POLYGON = 2;
    }
    public PhysicalObject(Assets assets, World world, String collisionData) {
        Json json = new Json();
        FileHandle file = Gdx.files.internal("json/PhysicalObjectDataList.json");
        PhysicalObjectDataList physicalObjectDataList = json.fromJson(PhysicalObjectDataList.class, file);
        PhysicalObjectData physicalObjectData = physicalObjectDataList.PHYSICAL_OBJECT_DATA.get(0);
        for (int i = 0; i < physicalObjectDataList.PHYSICAL_OBJECT_DATA.size; i++) {
            if (physicalObjectDataList.PHYSICAL_OBJECT_DATA.get(i).NAME.equals(collisionData)) {
                physicalObjectData = physicalObjectDataList.PHYSICAL_OBJECT_DATA.get(i);
                break;
            }
        }

        BodyDef def = new BodyDef();
        switch (physicalObjectData.BODY_TYPE){
            case 0:
                def.type = BodyDef.BodyType.StaticBody;
                break;
            case 1:
                def.type = BodyDef.BodyType.KinematicBody;
                break;
            case 2:
                def.type = BodyDef.BodyType.DynamicBody;
                break;
        }
        def.position.set(0f, 0f);
        body = world.createBody(def);

        if (physicalObjectData.IS_CIRCLE){
            createCircle(new Vector2(physicalObjectData.SIZE_X, physicalObjectData.SIZE_Y), new Vector2(physicalObjectData.COLLISION_OFFSET_POS_X, physicalObjectData.COLLISION_OFFSET_POS_Y),
                    new Vector2(physicalObjectData.COLLISION_OFFSET_SIZE_X, physicalObjectData.COLLISION_OFFSET_SIZE_Y));
        } else {
            createBox(new Vector2(physicalObjectData.SIZE_X, physicalObjectData.SIZE_Y), new Vector2(physicalObjectData.COLLISION_OFFSET_POS_X, physicalObjectData.COLLISION_OFFSET_POS_Y),
                    new Vector2(physicalObjectData.COLLISION_OFFSET_SIZE_X, physicalObjectData.COLLISION_OFFSET_SIZE_Y));
        }

        body.setUserData(this);
        body.setTransform(body.getPosition(), 0*((float)Math.PI / 180f));
        sprite = new Sprite(assets.getTexture(physicalObjectData.TEXTURE_NAME, false));
        sprite.setSize(physicalObjectData.SIZE_X, physicalObjectData.SIZE_Y);
        sprite.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
        sprite.setCenter(body.getPosition().x,  body.getPosition().y);

        name = physicalObjectData.NAME;
        worldId = world.getBodyCount();
        body.setLinearDamping(physicalObjectData.LINEAR_DUMPING);
        body.setAngularDamping(physicalObjectData.ANGULAR_DUMPING);
    }

    public PhysicalObject(World world, Vector2 size, int bodyType, int collisionType, Texture texture) {
        BodyDef def = new BodyDef();
        switch (bodyType){
            case 0:
                def.type = BodyDef.BodyType.StaticBody;
                break;
            case 1:
                def.type = BodyDef.BodyType.KinematicBody;
                break;
            case 2:
                def.type = BodyDef.BodyType.DynamicBody;
                break;
        }
        def.position.set(0f, 0f);
        body = world.createBody(def);

        switch (collisionType){
            case CollisionType.BOX:
                createBox(size, new Vector2(0f,0f), new Vector2(0f,0f));
                break;
            case CollisionType.CIRCLE:
                createCircle(size, new Vector2(0f,0f), new Vector2(0f,0f));
                break;
            case CollisionType.POLYGON:

                break;
        }
        body.setUserData(this);
        body.setTransform(body.getPosition(), 0*((float)Math.PI / 180f));

        sprite = new Sprite(texture);
        sprite.setSize(size.x, size.y);
        sprite.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
        sprite.setCenter(body.getPosition().x,  body.getPosition().y);
    }

    public PhysicalObject(World world, Vector2 size, int bodyType, Vector2 offsetPosCollision, Vector2 offsetSizeCollision, int collisionType, Texture texture){

        BodyDef def = new BodyDef();
        switch (bodyType){
            case 0:
                def.type = BodyDef.BodyType.StaticBody;
                break;
            case 1:
                def.type = BodyDef.BodyType.KinematicBody;
                break;
            case 2:
                def.type = BodyDef.BodyType.DynamicBody;
                break;
        }
        def.position.set(0f, 0f);
        body = world.createBody(def);

        switch (collisionType){
            case CollisionType.BOX:
                createBox(size, offsetPosCollision, offsetSizeCollision);
                break;
            case CollisionType.CIRCLE:
                createCircle(size, offsetPosCollision, offsetSizeCollision);
                break;
            case CollisionType.POLYGON:

                break;
        }
        body.setUserData(this);
        body.setTransform(body.getPosition(), 0*((float)Math.PI / 180f));
    }
    public void createBox(Vector2 size, Vector2 offsetPosCollision, Vector2 offsetSizeCollision){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 2f - size.y / 2f * offsetSizeCollision.x, size.y / 2f - size.y / 2f * offsetSizeCollision.y, offsetPosCollision, 0);
        body.createFixture(shape, 1f).setUserData(this);
        shape.dispose();
    }

    public void createCircle(Vector2 size, Vector2 offsetPosCollision, Vector2 offsetSizeCollision){
        CircleShape shape = new CircleShape();
        shape.setRadius(size.x / 2f - size.x / 2f * offsetSizeCollision.x);
        shape.setPosition(offsetPosCollision);
        body.createFixture(shape, 1f).setUserData(this);
        shape.dispose();
    }

    public void setTransform(Vector2 pos, float degrees){
        body.setTransform(pos, degrees * (float)(Math.PI / 180f));
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2f, body.getPosition().y - sprite.getHeight() / 2f);
        sprite.setRotation(degrees);
    }

    public void draw(SpriteBatch batch) {
        if (body.isActive()) {
            if (body.getType() == BodyDef.BodyType.DynamicBody) {
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2f, body.getPosition().y - sprite.getHeight() / 2f);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
            }
            sprite.draw(batch);
        }
    }

    public void setRotation(double angle){
        body.setTransform(body.getPosition().x, body.getPosition().y,(float)Math.toRadians(angle - 90));
    }

    public Body getBody(){
        return body;

    }
    public Sprite getSprite(){
        return sprite;
    }

    public void destroy(){
        body.setUserData(null);
        body.getWorld().destroyBody(body);
    }
}

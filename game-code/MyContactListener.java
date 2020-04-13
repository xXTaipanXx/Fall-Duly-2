package com.darkbrokengames.fallduly2;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.darkbrokengames.fallduly2.objects.Block;
import com.darkbrokengames.fallduly2.objects.MiniBlock;
import com.darkbrokengames.fallduly2.objects.PhysicalObject;
import com.darkbrokengames.fallduly2.objects.Player;
import com.darkbrokengames.fallduly2.objects.Zone;

public class MyContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() != null && fixtureB.getUserData() != null){

            PhysicalObject physicalObject = null;
            Player player = null;

            if(fixtureA.getUserData() instanceof Player){
                player = (Player) fixtureA.getUserData();
            }
            if(fixtureB.getUserData() instanceof Player){
                player = (Player) fixtureB.getUserData();
            }
            if(fixtureA.getUserData() instanceof Block){
                physicalObject = (Block) fixtureA.getUserData();
            }
            if(fixtureB.getUserData() instanceof Block){
                physicalObject = (Block) fixtureB.getUserData();
            }
            if(fixtureA.getUserData() instanceof MiniBlock){
                physicalObject = (MiniBlock) fixtureA.getUserData();
            }
            if(fixtureB.getUserData() instanceof MiniBlock){
                physicalObject = (MiniBlock) fixtureB.getUserData();
            }
            if(fixtureA.getUserData() instanceof Zone){
                physicalObject = (Zone) fixtureA.getUserData();
            }
            if(fixtureB.getUserData() instanceof Zone) {
                physicalObject = (Zone) fixtureB.getUserData();
            }

            if (physicalObject.tag.equals("WinZone")){
                player.setContactZone(1);
            }
            if (physicalObject.tag.equals("DeathZone")){
                player.setContactZone(2);
            }

            if (physicalObject.tag.equals("Block")){
                Block block = (Block) physicalObject;

                player.setContactSpecialBlock(block.getSpecialBlockId());

                block.defaultBlock();
                block.playSound();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

package com.massey.journey.Utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    private Array<Body> bodiesToRemove;

    //called when two fixtures start to overlap with each other

    public MyContactListener() {
        super();
        bodiesToRemove = new Array<Body>();
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();


        if(fa == null || fb == null) {
            return;
        }

        if(fa.getUserData() != null && fa.getUserData().equals("dagger")) {
            bodiesToRemove.add(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("dagger")) {
            bodiesToRemove.add(fb.getBody());
        }
    }

    //called when two fixtures no longer overlap with each other
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();



    }

    public Array<Body> getBodies() { return bodiesToRemove; }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) { }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) { }
}

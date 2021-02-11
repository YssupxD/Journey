package com.massey.journey.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.massey.journey.sprites.Enemy;
import com.massey.journey.sprites.Gino;

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

        int fc = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

        switch (fc) {
            case (Box2dVariables.BIT_ENEMY | Box2dVariables.BIT_SWORD):
                if (fa.getFilterData().categoryBits == Box2dVariables.BIT_ENEMY) {
                    ((Enemy) fa.getUserData()).hitBySword();
                } else if (fb.getFilterData().categoryBits == Box2dVariables.BIT_ENEMY) {
                    ((Enemy) fb.getUserData()).hitBySword();
                }
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

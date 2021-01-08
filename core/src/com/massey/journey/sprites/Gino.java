package com.massey.journey.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.massey.journey.Journey;

public class Gino extends Sprite {
    public World world;
    public Body b2body;

    public Gino(World world) {
        this.world = world;
        defineGino();
    }

    public void defineGino() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(50 / Journey.PPM, 250 / Journey.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(5 / Journey.PPM);

        fixtureDef.shape = circleShape;
        b2body.createFixture(fixtureDef);
    }
}

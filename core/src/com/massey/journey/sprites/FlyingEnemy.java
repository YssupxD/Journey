package com.massey.journey.sprites;

import com.badlogic.gdx.physics.box2d.World;
import com.massey.journey.states.PlayState;

public class FlyingEnemy extends Enemy{


    public FlyingEnemy(World world, PlayState screen, float x, float y) {
        super(world, screen, x, y);
    }

    @Override
    protected void defineEnemy() {

    }

    @Override
    public void hitBySword() {

    }
}
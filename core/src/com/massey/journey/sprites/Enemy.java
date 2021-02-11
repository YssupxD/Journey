package com.massey.journey.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.massey.journey.states.PlayState;

public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayState screen;
    public Body b2body;

    public Enemy(World world, PlayState screen, float x, float y) {
        this.screen = screen;
        this.world = world;
        setPosition(x, y);
        defineEnemy();
    }

    protected abstract void defineEnemy();
    public abstract void hitBySword();
    public abstract void collectedByPlayer();
    public abstract void update(float dt);
}

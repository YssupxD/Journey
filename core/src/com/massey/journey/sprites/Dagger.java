package com.massey.journey.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.massey.journey.Journey;


public class Dagger extends B2DSprite{

    public Dagger(Body body) {

        super (body);

        Texture tex = Journey.res.getTexture("dagger");
        TextureRegion[] sprites = TextureRegion.split(tex, 4, 16)[0];
        setAnimation(sprites, 0.1f);

        width = sprites[0].getRegionWidth();
        height = sprites[0].getRegionHeight();
    }
}

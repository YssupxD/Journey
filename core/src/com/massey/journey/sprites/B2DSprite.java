package com.massey.journey.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.massey.journey.Utils.Animation;
import com.massey.journey.Utils.BoundedCam;
import com.massey.journey.Utils.Box2dVariables;

import javax.swing.Box;

public class B2DSprite {

    protected Body body;
    protected Animation animation;
    protected float width;
    protected float height;

    public B2DSprite(Body body) {
        this.body = body;
        animation = new Animation();
    }

    public void setAnimation(TextureRegion textureRegion, float delay) {
        setAnimation(new TextureRegion[] {textureRegion}, delay);
    }

    public void setAnimation(TextureRegion[] textureRegions, float delay) {
        animation.setFrames(textureRegions, delay);
        width = textureRegions[0].getRegionWidth();
        height = textureRegions[0].getRegionHeight();
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(animation.getFrame(), getPosition().x * Box2dVariables.PPM - width / 2,
                getPosition().y * Box2dVariables.PPM - height / 2);
        batch.end();
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}

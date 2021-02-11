package com.massey.journey.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.massey.journey.main.Journey;
import com.massey.journey.states.PlayState;
import com.massey.journey.utilities.Box2dVariables;

import static com.massey.journey.utilities.Box2dVariables.PPM;


public class Diamond extends Enemy{

    private boolean setToDestroy;
    private boolean destroyed;

    private float stateTime;
    private Animation<TextureRegion> shinning;

    public Diamond(World world, PlayState screen, float x, float y) {
        super(world, screen, x, y);

        setToDestroy = false;
        destroyed = false;

        stateTime = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 9; i < 13; i ++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy01_sheet"), i * 32, 0
                    , 32, 32));
            shinning = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();

            setBounds(getX(), getY(), 64 / PPM, 64 / PPM);
            setRegion(shinning.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / PPM, 10 / PPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = Box2dVariables.BIT_DIAMOND;
        fdef.filter.maskBits = Box2dVariables.BIT_PLAYER;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hitBySword() {

    }

    @Override
    public void collectedByPlayer() {
        setToDestroy = true;
        screen.getHud().addDiamond(5);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(shinning.getKeyFrame(stateTime));
            Journey.res.getSound("collect").play();
            stateTime = 0;
        }
        else if(!destroyed){
            setPosition(b2body.getPosition().x - getWidth() / 2,
                    b2body.getPosition().y - getHeight() / 4);
            setRegion(shinning.getKeyFrame(stateTime, true));
        }
    }

    public void draw(Batch batch) {
        if(!destroyed || stateTime < 0) {
            super.draw(batch);
        }
    }
}

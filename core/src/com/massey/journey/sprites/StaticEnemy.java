package com.massey.journey.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.massey.journey.main.Journey;
import com.massey.journey.states.PlayState;
import com.massey.journey.utilities.Box2dVariables;

import static com.massey.journey.utilities.Box2dVariables.PPM;

public class StaticEnemy extends Enemy {

    public enum State { IDLING, ATTACK_LEFT, ATTACK_RIGHT }

    public State currentState;
    public State previousState;

    private boolean setToDestroy;
    private boolean destroyed;

    private float stateTime;
    private Animation<TextureRegion> attackRight;
    private Animation<TextureRegion> attackLeft;
    private Animation<TextureRegion> hit;
    private Animation<TextureRegion> idle;


    public StaticEnemy(World world, PlayState screen, float x, float y) {
        super(world, screen, x, y);

        setToDestroy = false;
        destroyed = false;

        currentState = State.IDLING;
        previousState = State.IDLING;
        stateTime = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy03_sheet"), i * 64,
                    0, 64, 64));
        }
        idle = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 8; i < 16; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy03_sheet"), i * 64,
                    0, 64, 64));
        }
        attackLeft = new Animation(0.2f, frames);
        frames.clear();

        for(int i = 16; i < 24; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy03_sheet"), i * 64,
                    0, 64, 64));
        }
        attackRight = new Animation(0.2f, frames);
        frames.clear();

        for(int i = 24; i < 27; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy03_sheet"), i * 64,
                    0, 64, 64));
        }
        hit = new Animation(0.1f, frames);
        frames.clear();

        setBounds(getX(), getY(), 64 / PPM, 64 / PPM);
        setRegion(idle.getKeyFrame(stateTime, true));
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(hit.getKeyFrame(stateTime));
            Journey.res.getSound("hit").play();
            stateTime = 0;
        }
        else if(!destroyed){
            setPosition(b2body.getPosition().x - getWidth() / 2,
                    b2body.getPosition().y - getHeight() / 4);
            setRegion(getFrame(dt));
        }
    }

    public void draw(Batch batch) {
        if(!destroyed || stateTime < 1) {
            super.draw(batch);
        }
    }

    public TextureRegion getFrame(float deltaTime) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case ATTACK_LEFT:
                region = attackLeft.getKeyFrame(stateTime, true);
                break;
            case ATTACK_RIGHT:
                region = attackRight.getKeyFrame(stateTime, true);
                break;
            case IDLING:
            default:
                region = idle.getKeyFrame(stateTime, true);
                break;
        }
        if(currentState == previousState) {
            stateTime += deltaTime;
        }
        else{
            stateTime = 0;
        }
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(screen.getGino().b2body.getPosition().x - b2body.getPosition().x <= 0.5f && screen.getGino().b2body.getPosition().x - b2body.getPosition().x > 0) {
            return State.ATTACK_RIGHT;
        }
        else if(screen.getGino().b2body.getPosition().x - b2body.getPosition().x >= -0.5f && screen.getGino().b2body.getPosition().x - b2body.getPosition().x < 0) {
            return State.ATTACK_LEFT;
        }
        else{
            return State.IDLING;
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
        shape.setAsBox(12 / PPM, 20 / PPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = Box2dVariables.BIT_ENEMY;
        fdef.filter.maskBits =
                Box2dVariables.BIT_GROUND | Box2dVariables.BIT_PLAYER | Box2dVariables.BIT_SWORD | Box2dVariables.BIT_ENEMY;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hitBySword() {
        if(Gdx.input.isKeyPressed(Input.Keys.J)){
            setToDestroy = true;
        }
    }

    @Override
    public void collectedByPlayer() {

    }
}

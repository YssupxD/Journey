package com.massey.journey.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.massey.journey.Journey;
import com.massey.journey.Utils.Box2dVariables;
import com.massey.journey.scenes.Hud;
import com.massey.journey.screens.MainGameScreen;

import java.security.spec.ECGenParameterSpec;

import javax.swing.Box;

import static com.massey.journey.Utils.Box2dVariables.PPM;

public class Gino extends Sprite {
    public enum State { JUMPING, IDLING, FALLING, RUNNING, THROWING, DIEING, GETTING_HIT }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion ginoStand;


    //Animation variables for the player
    private Animation<TextureRegion> ginoJump;
    private Animation<TextureRegion> ginoIdle;
    private Animation<TextureRegion> ginoFall;
    private Animation<TextureRegion> ginoRun;
    private Animation<TextureRegion> ginoThrow;
    private Animation<TextureRegion> ginoDie;
    private Animation<TextureRegion> ginoGetHit;

    private float stateTime;

    private boolean runningRight;

    public Gino(World world, MainGameScreen screen) {
        super(screen.getAtlas().findRegion("Gino"));
        this.world = world;
        currentState = State.IDLING;
        previousState = State.IDLING;
        stateTime = 0;
        runningRight = true;

        ginoStand = new TextureRegion(getTexture(), 0, 384, 64, 64);
        setBounds(0, 0, 64 / PPM, 64 / PPM);
        setRegion(ginoStand);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 9; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64,  384, 64, 64));
        }
        ginoIdle = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 9; i < 17; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 384, 64, 64));
        }
        ginoRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 17; i < 20; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 384, 64, 64));
        }
        ginoJump = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 20; i < 23; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 384, 64, 64));
        }
        ginoFall = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 7; i < 13; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 256, 64, 64));
        }
        ginoThrow = new Animation(0.05f, frames);
        frames.clear();

        for(int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 320, 64, 64));
        }
        ginoDie = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 5; i < 9; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 320, 64, 64));
        }
        ginoGetHit = new Animation(0.1f, frames);
        frames.clear();


        defineGino();


    }

    public void update(float deltaTime) {

        if(runningRight) {
            setPosition(b2body.getPosition().x - getWidth() / 3, b2body.getPosition().y - getHeight() / 3);
        }
        else{
            setPosition(b2body.getPosition().x - getWidth() / 1.5f, b2body.getPosition().y - getHeight() / 3);
        }
        setRegion(getFrame(deltaTime));
    }

    public TextureRegion getFrame(float deltaTime) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = ginoJump.getKeyFrame(stateTime, true);
                break;
            case RUNNING:
                region = ginoRun.getKeyFrame(stateTime, true);
                break;
            case FALLING:
                region = ginoFall.getKeyFrame(stateTime);
                break;
            case THROWING:
                region = ginoThrow.getKeyFrame(stateTime, false);
                break;
            case IDLING:
            default:
                region = ginoIdle.getKeyFrame(stateTime, true);
                break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
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

    public State getState() {
        if(b2body.getLinearVelocity().y > 0) {
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().x != 0 && b2body.getLinearVelocity().y == 0) {
            return State.RUNNING;
        }
        else if(b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.J)){
            return State.THROWING;
        }
        else if(b2body.getPosition().y < 0){
            return State.DIEING;
        }
        else { return State.IDLING; }
    }

    public void defineGino() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(50 / PPM, 250 / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6 / PPM, 16 / PPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = Box2dVariables.BIT_PLAYER;
        fdef.filter.maskBits = Box2dVariables.BIT_GROUND | Box2dVariables.BIT_ENEMY;
        b2body.createFixture(fdef).setUserData("Player");

        EdgeShape rightEdge = new EdgeShape();
        rightEdge.set(new Vector2(8 / PPM, 16 / PPM), new Vector2(8 / PPM, -16 / PPM));
        fdef.shape = rightEdge;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("Right_Edge");

        EdgeShape leftEdge = new EdgeShape();
        leftEdge.set(new Vector2(-8 / PPM, 16 / PPM), new Vector2(-8 / PPM, -16 / PPM));
        fdef.shape = leftEdge;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("Left_Edge");

    }
}

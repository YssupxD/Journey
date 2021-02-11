package com.massey.journey.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.massey.journey.main.Journey;
import com.massey.journey.utilities.Box2dVariables;
import com.massey.journey.states.PlayState;
import com.massey.journey.utilities.GameStateManager;

import static com.massey.journey.utilities.Box2dVariables.PPM;

public class Gino extends Sprite {
    public enum State { JUMPING, IDLING, FALLING, RUNNING, ATTACKING, DIEING, GETTING_HIT }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;

    //Animation variables for the player
    private Animation<TextureRegion> ginoJump;
    private Animation<TextureRegion> ginoIdle;
    private Animation<TextureRegion> ginoFall;
    private Animation<TextureRegion> ginoRun;
    private Animation<TextureRegion> ginoAttack;
    private Animation<TextureRegion> ginoDie;
    private Animation<TextureRegion> ginoGetHit;

    private float stateTime;
    private boolean runningRight;
    private boolean ginoIsDead;
    private boolean ginoIsHit;
    private boolean runThrowAnimation = true;

    PlayState screen;

    public Gino(World world, PlayState screen) {
        this.screen = screen;
        this.world = world;

        currentState = State.IDLING;
        previousState = State.IDLING;
        stateTime = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 9; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Gino"), i * 64,  128, 64,
                    64));
        }
        ginoIdle = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 9; i < 17; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Gino"), i * 64, 128, 64,
                    64));
        }
        ginoRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 17; i < 20; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Gino"), i * 64, 128, 64,
                    64));
        }
        ginoJump = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 20; i < 23; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Gino"), i * 64, 128, 64,
                    64));
        }
        ginoFall = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 7; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Gino"), i * 64, 192, 64,
                    64));
        }
        ginoAttack = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Gino"), i * 64, 64, 64, 64));
        }
        ginoDie = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 5; i < 9; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Gino"), i * 64, 64, 64, 64));
        }
        ginoGetHit = new Animation(0.1f, frames);
        frames.clear();

        defineGino();

        setBounds(0, 0, 64 / PPM, 64 / PPM);
        setRegion(ginoIdle.getKeyFrame(stateTime, true));


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
            case ATTACKING:
                region = ginoAttack.getKeyFrame(stateTime, false);
                break;

            case DIEING:
                region = ginoDie.getKeyFrame(stateTime, false);
                break;

            case GETTING_HIT:
                region = ginoGetHit.getKeyFrame(stateTime, false);
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
        else if((runThrowAnimation && Gdx.input.isKeyPressed(Input.Keys.J)) || screen.getJoyCon
        ().isPressAttack()){
            return State.ATTACKING;
        }
        else if(ginoIsDead){
            return State.DIEING;
        }
        else { return State.IDLING; }
    }

    public void defineGino() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(50 / PPM, 200 / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6 / PPM, 16 / PPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = Box2dVariables.BIT_PLAYER;
        fdef.filter.maskBits =
                Box2dVariables.BIT_GROUND | Box2dVariables.BIT_ENEMY | Box2dVariables.BIT_DIAMOND;
        b2body.createFixture(fdef).setUserData("Gino");

        shape.setAsBox(10 / PPM, 8 / PPM, new Vector2(18 / PPM, 0), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = Box2dVariables.BIT_SWORD;
        fdef.filter.maskBits = Box2dVariables.BIT_ENEMY;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("sword");

        shape.setAsBox(-10 / PPM, 8 / PPM, new Vector2(-18 / PPM, 0), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = Box2dVariables.BIT_SWORD;
        fdef.filter.maskBits = Box2dVariables.BIT_ENEMY;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("sword");
    }

    public void hitByEnemy() {
        screen.getHud().loseHP(100);
        Journey.res.getSound("gameover").play();
        Journey.res.getMusic("TownTheme").stop();
        screen.getGsm().setState(GameStateManager.OVER);
    }
}

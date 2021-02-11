package com.massey.journey.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.massey.journey.components.Hud;
import com.massey.journey.components.JoyCon;
import com.massey.journey.main.Journey;
import com.massey.journey.sprites.Enemy;
import com.massey.journey.sprites.StaticEnemy;
import com.massey.journey.utilities.Box2dVariables;
import com.massey.journey.utilities.GameStateManager;
import com.massey.journey.utilities.MyContactListener;
import com.massey.journey.sprites.Diamond;
import com.massey.journey.sprites.Gino;

import static com.massey.journey.utilities.Box2dVariables.PPM;

public class PlayState extends GameState {

    private boolean debug = false;

    private TextureAtlas atlas;
    private TextureAtlas atlas2;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private MyContactListener cl;
    private Viewport gamePort;

    private Gino gino;

    private Array<StaticEnemy> staticEnemies;

    public Array<StaticEnemy> getStaticEnemies() {
        return staticEnemies;
    }

    private Array<Diamond> diamonds;

    public Array<Diamond> getDiamonds() {
        return diamonds;
    }

    //Tiled map variables
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tmRenderer;

    //basic game screen variables
    private Hud hud;
    private JoyCon joyCon;

    //constructor
    public PlayState(GameStateManager gsm) {
        super(gsm);

        //Setup Box2D world, set 0 gravity in X axis, -9.8 in Y axis, and allow bodies to sleep
        world = new World(new Vector2(0,-9.8f), true);
        cl = new MyContactListener();
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();

        //load texture pack atlas
        atlas = new TextureAtlas("Texture Pack.atlas");
        atlas2 = new TextureAtlas("Diamond.atlas");

        //create player
        gino = new Gino(world, this);

        //create level graphics and items and graphics
        createWorld();

        //create game HUD for game info.
        hud = new Hud(batch);

        //create virtual control system;
        joyCon = new JoyCon(batch);


        gamePort = new FitViewport(Journey.WORLD_WIDTH / PPM, Journey.WORLD_HEIGHT / PPM,
                gameCam);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Journey.WORLD_WIDTH / PPM, Journey.WORLD_HEIGHT / PPM);

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TextureAtlas getAtlas2() { return atlas2; }

    public JoyCon getJoyCon() { return joyCon; }

    public Gino getGino() {return gino; }

    public MyContactListener getCl() { return cl; }

    public Hud getHud() { return hud; }

    public GameStateManager getGsm() { return gsm;}

    public void handleInput() {
        if((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || joyCon.isPressJump()) && gino
        .b2body.getLinearVelocity().y == 0){
            gino.b2body.applyLinearImpulse(new Vector2(0, 3.5f), gino.b2body.getWorldCenter(),
                    true);
            Journey.res.getSound("jump").play();
        }
        if((Gdx.input.isKeyPressed(Input.Keys.D) || joyCon.isPressRight()) && gino.b2body
        .getLinearVelocity().x <= 2) {
            gino.b2body.applyLinearImpulse(new Vector2(0.1f, 0), gino.b2body.getWorldCenter(), true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.A) || joyCon.isPressLeft()) && gino.b2body
        .getLinearVelocity().x >= -2) {
            gino.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), gino.b2body.getWorldCenter(), true);
        }
        if((Gdx.input.isKeyJustPressed(Input.Keys.J) || joyCon.isPressAttack()) && Math.abs(gino.b2body.getLinearVelocity().x) < 0.5f && gino.b2body.getLinearVelocity().y == 0){
            Journey.res.getSound("sword").play();
        }
    }

    public void update(float deltaTime) {
        handleInput();

        world.step(Journey.STEP, 1, 1);

        gino.update(deltaTime);

        for(Enemy e : getStaticEnemies()) {
            e.update(deltaTime);
        }

        for(Enemy d : getDiamonds()) {
            d.update(deltaTime);
        }

        if(gino.b2body.getPosition().y < 0) {
            Journey.res.getSound("gameover").play();
            Journey.res.getMusic("TownTheme").stop();
            gsm.setState(GameStateManager.OVER);
        }
    }

    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //set cam to focus on player
        gameCam.position.x = gino.b2body.getPosition().x;
        gameCam.position.y = gino.b2body.getPosition().y;
        gameCam.update();

        //draw tiled map
        tmRenderer.setView(gameCam);
        tmRenderer.render();

        //draw Player
        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        gino.draw(batch);
        for(Enemy e : getStaticEnemies()) {
            e.draw(batch);
        }
        for(Enemy d : getDiamonds()) {
            d.draw(batch);
        }
        batch.end();


        //draw HUD
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //draw virtual Joy Con
        batch.setProjectionMatrix(joyCon.stage.getCamera().combined);
        joyCon.stage.draw();

        //render Box2DDebugLines
        if(debug) {
            b2dCam.position.x = gino.b2body.getPosition().x;
            b2dCam.position.y = gino.b2body.getPosition().y;
            b2dCam.update();
            b2dr.render(world, b2dCam.combined);
        }
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tmRenderer.dispose();
        world.dispose();
        b2dr.dispose();
        joyCon.dispose();
    }

    public void createWorld(){
        //Load and setup map render.
        try{
            tiledMap = new TmxMapLoader().load("level1.tmx");
        }
        catch (Exception e) {
            System.out.println("Can't find the level file");
            Gdx.app.exit();
        }
        tmRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object :
                tiledMap.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Box2dVariables.BIT_GROUND;
            fixtureDef.filter.maskBits = Box2dVariables.BIT_PLAYER | Box2dVariables.BIT_ENEMY;
            body.createFixture(fixtureDef).setUserData("Ground");
        }

        //create all static Enemies
        staticEnemies = new Array<StaticEnemy>();
        for(MapObject object :
                tiledMap.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            staticEnemies.add(new StaticEnemy(world, this, rect.getX() / PPM, rect.getY() / PPM));
        }

        //create all diamonds
        diamonds = new Array<Diamond>();
        for(MapObject object :
                tiledMap.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            diamonds.add(new Diamond(world, this, rect.getX() / PPM, rect.getY() / PPM));
        }
    }
}

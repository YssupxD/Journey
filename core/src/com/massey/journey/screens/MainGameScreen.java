package com.massey.journey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.massey.journey.Journey;
import com.massey.journey.Utils.BoundedCam;
import com.massey.journey.Utils.Box2dVariables;
import com.massey.journey.Utils.MyContactListener;
import com.massey.journey.scenes.Hud;
import com.massey.journey.scenes.JoyCon;
import com.massey.journey.sprites.Dagger;
import com.massey.journey.sprites.Gino;
import com.massey.journey.Utils.B2WorldCreator;
import static com.massey.journey.Utils.Box2dVariables.PPM;

public class MainGameScreen implements Screen {

    private boolean debug = true;

    //Reference to the game, used to set Screens
    private Journey game;
    private TextureAtlas atlas;

    //basic game screen variables
    private BoundedCam gameCam;
    private Viewport gamePort;
    private Hud hud;
    private JoyCon joyCon;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tmRenderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dRenderer;
    private Gino gino;
    private Array<Dagger> daggers;


    //constructor
    public MainGameScreen(Journey game) {

        atlas = new TextureAtlas("Texture Pack.atlas");

        this.game = game;
        //Setup a game camera
        gameCam = new BoundedCam();
        gameCam.setToOrtho(false, Journey.VIRTUAL_WIDTH / PPM, Journey.VIRTUAL_HEIGHT / PPM);

        //Fitviewport doesn't change the aspect ratio, adding black bars to screen
        gamePort = new FitViewport(Journey.VIRTUAL_WIDTH / PPM, Journey.VIRTUAL_HEIGHT / PPM,
                gameCam);

        //create game HUD for game info.
        hud = new Hud(game.batch);

        //create virtual control system;
        joyCon = new JoyCon(game.batch);

        //Load and setup map render.
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("level1.tmx");
        tmRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);

        //set game camera to be centered when the game starts
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //Setup Box2D world, set 0 gravity in X axis, -9.8 in Y axis, and allow bodies to sleep
        world = new World(new Vector2(0,-9.8f), true);
        world.setContactListener(new MyContactListener());

        //allows for debug lines of game world
        b2dRenderer = new Box2DDebugRenderer();

        //create level graphics
        new B2WorldCreator(world, tiledMap, 7);

        //create player
        gino = new Gino(world, this);

        //create daggers
        createDaggers();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public JoyCon getJoyCon() {
        return joyCon;
    }


    @Override
    public void show() {

    }

    public void handleInput(float deltaTime) {
        if((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || joyCon.isPressJump()) && gino.b2body.getLinearVelocity().y == 0){
            gino.b2body.applyLinearImpulse(new Vector2(0, 3.5f), gino.b2body.getWorldCenter(),
                    true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.D) || joyCon.isPressRight())&& gino.b2body.getLinearVelocity().x <= 2) {
            gino.b2body.applyLinearImpulse(new Vector2(0.1f, 0), gino.b2body.getWorldCenter(), true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.A) || joyCon.isPressLeft())&& gino.b2body.getLinearVelocity().x >= -2) {
            gino.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), gino.b2body.getWorldCenter(), true);
        }
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);

        gameCam.position.x = gino.b2body.getPosition().x;
        gameCam.position.y = gino.b2body.getPosition().y;
        gameCam.update();

        world.step(1 / 60f, 1, 1);

        gino.update(deltaTime);

        for(int i = 0; i < daggers.size; i++) {
            daggers.get(i).update(deltaTime);
        }

    }

    @Override
    public void render(float deltaTime) {

        update(deltaTime);


        //The renderer only paints where the camera sees.
        tmRenderer.setView(gameCam);
        tmRenderer.render();

        //clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //map render
        tmRenderer.render();

        //render Box2DDebugLines
        if(debug) {
            b2dRenderer.render(world, gameCam.combined);
        }

        //draw Player
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        gino.draw(game.batch);
        game.batch.end();


        //draw HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        for(int i = 0; i < daggers.size; i++) {
            daggers.get(i).render(game.batch);
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tmRenderer.dispose();
        world.dispose();
        b2dRenderer.dispose();
        hud.dispose();

    }

    private void createDaggers() {
        daggers = new Array<Dagger>();

        MapLayer ml = tiledMap.getLayers().get("daggers");
        if(ml == null) {
            return;
        }

        for(MapObject mo : ml.getObjects()) {
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            float x = Float.parseFloat(mo.getProperties().get("x").toString()) / PPM;
            float y = Float.parseFloat(mo.getProperties().get("y").toString()) / PPM;
            bdef.position.set(x, y);
            Body body = world.createBody(bdef);
            FixtureDef fdef = new FixtureDef();
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(8 / PPM);
            fdef.shape = circleShape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Box2dVariables.BIT_DAGGER;
            fdef.filter.maskBits = Box2dVariables.BIT_PLAYER;

            body.createFixture(fdef).setUserData("Dagger");
            Dagger dagger = new Dagger(body);
            body.setUserData(dagger);
            daggers.add(dagger);
            circleShape.dispose();
        }
    }
}

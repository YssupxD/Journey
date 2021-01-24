package com.massey.journey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.massey.journey.Journey;
import com.massey.journey.scenes.Hud;
import com.massey.journey.sprites.Gino;
import com.massey.journey.Utils.B2WorldCreator;

public class MainGameScreen implements Screen {

    //Reference to the game, used to set Screens
    private Journey game;
    private TextureAtlas atlas;

    //basic game screen variables
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Gino gino;

    //key pressed
    public static int jPressed = 0;

    //constructor
    public MainGameScreen(Journey game) {
        atlas = new TextureAtlas("Texture Pack.atlas");

        this.game = game;
        //Setup a game camera
        gameCam = new OrthographicCamera();

        //Fitviewport doesn't change the aspect ratio, adding black bars to screen
        gamePort = new FitViewport(Journey.SCREEN_WIDTH / Journey.PPM, Journey.SCREEN_HEIGHT / Journey.PPM, gameCam);

        //create game HUD for game info.
        hud = new Hud(game.batch);

        //Load and setup map render.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Journey.PPM);

        //set game camera to be centered when the game starts
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //Setup Box2D world, set 0 gravity in X axis, -10 in Y axis, and allow bodies to sleep
        world = new World(new Vector2(0,-9.8f), true);
        //allows for debug lines of game world
        box2DDebugRenderer = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        gino = new Gino(world, this);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && gino.b2body.getLinearVelocity().y == 0){
            gino.b2body.applyLinearImpulse(new Vector2(0, 3.5f), gino.b2body.getWorldCenter(),
                    true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && gino.b2body.getLinearVelocity().x <= 2) {
            gino.b2body.applyLinearImpulse(new Vector2(0.1f, 0), gino.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && gino.b2body.getLinearVelocity().x >= -2) {
            gino.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), gino.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)) {
        }
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(1 / 60f, 5, 1);

        gino.update(deltaTime);

        gameCam.position.x = gino.b2body.getPosition().x;
        gameCam.position.y = gino.b2body.getPosition().y;
        gameCam.update();
        //The renderer only paints where the camera sees.
        renderer.setView(gameCam);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //map render
        renderer.render();

        //render Box2DDebugLines
        box2DDebugRenderer.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        gino.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();
    }
}

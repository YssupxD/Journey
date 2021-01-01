package com.massey.journey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.massey.journey.Journey;
import com.massey.journey.scenes.Hud;

import static com.massey.journey.Journey.SCREEN_HEIGHT;

public class MainGameScreen implements Screen {

    private Journey game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //constructor
    public MainGameScreen(Journey game) {
        this.game = game;
        //
        gameCam = new OrthographicCamera();
        //Fitviewport doesn't change the aspect ratio, adding black bars to screen
        gamePort = new FitViewport(Journey.SCREEN_WIDTH, Journey.SCREEN_HEIGHT, gameCam);
        //
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();

        map = mapLoader.load("level1.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }

    @Override
    public void show() {

    }

    public void handleInput(float deltaTime) {
        if(Gdx.input.isTouched()) {
            gameCam.position.x += 100 * deltaTime;
        }
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);

        //world.step(1 / 60f, 6, 2);

        gameCam.update();
        //The renderer only paints where the camera sees.
        renderer.setView(gameCam);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

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

    }
}

package com.massey.journey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.massey.journey.Journey;

public class MainMenuScreen implements Screen {

    private static final int TITLE_WIDTH = 884;
    private static final int TITLE_HEIGHT = 120;
    private static final int TITLE_Y = 620;

    private static final int QUIT_BUTTON_WIDTH = 438;
    private static final int QUIT_BUTTON_HEIGHT = 100;
    private static final int QUIT_BUTTON_Y = 375;

    private static final int PLAY_BUTTON_WIDTH = 438;
    private static final int PLAY_BUTTON_HEIGHT = 100;
    private static final int PLAY_BUTTON_Y = 500;

    Journey game;

    //Title Texture
    Texture title;

    //Button Textures
    Texture playButtonSelected;
    Texture playButtonUnselected;
    Texture quitButtonSelected;
    Texture quitButtonUnselected;

    private Stage stage;

    public MainMenuScreen(Journey game) {
        this.game = game;

        title = new Texture("TITLE.png");

        playButtonUnselected = new Texture("PLAY_UNSELECTED.png");
        playButtonSelected = new Texture("PLAY_SELECTED.png");
        quitButtonUnselected = new Texture("QUIT_UNSELECTED.png");
        quitButtonSelected = new Texture("QUIT_SELECTED.png");

        stage = new Stage();
        Table testTable = new Table();
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("TITILE_BACKGROUND.png"))));
        testTable.setFillParent(true);
        testTable.setDebug(false);
        stage.addActor(testTable);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        game.batch.begin();

        game.batch.draw(title, Journey.SCREEN_WIDTH / 2 - TITLE_WIDTH / 2, TITLE_Y, TITLE_WIDTH,
                TITLE_HEIGHT);

        int x = Journey.SCREEN_WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        if(Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && Journey.SCREEN_HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && Journey.SCREEN_HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y){
            game.batch.draw(playButtonSelected, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH,
                    PLAY_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()) {
                game.setScreen(new MainGameScreen(game));
            }
        } else {
            game.batch.draw(playButtonUnselected, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH,
                    PLAY_BUTTON_HEIGHT);
        }

        x = Journey.SCREEN_WIDTH / 2 - QUIT_BUTTON_WIDTH / 2;
        if(Gdx.input.getX() < x + QUIT_BUTTON_WIDTH && Gdx.input.getX() > x && Journey.SCREEN_HEIGHT - Gdx.input.getY() < QUIT_BUTTON_Y + QUIT_BUTTON_HEIGHT && Journey.SCREEN_HEIGHT - Gdx.input.getY() > QUIT_BUTTON_Y){
            game.batch.draw(quitButtonSelected, x, QUIT_BUTTON_Y, QUIT_BUTTON_WIDTH,
                    QUIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(quitButtonUnselected, x, QUIT_BUTTON_Y, QUIT_BUTTON_WIDTH,
                    QUIT_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

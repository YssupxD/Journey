package com.massey.journey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.massey.journey.Journey;

public class MainMenuScreen implements Screen {

    private static final int TITLE_WIDTH = 442;
    private static final int TITLE_HEIGHT = 58;
    private static final int TITLE_Y = 400;

    private static final int QUIT_BUTTON_WIDTH = 165;
    private static final int QUIT_BUTTON_HEIGHT = 37;
    private static final int QUIT_BUTTON_Y = 250;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 37;
    private static final int PLAY_BUTTON_Y = 320;

    Journey game;

    //Title Texture
    Texture title;

    //Button Textures
    Texture playButtonSelected;
    Texture playButtonUnselected;
    Texture quitButtonSelected;
    Texture quitButtonUnselected;

    public MainMenuScreen(Journey game) {
        this.game = game;

        title = new Texture("TITLE.png");

        playButtonUnselected = new Texture("NEW_GAME.png");
        playButtonSelected = new Texture("NEW_GAME_SELECTED.png");
        quitButtonUnselected = new Texture("QUIT.png");
        quitButtonSelected = new Texture("QUIT_SELECTED.png");


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

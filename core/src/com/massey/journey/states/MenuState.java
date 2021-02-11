package com.massey.journey.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.massey.journey.main.Journey;
import com.massey.journey.utilities.GameStateManager;

import static com.massey.journey.main.Journey.WORLD_HEIGHT;
import static com.massey.journey.main.Journey.WORLD_WIDTH;
import static com.massey.journey.main.Journey.SCALE;

public class MenuState extends GameState {

    private static final float FONT_SCALE = 1.5f;
    private static final float FONT_SCALE_PHONE = 2.5f;

    private static final float TITLE_WIDTH = 350 * FONT_SCALE;
    private static final float TITLE_HEIGHT = 50 * FONT_SCALE;
    private static final float TITLE_Y = 300;

    private static final float PLAY_BUTTON_WIDTH = 110 * FONT_SCALE;
    private static final float PLAY_BUTTON_HEIGHT = 25 * FONT_SCALE;
    private static final float PLAY_BUTTON_Y = 200;

    private static final float QUIT_BUTTON_WIDTH = 110 * FONT_SCALE;
    private static final float QUIT_BUTTON_HEIGHT = 25 * FONT_SCALE;
    private static final float QUIT_BUTTON_Y = 150;

    //Title Texture
    Texture title;

    //Button Textures
    Texture playButtonSelected;
    Texture playButtonUnselected;
    Texture quitButtonSelected;
    Texture quitButtonUnselected;

    private Stage stage;

    public MenuState(GameStateManager gsm) {
        super(gsm);

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
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        batch.begin();

        batch.draw(title, WORLD_WIDTH * SCALE / 2 - TITLE_WIDTH / 2, TITLE_Y,
                TITLE_WIDTH,
                TITLE_HEIGHT);

        float x = WORLD_WIDTH * SCALE / 2 - PLAY_BUTTON_WIDTH / 2;
        if(Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && WORLD_HEIGHT * SCALE - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && WORLD_HEIGHT * SCALE - Gdx.input.getY() > PLAY_BUTTON_Y){
            batch.draw(playButtonSelected, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH,
                    PLAY_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()) {
                gsm.setState(GameStateManager.PLAY);
            }
        } else {
            batch.draw(playButtonUnselected, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH,
                    PLAY_BUTTON_HEIGHT);
        }

        x = Journey.WORLD_WIDTH * Journey.SCALE / 2 - QUIT_BUTTON_WIDTH / 2;
        if(Gdx.input.getX() < x + QUIT_BUTTON_WIDTH && Gdx.input.getX() > x && WORLD_HEIGHT * SCALE - Gdx.input.getY() < QUIT_BUTTON_Y + QUIT_BUTTON_HEIGHT && WORLD_HEIGHT * SCALE- Gdx.input.getY() > QUIT_BUTTON_Y){
            batch.draw(quitButtonSelected, x, QUIT_BUTTON_Y, QUIT_BUTTON_WIDTH,
                    QUIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            batch.draw(quitButtonUnselected, x, QUIT_BUTTON_Y, QUIT_BUTTON_WIDTH,
                    QUIT_BUTTON_HEIGHT);
        }

        batch.end();
    }

    @Override
    public void dispose() {

    }
}

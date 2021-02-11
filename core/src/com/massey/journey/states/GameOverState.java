package com.massey.journey.states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.massey.journey.main.Journey;
import com.massey.journey.utilities.GameStateManager;

public class GameOverState extends GameState {

    private static final float FONT_SCALE = 1.5f;

    private static final float GAME_OVER_WIDTH = 190 * FONT_SCALE;
    private static final float GAME_OVER_HEIGHT = 94 * FONT_SCALE;
    private static final float GAME_OVER_Y = 235;

    private static final float RETRY_WIDTH = 138 * FONT_SCALE;
    private static final float RETRY_HEIGHT = 25 * FONT_SCALE;
    private static final float RETRY_Y = 160;

    private static final float MENU_WIDTH = 110 * FONT_SCALE;
    private static final float MENU_HEIGHT = 25 * FONT_SCALE;
    private static final float MENU_Y = 110;

    Texture gameOver;
    Texture retryUnselected;
    Texture retrySelected;
    Texture menuUnselected;
    Texture menuSelected;
    
    public GameOverState(GameStateManager gsm) {
        super(gsm);

        gameOver = new Texture("GAME_OVER.png");
        retrySelected = new Texture("RETRY_SELECTED.png");
        retryUnselected = new Texture("RETRY_UNSELECTED.png");
        menuSelected = new Texture("MENU_SELECTED.png");
        menuUnselected = new Texture("MENU_UNSELECTED.png");
    }


    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(gameOver, (Journey.WORLD_WIDTH * Journey.SCALE) / 2 - GAME_OVER_WIDTH / 2, GAME_OVER_Y,
                GAME_OVER_WIDTH,
                GAME_OVER_HEIGHT);

        float x = Journey.WORLD_WIDTH * Journey.SCALE / 2 - RETRY_WIDTH / 2;
        if(Gdx.input.getX() < x + RETRY_WIDTH && Gdx.input.getX() > x && Journey.WORLD_HEIGHT * Journey.SCALE - Gdx.input.getY() < RETRY_Y + RETRY_HEIGHT && Journey.WORLD_HEIGHT * Journey.SCALE - Gdx.input.getY() > RETRY_Y){
            batch.draw(retrySelected, x, RETRY_Y, RETRY_WIDTH,
                    RETRY_HEIGHT);
            if(Gdx.input.isTouched()) {
                Journey.res.getSound("gameover").stop();
                Journey.res.getMusic("TownTheme").play();
                gsm.setState(GameStateManager.PLAY);
            }
        } else {
            batch.draw(retryUnselected, x, RETRY_Y, RETRY_WIDTH,
                    RETRY_HEIGHT);
        }

        x = Journey.WORLD_WIDTH * Journey.SCALE / 2 - MENU_WIDTH / 2;
        if(Gdx.input.getX() < x + MENU_WIDTH && Gdx.input.getX() > x && Journey.WORLD_HEIGHT * Journey.SCALE - Gdx.input.getY() < MENU_Y + MENU_HEIGHT && Journey.WORLD_HEIGHT * Journey.SCALE - Gdx.input.getY() > MENU_Y){
            batch.draw(menuSelected, x, MENU_Y, MENU_WIDTH,
                    MENU_HEIGHT);
            if(Gdx.input.isTouched()) {
                Journey.res.getSound("gameover").stop();
                Journey.res.getMusic("TownTheme").play();
                gsm.setState(GameStateManager.MENU);
            }
        } else {
            batch.draw(menuUnselected, x, MENU_Y, MENU_WIDTH,
                    MENU_HEIGHT);
        }

        batch.end();


    }

    @Override
    public void dispose() {

    }
}

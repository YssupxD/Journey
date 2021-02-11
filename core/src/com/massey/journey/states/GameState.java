package com.massey.journey.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.massey.journey.main.Journey;
import com.massey.journey.utilities.GameStateManager;

public abstract class GameState {

    protected GameStateManager gsm;
    protected Journey game;

    protected SpriteBatch batch;
    protected OrthographicCamera gameCam;
    protected OrthographicCamera b2dCam;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        batch = game.getSpriteBatch();
        gameCam = game.getCamera();
        b2dCam = game.getCamera();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}

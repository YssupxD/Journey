package com.massey.journey.utilities;

import com.massey.journey.main.Journey;
import com.massey.journey.states.GameOverState;
import com.massey.journey.states.GameState;
import com.massey.journey.states.MenuState;
import com.massey.journey.states.PlayState;

import java.util.Stack;

public class GameStateManager {

    private Journey game;

    private Stack<GameState> gameStates;

    public static final int MENU = 817923;
    public static final int PLAY = 922873;
    public static final int OVER = 938475;


    public GameStateManager(Journey game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(MENU);
    }

    public Journey game() { return game; };

    public void update(float dt) {
        gameStates.peek().update(dt);
    }

    public void render() { gameStates.peek().render(); }

    private  GameState getState(int state) {
        if (state == PLAY) {
            return new PlayState(this);
        }
        if(state == MENU) {
            return new MenuState(this);
        }
        if(state == OVER) {
            return new GameOverState(this);
        }
        else {
            return null;
        }
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }
}

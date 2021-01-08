package com.massey.journey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.massey.journey.screens.MainGameScreen;

public class Journey extends Game {
	public static final int SCREEN_WIDTH = 400;
	public static final int SCREEN_HEIGHT = 320;
	public static final float PPM = 100;
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new MainGameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}

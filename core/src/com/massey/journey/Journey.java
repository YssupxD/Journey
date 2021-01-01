package com.massey.journey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.massey.journey.screens.MainGameScreen;

public class Journey extends Game {
	public static final int SCREEN_WIDTH = 720;
	public static final int SCREEN_HEIGHT = 480;
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

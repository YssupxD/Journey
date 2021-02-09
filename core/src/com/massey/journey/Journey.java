package com.massey.journey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.massey.journey.Utils.Content;
import com.massey.journey.screens.MainGameScreen;
import com.massey.journey.screens.MainMenuScreen;

public class Journey extends Game {
	public static final int VIRTUAL_WIDTH = 400;
	public static final int VIRTUAL_HEIGHT = 200;

	public static final int SCREEN_WIDTH = 1920;
	public static final int SCREEN_HEIGHT = 1080;

	public SpriteBatch batch;

	public static Content res;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));

		res = new Content();
		res.loadTexture("Dagger.png", "dagger");
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}

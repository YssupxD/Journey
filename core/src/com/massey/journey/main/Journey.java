package com.massey.journey.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.massey.journey.utilities.Content;
import com.massey.journey.utilities.GameStateManager;

import static com.massey.journey.utilities.Box2dVariables.PPM;

public class Journey implements ApplicationListener {

	public static final String TITLE = "Journey";
	public static final int WORLD_WIDTH = 400;
	public static final int WORLD_HEIGHT = 200;
	public static final int PHONE_WIDTH = 1920;
	public static final int PHONE_HEIGHT = 1080;
	public static final int SCALE = 2;
	public static final float STEP = 1 / 60f;

	private SpriteBatch batch;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;

	private GameStateManager gsm;

	public static Content res;

	@Override
	public void create() {

		cam = new OrthographicCamera();
		cam.setToOrtho(false, WORLD_WIDTH / PPM, WORLD_HEIGHT / PPM);

		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, WORLD_WIDTH / PPM, WORLD_HEIGHT / PPM);



		res = new Content();
		res.loadTexture("Diamond.png", "diamond");

		res.loadSoundEffect("gameover.ogg");
		res.loadSoundEffect("jump.wav");
		res.loadSoundEffect("collect.wav");
		res.loadSoundEffect("sword.wav");
		res.loadSoundEffect("hit.wav");

		res.loadMusic("TownTheme.mp3");
		res.getMusic("TownTheme").setLooping(true);
		res.getMusic("TownTheme").setVolume(0.5f);
		res.getMusic("TownTheme").play();

		batch = new SpriteBatch();

		gsm = new GameStateManager(this);
	}

	@Override
	public void dispose() {
		res.removeAll();
	}

	@Override
	public void pause() { }
	@Override
	public void resume() { }
	@Override
	public void resize(int width, int height) { }

	@Override
	public void render() {
		Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
	}

	public SpriteBatch getSpriteBatch() { return batch; }
	public OrthographicCamera getCamera() { return cam; }
	public OrthographicCamera getHudCam() { return hudCam; }

}

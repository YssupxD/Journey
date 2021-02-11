package com.massey.journey.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.massey.journey.main.Journey;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Journey.TITLE;
		config.foregroundFPS = 60;
		config.backgroundFPS = 30;
		config.width = Journey.WORLD_WIDTH * Journey.SCALE;
		config.height = Journey.WORLD_HEIGHT * Journey.SCALE;;
		config.resizable = false;
		new LwjglApplication(new Journey(), config);
	}
}

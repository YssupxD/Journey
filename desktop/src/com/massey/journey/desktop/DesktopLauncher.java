package com.massey.journey.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.massey.journey.Journey;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.backgroundFPS = 30;
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		new LwjglApplication(new Journey(), config);
	}
}

package com.martindilling.LD30.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.martindilling.LD30.LD30;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Game Name - Ludum Dare 30 - Martin Dilling-Hansen";
        config.width = 1024;
        config.height = 640;
        config.resizable = false;
        new LwjglApplication(new LD30(), config);
	}
}

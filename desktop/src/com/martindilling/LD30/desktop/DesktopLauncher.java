package com.martindilling.LD30.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.martindilling.LD30.LD30;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pastel Blocks - Martin Dilling-Hansen";
        config.width = 1024;
        config.height = 640;
        config.resizable = false;
        config.addIcon("icon128.png", Files.FileType.Internal);
        config.addIcon("icon64.png", Files.FileType.Internal);
        config.addIcon("icon32.png", Files.FileType.Internal);
        config.addIcon("icon16.png", Files.FileType.Internal);

        new LwjglApplication(new LD30(), config);
	}
}

package com.martindilling.LD30.loaders;

import com.martindilling.LD30.LD30;
import com.martindilling.LD30.screens.*;

/**
 * Project: LD30
 * Package: com.martindilling.LD30.screens
 * Author:  Martin
 * Date:    25-08-2014
 */
public class Screens
{
    public static StartScreen start;
    public static LevelScreen level;
    public static GameScreen game;
    public static PauseScreen pause;
    public static GameOverScreen gameOver;
    public static ClearedScreen cleared;
    public static HelpScreen help;

    public static void load(LD30 gameInstance) {
//        LD30.log("Loading", "Screens");
        start = new StartScreen(gameInstance);
        level = new LevelScreen(gameInstance);
        game = new GameScreen(gameInstance);
        pause = new PauseScreen(gameInstance);
        gameOver = new GameOverScreen(gameInstance);
        cleared = new ClearedScreen(gameInstance);
        help = new HelpScreen(gameInstance);
    }

    public static GameScreen getLevel(int level) {
        game.loadLevel(level);
//        LD30.log("State", LD30.state+"");
        return game;
    }

    public static GameScreen restartLevel() {
        game.restartLevel();
//        LD30.log("State", LD30.state + "");
        return game;
    }

    public static void dispose() {
//        LD30.log("Disposing", "Screens");
        start.dispose();
        level.dispose();
        game.dispose();
        pause.dispose();
        gameOver.dispose();
        cleared.dispose();
        help.dispose();
    }

}

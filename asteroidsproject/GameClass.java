package com.example.asteroidsproject;
import com.badlogic.gdx.Game;

import java.util.Random;

public class GameClass extends Game {

	GameScreen gameScreen;

	public static Random random = new Random();

	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}



	@Override
	public void render () {
		super.render();

	}

	//close the game screen
	@Override
	public void dispose () {
		gameScreen.dispose();
	}

	@Override
	public void pause() {
		gameScreen.pause();
	}
}

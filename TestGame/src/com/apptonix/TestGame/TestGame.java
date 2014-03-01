package com.apptonix.TestGame;

import com.apptonix.Screens.GameScreen;
import com.apptonix.TGHelpers.AssetLoader;
import com.badlogic.gdx.Game;

public class TestGame extends Game {

	// Create the game
	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	
	// Dispose of everything in the game when this is called
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
	
}

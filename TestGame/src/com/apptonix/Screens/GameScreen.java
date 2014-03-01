package com.apptonix.Screens;

import com.apptonix.GameWorld.GameRenderer;
import com.apptonix.GameWorld.GameWorld;
import com.apptonix.TGHelpers.InputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	
	// Set the game screen fields
	private GameWorld world;
	private GameRenderer renderer;
	
	// Set the runtime field to 0
	private float runTime = 0;

	public GameScreen() {
		
		// Initialize the game screen fields
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		
		// Set the games mid point
		int midPointY = (int) (gameHeight / 2);
		
		// Create game instances
		world = new GameWorld(midPointY);
		renderer = new GameRenderer(world, (int) gameHeight, midPointY);
		
		// Set the input processor class up for the scren
		Gdx.input.setInputProcessor(new InputHandler(world));
		
	}
	
	// This is performed every frame
	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(runTime);
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}

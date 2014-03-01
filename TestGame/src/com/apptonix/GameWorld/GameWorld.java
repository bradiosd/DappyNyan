package com.apptonix.GameWorld;

import com.apptonix.GameObjects.Player;
import com.apptonix.GameObjects.ScrollHandler;
import com.apptonix.TGHelpers.AssetLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld {
	
	// Set the game world fields
	private Player bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int midPointY;
	private int score;
	private int highScore;
	
	// Set the sound stopper field
	private boolean soundAllowed = true;
	
	// Set the game state fields
	public enum GameState { READY, RUNNING, GAMEOVER }
	private GameState currentState;
	
	public GameWorld(int midPointY) {
		
		// Initialize the game world objects
		bird = new Player(33, midPointY - 5, 26, 16);
		ground = new Rectangle(0, midPointY + 66, 136, 11);
		this.midPointY = midPointY;
		
		// Set the grass above the mid point
		scroller = new ScrollHandler(this, midPointY + 66);
		
		// Initialize the game states
		currentState = GameState.READY;
		
	}
	
	public void update(float delta) {
		
		// Call methods depending on what the game state is
		switch (currentState) {
			case READY:
				updateReady(delta);
				break;
			case RUNNING:
				default:
					updateRunning(delta);
					break;
		}
		
	}
	
	// Performed when the game has just started or restarted
	private void updateReady(float delta) {
		// Start the music
		AssetLoader.music.play();
	}
	
	// Performed when the game is running
	private void updateRunning(float delta) {
		
		// Add a delta cap to avoid messing with collision detection
		if (delta > 0.15f) {
			delta = 0.15f;
		}
		
		// Call the subclasses sub-methods
		bird.update(delta);
		scroller.update(delta);
		
		// If the bird is alive and collides with an object
		if (scroller.collides(bird) && bird.isAlive()) {
			
	        // Call subclasses methods to stop the objects
	        scroller.stop();
	        bird.die();
	        
	        // Only stop the music and play the dead sound if sounds are enabled
	        if (soundAllowed != false) {
				AssetLoader.dead.play();
				disallowSounds();
			}
	        
	        // Stop the music
	        AssetLoader.music.stop();
	    }
		
		// Check if the bird has hit the ground
		if (Intersector.overlaps(bird.getBoundingBox(), ground)) {
			
			// Stop the scroller and the bird
			scroller.stop();
			bird.die();
			bird.decelerate();
			
			// Set the game state to game over
			currentState = GameState.GAMEOVER;
			
	        // Only stop the music and play the dead sound if sounds are enabled
			if (soundAllowed != false) {
				AssetLoader.dead.play();
				disallowSounds();
			}
			
			// Stop the music
			AssetLoader.music.stop();
		}
		
	}
	
	// Set the game back to how it is at the start
	public void restart() {
		currentState = GameState.READY;
		scroller.getPipe1().setGapSize(90);
		score = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		AssetLoader.music.play();
		soundAllowed = true;
	}
	
	/*
	 * Set the getters and setters
	 */
	public boolean isReady() {
		return currentState == GameState.READY;
	}
	
	public void start() {
		currentState = GameState.RUNNING;
	}
	
	public boolean isGameOver() {
		if (this.score > this.highScore) {
			this.highScore = this.score;
		}
		
		return currentState == GameState.GAMEOVER;
	}
	
	public Player getBird() {
		return bird;
	}
	
	public ScrollHandler getScroller() {
		return scroller;
	}
	
	public void disallowSounds() {
		soundAllowed = false;
	}

	public void addScore(int increment) {
		score += increment;
		
		// Check if the score is divisible by 20 and below 80 so the gap doesn't get to 0
		if ((score % 10) == 0 && score <= 40) {
			scroller.getPipe1().setGapSize(scroller.getPipe1().getGapSize() - 10);
			AssetLoader.levelup.play();
		}
	}

	public int getScore() {
		return this.score;
	}

	public int getHighestScore() {
		return this.highScore;
	}
	
}

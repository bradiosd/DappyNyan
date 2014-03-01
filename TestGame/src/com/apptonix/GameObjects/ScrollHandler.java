package com.apptonix.GameObjects;

import com.apptonix.GameWorld.GameWorld;
import com.apptonix.TGHelpers.AssetLoader;

public class ScrollHandler {
	
	// Set the GameWorld object
	private GameWorld gameWorld;
	
	// Set the object fields
    private Grass frontGrass, backGrass;
    private Bar pipe1, pipe2, pipe3;

    // Set the scroll speed and the gap width between pipes
    public static final int SCROLL_SPEED = -50;
    public static final int PIPE_GAP = 50;
    
    public ScrollHandler(GameWorld gameWorld, float yPos) {
    	
    	// Initialize the scroll handler
    	this.gameWorld = gameWorld;
    	
    	// Initialize the front and back grass
    	frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);

        // Initialize the pipes
        pipe1 = new Bar(210, 0, 22, 60, SCROLL_SPEED, yPos);
        pipe2 = new Bar(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        pipe3 = new Bar(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
        
    }
    
    // This is called every frame
    public void update(float delta) {
    	
    	// Call our subclasses sub-methods
        frontGrass.update(delta);
        backGrass.update(delta);
        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);
        
        // If a pipe has gone off the screen then reset it's position back to the front
        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);
        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }

        // If the front or back grass has gone off the screen then reset it's poition back to the front
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());
        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }
        
    }
    
    // This method calls all the subclasses sub-methods
    public void onRestart() {
    	frontGrass.onRestart(0, SCROLL_SPEED);
        backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
        pipe1.onRestart(210, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
    }
    
    // This method calls all the subclasses sub-methods
    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
    }

    // This method checks if anything has collided with the player
    public boolean collides(Player player) {
    	
    	// If the player has passed a pipe then give them a point and play the coin sound
    	if (!pipe1.isScored() && pipe1.getX() + (pipe1.getWidth()) < player.getX() + player.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            AssetLoader.point.play();
        } else if (!pipe2.isScored() && pipe2.getX() + (pipe2.getWidth()) < player.getX() + player.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            AssetLoader.point.play();
        } else if (!pipe3.isScored() && pipe3.getX() + (pipe3.getWidth()) < player.getX() + player.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            AssetLoader.point.play();
        }
    	
    	// Return true if the player has collided with the pip
        return (pipe1.collides(player) || pipe2.collides(player) || pipe3.collides(player));
    }
    
    // Add a point to the players score
    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

    // The getters for our five instance variables
    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Bar getPipe1() {
        return pipe1;
    }

    public Bar getPipe2() {
        return pipe2;
    }

    public Bar getPipe3() {
        return pipe3;
    }
    
}

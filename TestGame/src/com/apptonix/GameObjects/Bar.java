package com.apptonix.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Bar extends Scrollable {

	// Set a random field for bar gap positioning
	private Random r;
	
	// Set the bar fields
	private Rectangle barBarUp, barBarDown;

	// Set bar fields
	// TODO Make the gap an int so we can adjust level difficulty
    public static final int BAR_WIDTH = 24;
    public static final int BAR_HEIGHT = 11;
    
    private static int VERTICAL_GAP = 90;
    
    // Set the groundY field so the pipes know where to situate
    private float groundY;
    
    // Set field to check if a point is scored
	private boolean isScored;
	
    public Bar(float x, float y, int width, int height, float scrollSpeed, float groundY) {
    	
    	// These fields are inherited from the super class
        super(x, y, width, height, scrollSpeed);
        
        // Initialize the classes instance variables
        r = new Random();
        barBarUp = new Rectangle();
        barBarDown = new Rectangle();
        this.groundY = groundY;
    }
    
    // This is run every frame
    @Override
    public void update(float delta) {
    	
        // This method extends another in the super class
        super.update(delta);

        // Set the bar positions
        barBarUp.set(
        		position.x,
        		position.y,
        		width,
        		height);
        barBarDown.set(
        		position.x,
        		position.y + height + VERTICAL_GAP,
        		width,
        		groundY - (position.y + height + VERTICAL_GAP));
        
    }
    
    // Restart the scroller when the onRestart method is called
    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    // This method is called when the bar reaches the end of the screen
    @Override
    public void reset(float newX) {
    	
        // This method extends another in the super class
        super.reset(newX);
        
        // Set the bar ends position to a random position
        height = r.nextInt(60) + 15;
        
        // Reset the isScored field so we can score a point on the next bar
        isScored = false;
        
    }
    
    // This method is called when the bar collides with the player
    public boolean collides(Player player) {
    	
    	// If the players right position is past the left of the bar
        if (position.x < player.getX() + player.getWidth()) {
        	
        	// Return true if the player overlaps 
            return (
            		Intersector.overlaps(player.getBoundingBox(), barBarUp) ||
            		Intersector.overlaps(player.getBoundingBox(), barBarDown));
            
        }
        
        // Return false if no collision is detected
        return false;
    }
    
    /*
     * Set the getters and setters
     */
    public int getGapSize() {
    	return VERTICAL_GAP;
    }
    
    public void setGapSize(int size) {
    	VERTICAL_GAP = size;
    }

    public Rectangle getBarUp() {
        return barBarUp;
    }

    public Rectangle getBarDown() {
        return barBarDown;
    }
    
    public boolean isScored() {
    	return isScored;
    }
    
    public void setScored(boolean b) {
		isScored = b;
	}
	
}

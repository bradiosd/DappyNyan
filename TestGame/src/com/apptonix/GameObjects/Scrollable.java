package com.apptonix.GameObjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {
    
	// These fields are protected so that subclasses can inherit them
	
    // Set the fields for the scroller positioning and velocity
    protected Vector2 position;
    protected Vector2 velocity;
    
    // Set the scrollers width and height
    protected int width;
    protected int height;
    
    // Check if the scroller object has gone off the screen
    protected boolean isScrolledLeft;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
    	
    	// Initialize the object
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isScrolledLeft = false;
        
    }

    // This is performed every frame
    public void update(float delta) {
    	
    	// Set the scrollers velocity
        position.add(velocity.cpy().scl(delta));

        // If the scroller goes off the screen then set the variable
        if (position.x + width < 0) {
            isScrolledLeft = true;
        }
    }
    
    // This method will restart the scrollers position and velocity after a Game Over
    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }

    // This method is called when Game Over is initiated and stops the scroller
    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
    }

    /*
     * Set some getters and setters
     */
    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }
    
    public void stop() {
        velocity.x = 0;
    }

    public float getTailX() {
        return position.x + width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}

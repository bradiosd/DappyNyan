package com.apptonix.GameObjects;

public class Grass extends Scrollable {
	
    public Grass(float x, float y, int width, int height, float scrollSpeed) {
    	
    	// These fields are inherited
        super(x, y, width, height, scrollSpeed);
        
    }
    
    // Grasses position and velocity should reset when this is called
    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }

}

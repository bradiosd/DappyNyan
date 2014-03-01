package com.apptonix.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	// Set player Vector fields
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	
	// Set player sprite fields
	private float rotation;
	private int width;
	private int height;
	
	// Set player collision bounding fields
	private Rectangle boundingBox;
	
	// Set player state fields
	private boolean isAlive = true;
	
	public Player(float x, float y, int width, int height) {
		
		// Initialize the player
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);
		boundingBox = new Rectangle();
	}
	
	// This will be run every frame
	public void update(float delta) {
		
		// Scale up the players velocity every frame
        velocity.add(acceleration.cpy().scl(delta));

        // Set the players max velocity
        if (velocity.y > 140) {
            velocity.y = 140;
        }
        
        // Stop the player from going too high
		if (position.y < -13) {
	     	position.y = -13;
	     	velocity.y = 0;
	     }

		// Update the players position based on the velocity change
        position.add(velocity.cpy().scl(delta));
        
        // Creates the collision bounding box on top of the player
        boundingBox.set(position.x + 2, position.y + 2, width - 4, height - 4);
    }
	
	// Reset the player back to the starting position on a Game Over
	public void onRestart(int y) {
		rotation = 0;
		position.y = y;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = 460;
		isAlive = true;
	}
	
	// What to do when the player has died
	public void die() {
		isAlive = false;
		velocity.y = 0;
	}
	
	// Not really sure what this does or how it works
	public void decelerate() {
		acceleration.y = 0;
	}
	
	// If the player is falling return true
	public boolean isFalling() {
		return velocity.y > 110;
	}
	
	// If the player is dead then stop them flying
	public boolean shouldntFly() {
		return velocity.y > 70 || !isAlive;
	}
	
	// Make the player fly when onClick is called
    public void onClick() {
        if (isAlive()) {
        	velocity.y = -140;
        }
    }
    
    /*
     * Set the setters and getters
     */
    public boolean isAlive() {
		return isAlive;
	}
    
    public float getX() {
        return position.x;
    }
    
    public void setX(int positionX) {
    	this.position.x = positionX;
    }

    public float getY() {
        return position.y;
    }
    
    public void setY(float positionY) {
    	this.position.y = positionY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
    
    public Rectangle getBoundingBox() {
    	return boundingBox;
    }
	
}

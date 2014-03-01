package com.apptonix.TGHelpers;

import com.apptonix.GameObjects.Player;
import com.apptonix.GameWorld.GameWorld;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
	
	// Set the game world and player fields
	private GameWorld myWorld;
	private Player player;
	
	public InputHandler(GameWorld myWorld) {
		
		// Initialize the instance
		this.myWorld = myWorld;
		player = myWorld.getBird();
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (myWorld.isReady()) {
            myWorld.start();
        }
		
		// Call the birds onClick method
		player.onClick();
		
		if (myWorld.isGameOver()) {
            // Reset all variables, go to GameState.READ
            myWorld.restart();
        }
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}

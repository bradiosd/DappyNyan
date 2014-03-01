package com.apptonix.GameWorld;

import com.apptonix.GameObjects.Player;
import com.apptonix.GameObjects.Grass;
import com.apptonix.GameObjects.Bar;
import com.apptonix.GameObjects.ScrollHandler;
import com.apptonix.TGHelpers.AssetLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameRenderer {

	// Set the world and camera fields
	private GameWorld myWorld;
	private OrthographicCamera cam;
	
	// Set the shape renderer for debugging collisions
	private ShapeRenderer shapeRenderer;
	
	// Set the game screen dimensions fields
	private int midPointY;
	private int gameHeight;
    
    // Set the game object fields
    private Player player;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Bar pipe1, pipe2, pipe3;
    
    // Set the fields for sprites, animations and sounds
 	private SpriteBatch batcher;
	private TextureRegion bg, grass, grassBottom;
    private Animation nyanAnimation;
	private TextureRegion nyan1, nyan2, nyan3;
    private TextureRegion bar;
    
    // Set the debugging field
    private boolean debug_renderMode = false;
	
	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		
		// Initialize the game instances
		myWorld = world;
		this.gameHeight = gameHeight;
		this.midPointY = midPointY;
		
		// Initialize the camera
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, 204);
		
		// Attach the batcher to the camera so it can see the sprites
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		
		// Attach the shape rendered to the camera so we can see the collision bounding boxes
		shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        // Call the asset and object loaders
        initGameObjects();
        initAssets();
        
	}
	
	// This is run every frame
	public void render(float runTime) {
		
		// Set the background to null every render to stop screen tearing otherwise you see a weird matrix effect
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        // ShapeRenderer is used for draw items that are not sprites
        
        // Begin the shape renderer for the game shapes
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color / the sky
        shapeRenderer.setColor(0 / 255.0f, 73 / 255.0f, 121 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);
        
        // End the renderer for game shapes
        shapeRenderer.end();
        
        // SpriteBatchers are used for draw items that are sprites
        
        // Start a batcher for the game sprites
        batcher.begin();
        batcher.disableBlending(); // See libgdx guide about blending
        batcher.draw(bg, 0, midPointY + 23, 136, 43);
        
        // Draw the grass and pipe sprites
        drawGrass();
        drawPipes();
        
        // Enable blending again for the skulls as they contain transparency
        batcher.enableBlending();
        
        // Draw the score
        if (myWorld.isReady()) {
        	// Draw some text for the Game is Ready state
            AssetLoader.shadow.draw(batcher, "Touch me =)", (136 / 2) - (55), 86);
            AssetLoader.font.draw(batcher, "Touch me =)", (136 / 2) - (55 - 1), 85);
        } else {
            if (myWorld.isGameOver()) {
            	// Draw some text on the Game Over state
                AssetLoader.shadow.draw(batcher, "Silly Nyan!", 25, 66);
                AssetLoader.font.draw(batcher, "Silly Nyan!", 26, 65);
                
                AssetLoader.shadow.draw(batcher, "Nyan again?", 18, 86);
                AssetLoader.font.draw(batcher, "Nyan again?", 17, 85);
                
                AssetLoader.shadow.draw(batcher, "Top Score", 24, 126);
                AssetLoader.font.draw(batcher, "Top Score", 23, 125);
                
                String highScore = String.valueOf(myWorld.getHighestScore());
                AssetLoader.shadow.draw(batcher, String.valueOf(highScore), (136 / 2) - (6 * highScore.length()), 146);
                AssetLoader.font.draw(batcher, String.valueOf(highScore), (136 / 2) - (6 * highScore.length()) - 1, 145);
            }
            
            // Draw the score
            String score = String.valueOf(myWorld.getScore());
            AssetLoader.shadow.draw(batcher, score, (136 / 2) - (6 * score.length()), 24);
            AssetLoader.font.draw(batcher, score, (136 / 2) - (6 * score.length() - 1), 23);
        }

        // If the player shouldn't be allowed to fly then stop it moving
        if (player.shouldntFly()) {
            batcher.draw(
            		nyan1, player.getX(),
            		player.getY(),
                    player.getWidth() / 2.0f,
                    player.getHeight() / 2.0f,
                    player.getWidth(),
                    player.getHeight(),
                    1,
                    1,
                    player.getRotation());

        } else {
            batcher.draw(
            		nyanAnimation.getKeyFrame(runTime),
            		player.getX(),
                    player.getY(),
                    player.getWidth() / 2.0f,
                    player.getHeight() / 2.0f,
                    player.getWidth(),
                    player.getHeight(),
                    1,
                    1,
                    player.getRotation());
        }

        // End the batcher as no more sprites are needed
        batcher.end();
        
        // Start debug mode drawing
        if (debug_renderMode) {
        	
        	// Draw the birds collision box
        	shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(
            		player.getBoundingBox().x,
            		player.getBoundingBox().y,
            		player.getBoundingBox().width,
            		player.getBoundingBox().height);
            
            // Draw the pipes collision boxes
			shapeRenderer.rect(pipe1.getBarUp().x, pipe1.getBarUp().y,
			        pipe1.getBarUp().width, pipe1.getBarUp().height);
			shapeRenderer.rect(pipe2.getBarUp().x, pipe2.getBarUp().y,
			        pipe2.getBarUp().width, pipe2.getBarUp().height);
			shapeRenderer.rect(pipe3.getBarUp().x, pipe3.getBarUp().y,
			        pipe3.getBarUp().width, pipe3.getBarUp().height);
			shapeRenderer.rect(pipe1.getBarDown().x, pipe1.getBarDown().y,
			        pipe1.getBarDown().width, pipe1.getBarDown().height);
			shapeRenderer.rect(pipe2.getBarDown().x, pipe2.getBarDown().y,
			        pipe2.getBarDown().width, pipe2.getBarDown().height);
			shapeRenderer.rect(pipe3.getBarDown().x, pipe3.getBarDown().y,
			        pipe3.getBarDown().width, pipe3.getBarDown().height);
			
			// End the shape renderer for the debug mode
			shapeRenderer.end();
        }
        
    }
	
	// Initialize the game objects
	private void initGameObjects() {
		player = myWorld.getBird();
		scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
	}
	
	// Initialize the sound and sprite files
	private void initAssets() {
		bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        nyanAnimation = AssetLoader.nyanAnimation;
        nyan1 = AssetLoader.nyan2;
        nyan2 = AssetLoader.nyan2;
        nyan3 = AssetLoader.nyan3;
        bar = AssetLoader.bar;
	}
	
	// Draw the grass sprite
	private void drawGrass() {
        batcher.draw(
        		grass,
        		frontGrass.getX(),
        		frontGrass.getY(),
                frontGrass.getWidth(),
                frontGrass.getHeight());
        		batcher.draw(grass, backGrass.getX(),
        		backGrass.getY(),
                backGrass.getWidth(),
                backGrass.getHeight());
    }

    // Draw the bars themselves
    private void drawPipes() {
        batcher.draw(
        		bar,
        		pipe1.getX(),
        		pipe1.getY(),
        		pipe1.getWidth(),
                pipe1.getHeight());
        
        batcher.draw(
        		bar,
        		pipe1.getX(),
        		pipe1.getY() + pipe1.getHeight() + pipe1.getGapSize(),
                pipe1.getWidth(),
                midPointY + 66 - (pipe1.getHeight() + pipe1.getGapSize()));

        batcher.draw(
        		bar,
        		pipe2.getX(),
        		pipe2.getY(),
        		pipe2.getWidth(),
                pipe2.getHeight());
        
        batcher.draw(
        		bar,
        		pipe2.getX(),
        		pipe2.getY() + pipe2.getHeight() + pipe2.getGapSize(),
                pipe2.getWidth(),
                midPointY + 66 - (pipe2.getHeight() + pipe2.getGapSize()));

        batcher.draw(
        		bar,
        		pipe3.getX(),
        		pipe3.getY(),
        		pipe3.getWidth(),
                pipe3.getHeight());
        
        batcher.draw(
        		bar,
        		pipe3.getX(),
        		pipe3.getY() + pipe3.getHeight() + pipe3.getGapSize(),
                pipe3.getWidth(),
                midPointY + 66 - (pipe3.getHeight() + pipe3.getGapSize()));
    }
	
}

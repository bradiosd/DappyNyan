package com.apptonix.TGHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	// Set the asset loader fields
    public static Texture texture;
    public static TextureRegion bg, grass, grassBottom;

    public static Animation nyanAnimation;
    public static TextureRegion nyan1, nyan2, nyan3;

    public static TextureRegion barUp, barDown, bar;
    
    public static BitmapFont font, shadow;
    
    public static Sound dead, point, levelup;
    public static Music music;

    public static void load() {
    	
    	// Load the main texture file
        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        // Create the background from the texture file
        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);

        // Create the grass from the texture file
        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);
        
        // Create the bottom grass sprite from the texutre file
        grassBottom = new TextureRegion(texture, 0, 59, 10, 10);

        // Create the player sprites from the texture file
        nyan1 = new TextureRegion(texture, 178, 48, 26, 16);
        nyan1.flip(false, true);

        nyan2 = new TextureRegion(texture, 204, 48, 26, 16);
        nyan2.flip(false, true);
        
        nyan3 = new TextureRegion(texture, 230, 48, 26, 16);
        nyan3.flip(false, true);

        // Merge these sprites into an animation for the player
        TextureRegion[] nyanFrames = { nyan1, nyan2, nyan3 };
        nyanAnimation = new Animation(0.06f, nyanFrames);
        nyanAnimation.setPlayMode(Animation.LOOP_PINGPONG);

        // Set the bar sprite from the texture file
        barUp = new TextureRegion(texture, 192, 0, 24, 14);
        
        // Flip the bar end upside down for up pipes
        barDown = new TextureRegion(barUp);
        barDown.flip(false, true);

        // Create the bar sprite from the texture file
        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);
        
        // Create a font map from a font file
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(0.25f, -0.25f);
        
        // Create the shadow font map from the font file
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(0.25f, -0.25f);
        
        // Create the sounds for the game from files
        music = Gdx.audio.newMusic(Gdx.files.internal("data/music.ogg"));
        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        point = Gdx.audio.newSound(Gdx.files.internal("data/point.wav"));
        levelup = Gdx.audio.newSound(Gdx.files.internal("data/levelup.ogg"));
        
    }
    
    // Dispose of the assets when we are done with them
    public static void dispose() {
        texture.dispose();
        music.dispose();
        dead.dispose();
        point.dispose();
        levelup.dispose();
        font.dispose();
        shadow.dispose();
    }

}


package com.Betulis.Game2D.engine.system;

import com.Betulis.Game2D.engine.audio.AudioManager;
import com.Betulis.Game2D.engine.render.DebugRender;
import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.scenes.DeathValley;
import com.Betulis.Game2D.game.ui.UIAssetGenerator;
import com.Betulis.Game2D.game.ui.UIManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private Scene scene;
    private InputBindings input;
    private DebugRender debugRender;
    private BitmapFont font;
    private Assets assets;
    private AudioManager audioManager;
    private UIManager ui;

    //screen
    private int screenWidth, screenHeight;

    // fps and time
    private int fps;
    private float timeSeconds = 0.0f;

    @Override
    public void create() {
        System.out.println("Starting game...");
        batch = new SpriteBatch();
        font = new BitmapFont();

        initWindow();
        initInput();
        initSystems();


        scene = new DeathValley();
        scene.load(this);

        UIAssetGenerator.generateIfAbsent();
        ui = new UIManager();
        ui.init(this, ((DeathValley) scene).getPlayerXP());
        ((DeathValley) scene).wireUI(ui);
    }

    public void initWindow() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    public void initInput() {
        input = new InputBindings();
        Gdx.input.setInputProcessor(input);
    }

    public void initSystems() {
        assets = new Assets();
        assets.load();
        debugRender = new DebugRender(font, getAssets().getPixel());
        audioManager = new AudioManager();
        audioManager.load();
    }


    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        fps = Gdx.graphics.getFramesPerSecond();
        float dt = Gdx.graphics.getDeltaTime();
        timeSeconds += dt;
        
        scene.update(dt); //update
        ui.update(dt);    //ui update

        batch.begin();
        scene.render(batch);        //render
        scene.renderOverlay(batch); //overlay (floating text, etc.)
        ui.render(batch);           //ui render
        debugRender.render(scene, batch);
        batch.end();
    }
    
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() { batch.dispose(); audioManager.dispose(); }


    public InputBindings getInput() {
        return input;
    }

    public Assets getAssets() {
        return assets;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getFps() {
        return fps;
    }

    public float getTime() {
        return timeSeconds;
    }

    public UIManager getUI() {
        return ui;
    }

    public Scene getScene() {
        return scene;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
}

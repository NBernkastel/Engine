package Scenes;

import Engine.*;
import Objects.Button;
import components.Sprite;
import components.SpriteRenderer;
import eventHandlers.MouseListener;
import org.joml.Vector2f;
import util.AssetPool;

public class Menu extends Scene {
    public Menu() {
    }
    Button resumeButton, settings;
    Camera camera;
    @Override
    public void init() {
        loadResources();
        camera = new Camera(new Vector2f(0,0));
        resumeButton = new Button("resumeButton", new Transform(new Vector2f(500,500),new Vector2f(256,64)),0);
        resumeButton.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/resume_button.png"))));
        settings = new Button("settings", new Transform(new Vector2f(500,350), new Vector2f(256,64)),0);
      //  settings.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/resume_button.png"))));
        addGameObjectToScene(resumeButton);
        addGameObjectToScene(settings);

    }
    private boolean buttonsUpdate(Button button, float dt){
        if ((MouseListener.getX() > button.transform.position.x && MouseListener.getX() < button.transform.position.x + button.transform.scale.x)
                && (MouseListener.getY() > button.transform.position.y && MouseListener.getY() < button.transform.position.y + button.transform.scale.y)){
            if ((button.transform.scale.x < button.getBaseScale().x*1.1 && button.transform.scale.y < button.getBaseScale().y*1.1)) {
                button.transform.scale.x -= 2000 * dt;
                button.transform.scale.y -= 500 * dt;
                button.transform.position.x += 750*dt;
                button.transform.position.y += 200*dt;
            }
            if (MouseListener.mouseButtonDown(0))
                return true;
            else
                return false;
        }
        else {
            button.transform.scale = new Vector2f(256, 64);
            button.transform.position = new Vector2f(button.getBasePosition().x,button.getBasePosition().y);
        }
        return false;
    }
    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        if (buttonsUpdate(resumeButton, dt) == true){
            Window.get().isMenuCall = false;
        }
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }
    @Override
    public Camera camera() {
        return camera;
    }
}

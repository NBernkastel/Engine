package Scenes;

import Engine.*;
import components.Sprite;
import components.SpriteRenderer;
import eventHandlers.MouseListener;
import org.joml.Vector2f;
import util.AssetPool;

public class Menu extends Scene {
    public Menu() {
    }
    GameObject resumeButton, settings;
    Camera camera;
    @Override
    public void init() {
        loadResources();
        camera = new Camera(new Vector2f(0,0));
        resumeButton = new GameObject("resumeButton", new Transform(new Vector2f(500,500),new Vector2f(256,64)),0);
        resumeButton.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/resume_button.png"))));
        settings = new GameObject("settings", new Transform(new Vector2f(500,350), new Vector2f(256,64)),0);
       // settings.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/settings_button.png"))));
        addGameObjectToScene(resumeButton);
        addGameObjectToScene(settings);

    }
    private void buttonsUpdate(GameObject go, float dt,float sizeX, float sizeY){
        if ((MouseListener.getX() > go.transform.position.x && MouseListener.getX() < go.transform.position.x + go.transform.scale.x)
                && (MouseListener.getY() > go.transform.position.y && MouseListener.getY() < go.transform.position.y + go.transform.scale.y)){
            if ((go.transform.scale.x < sizeX*1.15 && go.transform.scale.y < sizeY*1.15)) {
                go.transform.scale.x -= 2000 * dt;
                go.transform.scale.y -= 500 * dt;
                go.transform.position.x += 750*dt;
                go.transform.position.y += 200*dt;
            }
            if (MouseListener.mouseButtonDown(0))
                Window.isMenuCall = false;
        }
        else {
            go.transform.scale = new Vector2f(256, 64);
            go.transform.position = new Vector2f(500,500);
        }
    }
    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {

        buttonsUpdate(resumeButton, dt,resumeButton.transform.scale.x,resumeButton.transform.scale.y);
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

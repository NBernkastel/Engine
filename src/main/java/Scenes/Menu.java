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
    GameObject resumeButton;
    Camera camera;
    @Override
    public void init() {
        loadResources();
        camera = new Camera(new Vector2f(0,0));
        resumeButton = new GameObject("resumeButton", new Transform(new Vector2f(500,500),new Vector2f(256,64)),0);
        resumeButton.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/resume_button.png"))));
        addGameObjectToScene(resumeButton);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        if ((MouseListener.getX() > 780 && MouseListener.getX() < 1180)&& (MouseListener.getY() > 160 && MouseListener.getY() < 260)){
            if ((resumeButton.transform.scale.x < 256*1.15 && resumeButton.transform.scale.y < 64*1.15)) {
                resumeButton.transform.scale.x -= 2000 * dt;
                resumeButton.transform.scale.y -= 500 * dt;
                resumeButton.transform.position.x += 750*dt;
                resumeButton.transform.position.y += 200*dt;
            }
            if (MouseListener.mouseButtonDown(0))
                Window.isMenuCall = false;
        }
        else {
            resumeButton.transform.scale = new Vector2f(256, 64);
            resumeButton.transform.position = new Vector2f(500,500);
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

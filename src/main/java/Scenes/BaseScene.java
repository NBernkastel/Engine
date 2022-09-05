package Scenes;

import Engine.Camera;
import Engine.GameObject;
import Engine.Scene;
import Engine.Transform;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import eventHandlers.KeyListener;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import util.AssetPool;

import static org.lwjgl.glfw.GLFW.*;

public class BaseScene extends Scene {
    public BaseScene() {
    }
    Menu menu;
    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(0, 0));
        GameObject object1 = new GameObject("test",new Transform(new Vector2f(200f,200f),new Vector2f(500,500)),10);
        object1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage1.png"))));
        GameObject object2 = new GameObject("test",new Transform(new Vector2f(300f,100f),new Vector2f(500,500)),-20);
        object2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage2.png"))));
        addGameObjectToScene(object2);
        addGameObjectToScene(object1);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/spritesheet.png",new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                16,16,26,0));
    }

    @Override
    public void update(float dt) {
        camera.update(dt);
        if (KeyListener.isKeyPressed(GLFW_KEY_E))

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}

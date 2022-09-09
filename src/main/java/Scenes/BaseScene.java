package Scenes;

import Engine.*;
import Objects.Player;
import components.Animation;
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

    Player player;
    Camera camera;
    Spritesheet spritesheet;

    @Override
    public void init() {
        loadResources();
        spritesheet = AssetPool.getSpritesheet("assets/images/player.png");
        this.camera = new Camera(new Vector2f(0, 0));
        player = new Player("player", new Transform(new Vector2f(500, 200), new Vector2f(64, 64)), 0);
        addGameObjectToScene(player);
        camera.isCameraLocked = true;
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/player.png", new Spritesheet(AssetPool.getTexture("assets/images/player.png"), 32, 32, 68, 0));
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
            Window.isMenuCall = true;
            camera.isCameraLocked = true;
        }
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        if (!camera.isCameraLocked)
            camera.update(dt);
        this.renderer.render();
    }

    @Override
    public Camera camera() {
        return camera;
    }
}

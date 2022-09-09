package Scenes;

import Engine.*;
import Objects.Player;
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
    public Spritesheet plsheet  = AssetPool.getSpritesheet("assets/images/player.png");
    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(0, 0));
        player = new Player("player",new Transform(new Vector2f(500,200),new Vector2f(133,117)),0);
        player.addComponent(new SpriteRenderer(plsheet.getSprite(0)));
        addGameObjectToScene(player);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/player.png",new Spritesheet(AssetPool.getTexture("assets/images/player.png"),32,32,2,0));
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)){
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

package Scenes;

import Engine.GameObject;
import Engine.Scene;
import Engine.Transform;
import components.Sprite;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.lwjgl.system.CallbackI;
import renderer.Texture;
import util.AssetPool;

public class Menu extends Scene {
    GameObject startButton;
    @Override
    public void init(){
        loadResources();
        startButton = new GameObject("start",new Transform(new Vector2f(100f,100f),new Vector2f(100f,100f)),20);
        startButton.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/start.png"))));
        addGameObjectToScene(startButton);
    }
    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }
    @Override
    public void update(float dt) {
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }
}

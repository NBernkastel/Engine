package Objects;

import Engine.Component;
import Engine.GameObject;
import Engine.Transform;
import components.Animation;
import components.SpriteRenderer;
import components.Spritesheet;
import eventHandlers.KeyListener;
import util.AssetPool;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameObject {
    private Spritesheet spritesheet;
    private boolean full = false;
    private Component anfight;

    public Player(String name, Transform transform, int zIndex) {
        super(name, transform, zIndex);
        spritesheet = AssetPool.getSpritesheet("assets/images/player.png");
        addComponent(new SpriteRenderer(spritesheet.getSprite(0)));
        addComponent(new Animation(0, 13, 0.8f, spritesheet));
        addComponent(new Animation(14, 21, 0.3f, spritesheet));
        addComponent(new Animation(27, 36, 0.3f, spritesheet));
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < components.size(); i++) {
            components.get(0).update(dt);
            int j = KeyListener.get().getKeyPressed;
            if (full)
                j = GLFW_KEY_F;
            switch (i) {
                case GLFW_KEY_D:
                    components.get(2).update(dt);
                    transform.position.x -= 30 * dt;
                    break;
                case GLFW_KEY_F:
                    components.get(3).update(dt);
                    full = true;
                    break;
                default:
                    components.get(1).update(dt);
                    break;

            }
        }
    }
}

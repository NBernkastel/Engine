package Objects;

import Engine.GameObject;
import Engine.Transform;
import org.joml.Vector2f;

public class Button extends GameObject {
    private Vector2f baseScale;
    private Vector2f basePosition;
    public Button(String name) {
        super(name);
    }
    public Button(String name, Transform transform, int zIndex){
        super(name,transform,zIndex);
        this.baseScale = transform.scale;
        this.basePosition = transform.position;
    }
    public Vector2f getBaseScale() {
        return baseScale;
    }
    public Vector2f getBasePosition() {
        return basePosition;
    }
}

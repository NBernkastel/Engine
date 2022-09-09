package Objects;

import Engine.GameObject;
import Engine.Transform;
import components.Spritesheet;
import util.AssetPool;

public class Player extends GameObject {
    public Player(String name, Transform transform, int zIndex) {
        super(name, transform, zIndex);
    }
}

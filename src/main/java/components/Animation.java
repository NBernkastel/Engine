package components;

import Engine.Component;

public class Animation extends Component {
    protected int start;
    protected int end;
    public int spriteIndex;
    protected float spriteFlipTime;
    protected float spriteFlipTimeLeft = 0.0f;
    private Spritesheet spritesheet;

    public Animation(int start, int end, float speed, Spritesheet spritesheet) {
        this.end = end;
        this.spriteFlipTime = speed;
        this.start = start;
        this.spriteIndex = start;
        this.spritesheet = spritesheet;
    }

    @Override
    public void start() {
        gameObject.getComponent(SpriteRenderer.class).setSprite(spritesheet.getSprite(start));
    }

    @Override
    public void update(float dt) {
        spriteFlipTimeLeft += dt;
        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex++;
            if (spriteIndex >= end) {
                spriteIndex = start;
            }
            gameObject.getComponent(SpriteRenderer.class).setSprite(spritesheet.getSprite(this.spriteIndex));
        }
    }
}

package com.mygdx.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


public class Door {
    public Sprite openSprite;
    public Sprite closedSprite;
    public boolean isOpen;
    public boolean isGoat;
    public Vector2 position;
    public float width;
    public float height;

    public Door() {
        this.isOpen = false;
        this.isGoat = false;
        this.position = new Vector2();
    }

    public void render(SpriteBatch batch) {
        if (isOpen) {
            openSprite.draw(batch);
        } else {
            closedSprite.draw(batch);
        }
    }
}

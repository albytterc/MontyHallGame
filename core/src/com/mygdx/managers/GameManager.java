package com.mygdx.managers;


import com.mygdx.gameobjects.Door;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;


public class GameManager {
    private static final float DOOR_RESIZE_FACTOR = 2500f;
    private static final float DOOR_VERT_POSITION_FACTOR = 5f;
    private static final float RESTART_RESIZE_FACTOR = 5500f;

    static Vector3 temp = new Vector3();
    static float width, height;
    static Array<Door> doors;
    static Texture doorTexture;
    static Texture carTexture;
    static Texture goatTexture;
    static Texture restartTexture;
    static Texture backgroundTexture;

    static Sprite restartSprite;
    static Sprite backgroundSprite;

    static boolean hasWon = false;
    static boolean hasRestartedGame = false;
    static IntArray goatIndices;

    public enum Level {
        START, CONFIRM, END, RESTART
    }

    static Level level;

    public static void initialize(float width, float height) {
        GameManager.width = width;
        GameManager.height = height;
        doorTexture = new Texture("data/door_close.png");
        carTexture = new Texture("data/door_open_car.png");
        goatTexture = new Texture("data/door_open_goat.png");
        initDoors();

        goatIndices = new IntArray();
        level = Level.START;

        restartTexture = new Texture("data/Restart.png");
        restartSprite = new Sprite(restartTexture);
        restartSprite.setSize(restartSprite.getWidth() * (width / RESTART_RESIZE_FACTOR), restartSprite.getHeight() * (width / RESTART_RESIZE_FACTOR));

        backgroundTexture = new Texture("data/background.jpg");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(width, height);

    }

    public static void renderGame(SpriteBatch batch) {
        backgroundSprite.draw(batch);
        for (Door door : doors) {
            door.render(batch);
        }
        restartSprite.draw(batch);
    }

    public static void restartGame() {
        // shuffle positions of doors
        doors.shuffle();

        float doorY = height / DOOR_VERT_POSITION_FACTOR;
        float middleDoorX = width / 2 - (doors.get(1).width / 2);
        doors.get(0).position.set(middleDoorX - doors.get(0).width - 50, doorY);
        doors.get(1).position.set(middleDoorX, doorY);
        doors.get(2).position.set(middleDoorX + doors.get(2).width + 50, doorY);

        for (Door door : doors) {
            door.isOpen = false;

            door.closedSprite.setPosition(door.position.x, door.position.y);
            door.openSprite.setPosition(door.position.x, door.position.y);
        }

        GameManager.hasRestartedGame = true;
        GameManager.hasWon = false;
        GameManager.level = Level.RESTART;

    }

    public static void dispose() {
        doorTexture.dispose();
        carTexture.dispose();
        goatTexture.dispose();
        restartTexture.dispose();
        backgroundTexture.dispose();
    }

    public static void initDoors() {
        doors = new Array<>();

        for (int i = 0; i < 3; i++) {
            doors.add(new Door());
        }


        for (Door door : doors) {
            door.closedSprite = new Sprite(doorTexture);
            door.openSprite = new Sprite();

            door.width = door.closedSprite.getWidth() * (width / DOOR_RESIZE_FACTOR);
            door.height = door.closedSprite.getHeight() * (width / DOOR_RESIZE_FACTOR);

            door.closedSprite.setSize(door.width, door.height);

            door.openSprite.setSize(door.width, door.height);
        }

        // float doorX = width / 2 - (doors.get(1).closedSprite.getWidth() / 2);
        float doorY = height / DOOR_VERT_POSITION_FACTOR;
        float middleDoorX = width / 2 - (doors.get(1).width / 2);
        doors.get(0).position.set(middleDoorX - doors.get(0).width - 50, doorY);
        doors.get(1).position.set(middleDoorX, doorY);
        doors.get(2).position.set(middleDoorX + doors.get(2).width + 50, doorY);

        for (Door door : doors) {
            door.closedSprite.setPosition(door.position.x, door.position.y);
            door.openSprite.setPosition(door.position.x, door.position.y);
        }

        doors.get(0).openSprite.setRegion(goatTexture);
        doors.get(0).isGoat = true;
        doors.get(1).openSprite.setRegion(carTexture);
        doors.get(1).isGoat = false;
        doors.get(2).openSprite.setRegion(goatTexture);
        doors.get(2).isGoat = true;
    }

    public static IntArray getGoatIndices(int index) {
        goatIndices.clear();

        for (int i = 0; i < doors.size; i++) {
            if (i != index && doors.get(i).isGoat) {
                goatIndices.add(i);
            }
        }

        return goatIndices;
    }
}

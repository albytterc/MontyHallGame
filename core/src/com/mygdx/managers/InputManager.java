package com.mygdx.managers;

import com.mygdx.gameobjects.Door;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class InputManager {
    public static void handleInput(OrthographicCamera camera) {
        if (Gdx.input.justTouched()) {
            System.out.println("Input touched");
            GameManager.temp.set((float) Gdx.input.getX(), (float) Gdx.input.getY(), 0f);
            camera.unproject(GameManager.temp);
            final float touchX = GameManager.temp.x;
            final float touchY = GameManager.temp.y;
            for (int i = 0; i < GameManager.doors.size; i++) {
                Door door = GameManager.doors.get(i);

                if (!door.isOpen && handleDoor(door, touchX, touchY, i)) { break; }
            }

            handleRestart(touchX, touchY);
        }
    }

    public static void handleRestart(float touchX, float touchY) {
        boolean inBoundsX = touchX >= GameManager.restartSprite.getX() && touchX <= GameManager.restartSprite.getX() + GameManager.restartSprite.getWidth();
        boolean inBoundsY = touchY >= GameManager.restartSprite.getY() && touchY <= GameManager.restartSprite.getY() + GameManager.restartSprite.getHeight();
        if (inBoundsX && inBoundsY) {
            GameManager.restartGame();
        }
    }

    // what happens when you select a door
    public static boolean handleDoor(Door door, float touchX, float touchY, int index) {
        boolean inBoundsX = touchX >= door.position.x && touchX <= door.position.x + door.width;
        boolean inBoundsY = touchY >= door.position.y && touchY <= door.position.y + door.height;
        if (inBoundsX && inBoundsY) {
            System.out.println("Touching door " + (index + 1));
            switch (GameManager.level) {
                case START:
                    Door randomDoor = GameManager.doors.get(GameManager.getGoatIndices(index).random());
                    // System.out.println("Opening door " + randomDoor.);
                    randomDoor.isOpen = true;
                    GameManager.level = GameManager.Level.CONFIRM;
                    TextManager.setSelectedDoor(index);
                    break;
                case CONFIRM:
                    door.isOpen = true;
                    GameManager.level = GameManager.Level.END;
                    if (!door.isGoat) {
                        GameManager.hasWon = true;
                    }
                    break;

            }
            return true;
        }
        return false;
    }
}

package com.mygdx.managers;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.w3c.dom.Text;

public class TextManager {
    static GlyphLayout glyphLayout;
    static BitmapFont font;
    static String confirm;
    static String restart;
    static String start;
    static String win;
    static String lose;
    static float width;
    static float height;

    static {
        TextManager.restart = "Restarting game...";
        TextManager.start = "Select a door";
        TextManager.win = "You win!";
        TextManager.lose = "You lose";
    }

    public static void initialize(float width, float height) {
        TextManager.width = width;
        TextManager.height = height;
        TextManager.font = new BitmapFont(new FileHandle("assets/arial32.fnt"));
        // TextManager.font.getData().setScale(0.5f);
        glyphLayout = new GlyphLayout();
        // TextManager.font.setColor(Color.BLACK);
    }

    public static void renderText(SpriteBatch batch) {
        float textPosX = TextManager.width / 2f;
        float textPosY = TextManager.height - 190;

        TextManager.font.getData().setScale(1f);
        glyphLayout.setText(TextManager.font, "Welcome to Monty Hall!");
        TextManager.font.draw(batch, glyphLayout, textPosX - (glyphLayout.width / 2f), TextManager.height - 100);
        TextManager.font.getData().setScale(0.75f);

        switch (GameManager.level) {
            case RESTART:
                glyphLayout.setText(TextManager.font, TextManager.restart);
                TextManager.font.draw(batch, glyphLayout, textPosX - (glyphLayout.width / 2f), textPosY);
                GameManager.level = GameManager.Level.START;
                break;
            case START:
                if (GameManager.hasRestartedGame) {
                    GameManager.hasRestartedGame = false;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                glyphLayout.setText(TextManager.font, TextManager.start);
                TextManager.font.draw(batch, glyphLayout, textPosX - (glyphLayout.width / 2f), textPosY);
                break;
            case CONFIRM:
                glyphLayout.setText(TextManager.font, TextManager.confirm);
                TextManager.font.draw(batch, glyphLayout, textPosX - (glyphLayout.width / 2f), textPosY);
                break;
            case END:
                if (GameManager.hasWon) {
                    glyphLayout.setText(TextManager.font, TextManager.win);
                    TextManager.font.draw(batch, glyphLayout, textPosX - (glyphLayout.width / 2f), textPosY);
                } else {
                    glyphLayout.setText(TextManager.font, TextManager.lose);
                    TextManager.font.draw(batch, glyphLayout, textPosX - (glyphLayout.width / 2f), textPosY);
                }
                break;
        }
    }

    public static void setSelectedDoor(int doorIndex) {
        TextManager.confirm = "You selected door #" + (doorIndex + 1) + ". Do you want to switch or stay?";
        // = new StringBuffer("You selected door #. Do you want to switch or stay?");
        // TextManager.confirm.insert(TextManager.confirm.indexOf("door #") + "door #".length(), " " + (doorIndex + 1));
    }


}

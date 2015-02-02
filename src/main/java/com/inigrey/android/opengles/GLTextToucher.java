package com.inigrey.android.opengles;

import android.content.Context;
import com.inigrey.android.opengles.gltext.GLText;

import javax.microedition.khronos.opengles.GL10;

/**
 * Класс для изображения и управления текста на экране
 */
public class GLTextToucher {

    /**
     * Объект для изображения текста
     */
    private static GLText glText;

    /**
     * ширина текста
     */
    private float width;

    /**
     * высота текста
     */
    private float height;

    /**
     * размер текста
     */
    private static int size = 36;

    /**
     * выводимый текст
     */
    private String text;

    /**
     * цветовая гамма
     */
    private float red;
    private float green;
    private float blue;
    private float alpha = 1.0f;

    /**
     * координаты нижнего левого угла текста
     */
    private float startX;
    private float startY;

    /**
     * Метод для инициализации обекта GLText
     *
     * @param gl      обьект позволяюший давать команды OpenGL ES
     * @param context
     * @param size    размер текста
     */
    public static void initGLText(GL10 gl, Context context, int size) {
        glText = new GLText(gl, context.getAssets());
        setSize(size);
    }

    public int getSize() {
        return size;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getWidth() {
        return width;
    }

    public String getText() {
        return text;
    }

    public float getHeight() {
        return height;
    }

    /**
     * Изменяе размер шрифта, для этого нам приходиться перегружать
     * файл с описанием шрифта
     *
     * @param size размер шрифта
     */
    public static void setSize(int size) {
        GLTextToucher.size = size;
        glText.load("Roboto-Regular.ttf", size, 2, 2);
    }

    /**
     * меняем положение текста
     *
     * @param x
     * @param y
     */
    public void setPosition(float x, float y) {
        startX = x;
        startY = y;
    }

    public void setText(String text) {
        this.text = text;
        width = glText.width(text);
        height = glText.getCharHeight();
    }

    public void setColor(float r, float g, float b, float a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }

    public void draw(String text, int size, float x, float y) {
        setSize(size);
        setPosition(x, y);
        this.text = text;
        glText.begin(red, green, blue, alpha);
        width = glText.draw(text, x, y);
        height = glText.getCharHeight();
        glText.end();
    }

    public void draw(String text, float x, float y) {
        this.text = text;
        setPosition(x, y);
        glText.begin(red, green, blue, alpha);
        width = glText.draw(text, x, y);
        height = glText.getCharHeight();
        glText.end();
    }

    public void draw(float x, float y) {
        setPosition(x, y);
        glText.begin(red, green, blue, alpha);
        width = glText.draw(text, x, y);
        height = glText.getCharHeight();
        glText.end();
    }

    public void draw() {
        glText.begin(red, green, blue, alpha);
        width = glText.draw(text, startX, startY);
        height = glText.getCharHeight();
        glText.end();
    }

}

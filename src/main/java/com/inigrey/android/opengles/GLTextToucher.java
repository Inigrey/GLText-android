package com.inigrey.android.opengles;

import android.content.Context;
import com.inigrey.android.opengles.gltext.GLText;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by sergey on 01.02.15.
 */
public class GLTextToucher {
    private GLText glText;
    private float width;
    private float height;
    private int size=20;
    private String text;
    private float red;
    private float green;
    private float blue;
    private float alpha=1.0f;
    private float startX;
    private float startY;
    public GLTextToucher(GL10 gl, Context context) {
        glText = new GLText(gl, context.getAssets());
        setSize(size);
    }
    public int getSize(){
        return size;
    }
    public float getStartX(){
        return startX;
    }
    public float getStartY(){
        return startY;
    }
    public float getWidth(){
        return width;
    }
    public String getText(){
        return text;
    }
    public float getHeight(){
        return height;
    }
    public void setSize(int size){
        this.size = size;
        glText.load( "Roboto-Regular.ttf", size, 2, 2 );
    }
    public void setPosition(float x, float y){
        startX = x;
        startY = y;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setColor(float r, float g, float b, float a){
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }
    public void draw(String text, int size, float x, float y){
        setSize(size);
        setPosition(x,y);
        this.text = text;
        glText.begin( red, green, blue, alpha );
        width = glText.draw(text, x, y);
        height = glText.getCharHeight();
        glText.end();
    }
    public void draw(String text, float x, float y){
        this.text = text;
        setPosition(x,y);
        glText.begin( red, green, blue, alpha );
        width = glText.draw(text, x, y);
        height = glText.getCharHeight();
        glText.end();
    }
    public void draw(float x, float y){
        setPosition(x,y);
        glText.begin( red, green, blue, alpha );
        width = glText.draw(text, x, y);
        height = glText.getCharHeight();
        glText.end();
    }
    public void draw(){
        glText.begin( red, green, blue, alpha );
        width = glText.draw(text, startX, startY);
        height = glText.getCharHeight();
        glText.end();
    }

}

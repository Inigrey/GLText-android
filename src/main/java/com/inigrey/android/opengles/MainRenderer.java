package com.inigrey.android.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainRenderer implements GLSurfaceView.Renderer {

    private static final String LOG_TAG = "MainRenderer";

    /**
     * лист для разбиения текста на слова
     * и отрисовки их
     */
    private List<GLTextToucher> glTexts;                             // A GLText Instance
    private Context context;                           // Context (from Activity)
    private int width, height;
    private int index;
    private int heightText;
    private boolean finish = false;

    public MainRenderer(Context context) {
        this.context = context;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glTexts = Collections.synchronizedList(new LinkedList<GLTextToucher>());

        //инициализируем GLText для отображения текста
        GLTextToucher.initGLText(gl, context, 36);
        initText();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // Set to ModelView mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);           // Activate Model View Matrix
        gl.glLoadIdentity();                            // Load Identity Matrix

        // enable texture + alpha blending
        // NOTE: this is required for text rendering! we could incorporate it into
        // the GLText class, but then it would be called multiple times (which impacts performance).
        gl.glEnable(GL10.GL_TEXTURE_2D);              // Enable Texture Mapping
        gl.glEnable(GL10.GL_BLEND);                   // Enable Alpha Blend
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);  // Set Alpha Blend Function

        // TEST: render the entire font texture
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);         // Set Color to Use
        //glText.drawTexture( width, height );            // Draw the Entire Texture
        if (!finish) {
            synchronized (glTexts) {
                for (GLTextToucher text : glTexts) {
                    text.draw();
                }
            }
        } else {
            GLTextToucher glText = new GLTextToucher();
            glText.setColor(0.0f, 1.0f, 0.0f, 1.0f);
            glText.setText("ARIGHT!");
            glText.draw((width - glText.getWidth()) / 2.0f, (height + heightText) / 2.0f);
        }
        // disable texture + alpha
        gl.glDisable(GL10.GL_BLEND);                  // Disable Alpha Blend
        gl.glDisable(GL10.GL_TEXTURE_2D);             // Disable Texture Mapping
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        // Setup orthographic projection
        gl.glMatrixMode(GL10.GL_PROJECTION);          // Activate Projection Matrix
        gl.glLoadIdentity();                            // Load Identity Matrix
        gl.glOrthof(                                    // Set Ortho Projection (Left,Right,Bottom,Top,Front,Back)
                0, width,
                0, height,
                1.0f, -1.0f
        );
        this.width = width;
        this.height = height;
        repositions();
    }

    public List<GLTextToucher> getGlTexts() {
        return glTexts;
    }

    /**
     * Берем текст из листа в главной активности и разбиваем
     * его на куски между пробелами, заполняем ими наш лист
     * с обектами для рисования слов.
     */
    public void initText() {
        glTexts.clear();
        String text = MainActivity.getTexts()[index];
        String[] words = text.split(" ");
        Arrays.sort(words);
        GLTextToucher glText = new GLTextToucher();
        float red = 0f;
        float green = 1.0f;
        float blue = 0f;
        glText.setColor(red, green, blue, 1.0f);
        glText.setText(words[0]);
        heightText = (int) glText.getHeight();
        glTexts.add(glText);
        for (int i = 1; i < words.length; i++) {
            glText = new GLTextToucher();
            red += 0.03f;
            green -= 0.05f;
            blue += 0.03f;
            glText.setColor(red, green, blue, 1.0f);
            glText.setText(words[i]);
            glTexts.add(glText);
        }
    }

    /**
     * Метод переопределяет позиции слов на изображении
     * и проверяет совпадает ли текст с образцом
     */
    public void repositions() {
        String text = "";
        int startX = 0;
        int startY = height - heightText;
        for (GLTextToucher glText : glTexts) {
            int widthWord = (int) glText.getWidth();
            if (startX + widthWord > width) {
                startX = 0;
                startY -= heightText;
            }
            glText.setPosition(startX, startY);
            startX += widthWord;
            text += " " + glText.getText();
        }
        text = text.substring(1);
        if (text.equals(MainActivity.getTexts()[index]))
            finish = true;
    }

    /**
     * Метод для проверки правильности состава приложения
     *
     * @return
     */
    public boolean getFinish() {
        return finish;
    }

    /**
     * Меняем текст для отображения
     */
    public void refinish() {
        finish = !finish;
        index = (index + 1) % MainActivity.getTexts().length;
        initText();
        repositions();
    }
}

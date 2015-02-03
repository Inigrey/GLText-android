package com.inigrey.android.opengles;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String LOG_TAG = "GLTextMainActivity";

    /**
     * Список строк с файла ресурса
     */
    private static List<String> texts;

    /**
     * Объект заполнения экрана приложения
     */
    private GLSurfaceView glView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout layout= (FrameLayout) findViewById(R.id.glView);
        glView = new MainSurfaceView(this);
        layout.addView(glView);
        Handler handler = new Handler();
        handler.post(runnable);

    }

    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    /**
     * Читаем строки с файла "in.txt" и заполняем
     * List texts
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                InputStreamReader iStream = new InputStreamReader(getAssets().open("in.txt"));
                BufferedReader in = new BufferedReader(iStream);
                String text;
                texts = new ArrayList<String>();
                while ((text = in.readLine()) != null) {
                    texts.add(text);
                }
                in.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }

        }
    };

    /**
     * Метод передает массив строк с файла другим классам
     *
     * @return массив строк
     */
    public static String[] getTexts() {
        return texts.toArray(new String[texts.size()]);
    }
}
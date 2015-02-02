package com.inigrey.android.opengles;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

public class MainSurfaceView extends GLSurfaceView {

    public static final String LOG_TAG = "BoxDrawingView";
    public MainRenderer renderer;
    private GLTextToucher glText;
    float dx, dy;

    public MainSurfaceView(Context context) {
        super(context);
        renderer = new MainRenderer(context);
        setRenderer(renderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF curr = new PointF(event.getX(), event.getY());
        int x = (int) curr.x, y = renderer.getHeight() - (int) curr.y;
        Log.i(LOG_TAG, "Received event at x=" + curr.x +
                ", y=" + curr.y + ":");
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                Log.i(LOG_TAG, " ACTION_DOWN");
                if (!renderer.getFinish()) {
                    if (renderer.getGlTexts() != null)
                        for (GLTextToucher text : renderer.getGlTexts()) {
                            if (x >= text.getStartX() && x <= text.getStartX() + text.getWidth()) {
                                if (y >= text.getStartY() && y < text.getStartY() + text.getHeight()) {
                                    glText = text;
                                    dx = x - text.getStartX();
                                    dy = y - text.getStartY();
                                    renderer.getGlTexts().remove(text);
                                    renderer.getGlTexts().add(glText);
                                    Log.i(LOG_TAG, "dx=" + dx + " dy=" + dy);
                                    break;
                                }
                            }
                        }
                } else {
                    renderer.refinish();
                }

                break;

            case MotionEvent.ACTION_MOVE:
                Log.i(LOG_TAG, " ACTION_MOVE");
                if (glText != null) {
                    glText.setPosition(x - dx, y - dy);
                    Log.i(LOG_TAG, "dx=" + dx + " dy=" + dy);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (glText != null) {
                    glText = null;
                    renderer.repositions();
                }
                Log.i(LOG_TAG, " ACTION_UP");
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.i(LOG_TAG, " ACTION_CANCEL");

                break;

        }

        return true;

    }
}

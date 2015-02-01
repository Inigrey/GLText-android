package com.inigrey.android.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 01.02.15.
 */
public class MainRenderer implements GLSurfaceView.Renderer  {

    private List<GLTextToucher> glTexts;                             // A GLText Instance
    private Context context;                           // Context (from Activity)
    private int width,height;

    public MainRenderer(Context context)  {
        this.context = context;                         // Save Specified Context

    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        gl.glClearColor( 0.5f, 0.5f, 0.5f, 1.0f );
        glTexts = new ArrayList<GLTextToucher>();
        GLTextToucher glText = new GLTextToucher( gl, context );
        glText.setSize(24);
        glText.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        glText.setPosition(0, 0);
        glText.setText("Test String :)");
        glTexts.add(glText);

        glText = new GLTextToucher( gl, context );
        glText.setSize(24);
        glText.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        glText.setPosition(50, 50);
        glText.setText("Line 1");
        glTexts.add(glText);

        glText = new GLTextToucher( gl, context );
        glText.setSize(24);
        glText.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        glText.setPosition(100, 100);
        glText.setText("Line 2");
        glTexts.add(glText);

        glText = new GLTextToucher( gl, context );
        glText.setSize(24);
        glText.setColor(0.0f, 0.0f, 1.0f, 1.0f);
        glText.setPosition(200, 150);
        glText.setText("More Lines...");
        glTexts.add(glText);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        gl.glClear( GL10.GL_COLOR_BUFFER_BIT );

        // Set to ModelView mode
        gl.glMatrixMode( GL10.GL_MODELVIEW );           // Activate Model View Matrix
        gl.glLoadIdentity();                            // Load Identity Matrix

        // enable texture + alpha blending
        // NOTE: this is required for text rendering! we could incorporate it into
        // the GLText class, but then it would be called multiple times (which impacts performance).
        gl.glEnable( GL10.GL_TEXTURE_2D );              // Enable Texture Mapping
        gl.glEnable( GL10.GL_BLEND );                   // Enable Alpha Blend
        gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );  // Set Alpha Blend Function

        // TEST: render the entire font texture
        gl.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );         // Set Color to Use
        //glText.drawTexture( width, height );            // Draw the Entire Texture
        for (GLTextToucher text: glTexts){
            text.draw();
        }

        // disable texture + alpha
        gl.glDisable( GL10.GL_BLEND );                  // Disable Alpha Blend
        gl.glDisable( GL10.GL_TEXTURE_2D );             // Disable Texture Mapping
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport( 0, 0, width, height );
        // Setup orthographic projection
        gl.glMatrixMode( GL10.GL_PROJECTION );          // Activate Projection Matrix
        gl.glLoadIdentity();                            // Load Identity Matrix
        gl.glOrthof(                                    // Set Ortho Projection (Left,Right,Bottom,Top,Front,Back)
                0, width,
                0, height,
                1.0f, -1.0f
        );
        this.width=width;
        this.height=height;
    }
    public List<GLTextToucher> getGlTexts(){
        return glTexts;
    }
}

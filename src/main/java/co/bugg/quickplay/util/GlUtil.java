package co.bugg.quickplay.util;

import static org.lwjgl.opengl.GL11.glColor3f;

/**
 * OpenGL Utilities
 */
public final class GlUtil {

    private GlUtil() { throw new AssertionError(); }

    /**
     * Reset
     */
    public static void resetGlColor() {
        glColor3f(1, 1, 1);
    }
}

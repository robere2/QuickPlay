package co.bugg.quickplay.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Class for reflection (getting class values at runtime)
 */
public final class ReflectUtil {

    private ReflectUtil() { throw new AssertionError(); }

    /**
     * Get the field of a class reflectively at runtime.
     * @param theClass The class to get the field from
     * @param fieldName The field to get
     * @return Field that was requested
     */
    public static Field getField(Class<?> theClass, String fieldName) {
        // TODO: This should not return null unless it is truly null.
        try {
            Field field = theClass.getField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the method of a class reflectively at runtime.
     * @param theClass The class to get the field from
     * @param methodName The method to get
     * @return Method that was requested
     */
    public static Method getMethod(Class<?> theClass, String methodName, Class<?>... args) {
        // TODO: This should not return null unless it is truly null.
        try {
            Method method = theClass.getMethod(methodName, args);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}

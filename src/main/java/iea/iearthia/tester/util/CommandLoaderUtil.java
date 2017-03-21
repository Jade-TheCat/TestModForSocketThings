package iea.iearthia.tester.util;


import iea.iearthia.tester.annotation.CommandLoader;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandLoaderUtil {
    public static void loadCommands(Class clazz, FMLServerStartingEvent event) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(CommandLoader.class)) {
                for (String command : m.getAnnotation(CommandLoader.class).commands()) {
                    System.out.println(command);
                }

                m.invoke(clazz.newInstance(), event);
            }
        }
    }
}

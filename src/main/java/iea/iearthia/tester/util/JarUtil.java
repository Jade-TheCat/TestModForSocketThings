package iea.iearthia.tester.util;

import iea.iearthia.tester.Tester;
import iea.iearthia.tester.annotation.Plugin;
import iea.iearthia.testplugin.plugin;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtil {

    public void findAllPlugins() throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        boolean isDevEnv = false;
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            if (envName.equals("isDevEnv")) {
                if (env.get("isDevEnv").equals("true")) {
                    isDevEnv = true;
                }
            }
        }
        if (isDevEnv) {
            System.out.println("Dev environment, loading test plugin!");
            for (Annotation annotation : plugin.class.getAnnotations()) {
                Class<? extends Annotation> type = annotation.annotationType();
                System.out.println("Values of " + type.getName());

                for (Method method : type.getDeclaredMethods()) {
                    Object value = method.invoke(annotation, (Object[])null);
                    System.out.println(" " + method.getName() + ": " + value);
                }
            }
        }

        for(File F : (new File(Tester.PLUGINDIR)).listFiles()) {
            if (F.toString().endsWith(".jar")) {
                Class c = findPluginClass(loadJar(F.getAbsolutePath()));
                if (c != null) {
                    for (Annotation annotation : c.getAnnotations()) {
                        Class<? extends Annotation> type = annotation.annotationType();
                        System.out.println("Values of " + type.getName());

                        for (Method method : type.getDeclaredMethods()) {
                            Object value = method.invoke(annotation, (Object[])null);
                            System.out.println(" " + method.getName() + ": " + value);
                        }
                    }
                } else {
                    System.out.println("Jar " + F.getName() + " does not contain a plugin!");
                }
            } else {
                System.out.println("File " + F.getName() + " is not a JAR!");
            }
        }
    }


    public Class findPluginClass(ArrayList<Class> classes) {
        Class actualClass = null;
        for (Class c : classes) {
            System.out.println(c.getName());
            for (Annotation a : c.getAnnotations()) {
                System.out.println(a.annotationType().getName());
                if (a.annotationType().getName().endsWith("Plugin")) {
                    actualClass = c;
                }
            }

        }
        return actualClass;
    }


    public ArrayList<Class> loadJar(String path) throws IOException, ClassNotFoundException {
        ArrayList<Class> classArrayList = new ArrayList<Class>();

        JarFile jarFile = new JarFile(path);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + path + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            classArrayList.add(c);
            System.out.println(c.getName());
        }
        return classArrayList;
    }
}

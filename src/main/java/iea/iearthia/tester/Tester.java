package iea.iearthia.tester;

import iea.iearthia.tester.util.CommandLoaderUtil;
import iea.iearthia.tester.util.JarUtil;
import iea.iearthia.testplugin.plugin;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Mod(modid = Tester.MODID, version = Tester.VERSION)
public class Tester {
    public static final String MODID = "testermod";
    public static final String VERSION = "0.0.1";
    public static final String PLUGINDIR = (Minecraft.getMinecraft().mcDataDir.getAbsolutePath().substring(0, Minecraft.getMinecraft().mcDataDir.getAbsolutePath().length() - 1) + "plugins\\");

    @EventHandler
    public void onConstruct(FMLConstructionEvent event) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        System.out.println(PLUGINDIR);
        JarUtil util = new JarUtil();
        util.findAllPlugins();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
    @EventHandler
    public void serverStarting(FMLServerStartingEvent e) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        CommandLoaderUtil.loadCommands(plugin.class, e);
    }
}

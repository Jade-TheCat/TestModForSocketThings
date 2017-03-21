package iea.iearthia.testplugin;

import iea.iearthia.tester.annotation.CommandLoader;
import iea.iearthia.tester.annotation.Plugin;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Plugin(name = "test", version = "0.0.1", author = "Jade")
public class plugin {
    @CommandLoader(commands = {"test"})
    public static void loadCommands(FMLServerStartingEvent e) {
        e.registerServerCommand(testCommand);
    }

    public static ICommand testCommand = new ICommand() {
        @Override
        public String getCommandName() {
            return "test";
        }

		
        @Override
        public String getCommandUsage(ICommandSender sender) {
            return null;
        }

        @Override
        public List<String> getCommandAliases() {
            List<String> s = new ArrayList<String>();
            s.add("test");
            return s;
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            sender.addChatMessage(new TextComponentString("Test"));
        }

        @Override
        public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
            return true;
        }

        @Override
        public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
            return null;
        }

        @Override
        public boolean isUsernameIndex(String[] args, int index) {
            return false;
        }

        @Override
        public int compareTo(ICommand o) {
            return 0;
        }
    };


}

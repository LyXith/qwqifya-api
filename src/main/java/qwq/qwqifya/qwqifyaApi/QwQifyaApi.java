package qwq.qwqifya.qwqifyaApi;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import qwq.qwqifya.qwqifyaApi.commandUtils.CommandManager;
import qwq.qwqifya.qwqifyaApi.commandUtils.TestCommand;

public class QwQifyaApi implements ModInitializer {
    public static CommandDispatcher<CommandSourceStack> serverDispatcher;
    @Override
    public void onInitialize() {
        new TestCommand();
        CommandManager.scanClassesAndRegister();
        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> {
            CommandManager.registerServerAll(dispatcher);
            serverDispatcher = dispatcher;
        });
    }
}

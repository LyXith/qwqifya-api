package qwq.qwqifya.qwqifyaApi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import qwq.qwqifya.qwqifyaApi.commandUtils.CommandManager;
import qwq.qwqifya.qwqifyaApi.commandUtils.TestCommand;


public class QwQifyaApiClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new TestCommand();
        CommandManager.scanClassesAndRegister();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            CommandManager.registerClientAll(dispatcher);
        });
    }
}

package qwq.qwqifya.qwqifyaApi.commandUtils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandSourceStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static qwq.qwqifya.qwqifyaApi.QwQifyaApi.serverDispatcher;
import static qwq.qwqifya.qwqifyaApi.commandUtils.BaseCommand.scanClasses;

public class CommandManager {
    private static final List<LiteralCommandNode<CommandSourceStack>> serverCommands = new CopyOnWriteArrayList<>();
    private static final List<CommandNode<FabricClientCommandSource>> clientCommands = new CopyOnWriteArrayList<>();

    public static void registerServerAll(CommandDispatcher<CommandSourceStack> dispatcher) {
        for (LiteralCommandNode<CommandSourceStack> command : serverCommands) {
            dispatcher.getRoot().addChild(command);
        }
    }

    public static void registerClientAll(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        for (CommandNode<FabricClientCommandSource> command : clientCommands) {
            dispatcher.getRoot().addChild(command);
        }
    }

    @SuppressWarnings("unchecked")
    public static void scanClassesAndRegister() {
        for (BaseCommand clazz : scanClasses) {
            Field[] fields = clazz.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(RegisterCommand.class)) {
                    if (!LiteralCommandNode.class.isAssignableFrom(field.getType())) { continue; }
                    try {
                        field.setAccessible(true);
                        Object commandNode = field.get(clazz);
                        if (commandNode == null) { continue; }

                        if (commandNode instanceof LiteralCommandNode) {
                            serverCommands.add((LiteralCommandNode<CommandSourceStack>) commandNode);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else if (field.isAnnotationPresent(RegisterClientCommand.class)) {
                    if (!CommandNode.class.isAssignableFrom(field.getType())) { continue; }
                    try {
                        field.setAccessible(true);
                        Object commandNode = field.get(clazz);
                        if (commandNode == null) { continue; }
                        clientCommands.add((CommandNode<FabricClientCommandSource>) commandNode);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void refreshCommands() {
        CommandDispatcher<FabricClientCommandSource> clientDispatcher =
                ClientCommandManager.getActiveDispatcher();
        serverCommands.clear();
        clientCommands.clear();
        scanClassesAndRegister();
        if (serverDispatcher == null || clientDispatcher == null) {
            return;
        }
        registerServerAll(serverDispatcher);
        registerClientAll(clientDispatcher);
    }
}

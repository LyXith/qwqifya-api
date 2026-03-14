package qwq.qwqifya.qwqifyaApi.commandUtils;

import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TestCommand extends BaseCommand {

    @RegisterCommand
    public static LiteralCommandNode<CommandSourceStack> testCmd = Commands.literal("test-cmd")
            .executes(c -> {
                c.getSource().sendSystemMessage(Component.literal("test succeed"));
                return 1;
            }).build();

    @RegisterClientCommand
    public static CommandNode<FabricClientCommandSource> testClientCmd = ClientCommandManager.literal("test-client-cmd")
            .executes(c -> {
                c.getSource().sendFeedback(Component.literal("test succeed"));
                return 1;
            }).build();
}

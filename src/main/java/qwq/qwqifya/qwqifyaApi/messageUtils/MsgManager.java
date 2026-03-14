package qwq.qwqifya.qwqifyaApi.messageUtils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class MsgManager {
    private final String name;
    private final Component prefix;
    public MsgManager(String name) {
        this.name = name;
        prefix = Component.literal("["+name+"] ").withStyle(ChatFormatting.AQUA);
    }
    public void sendMsg(Player player, Component msg) {
        player.displayClientMessage(prefix.copy().append(msg), false);
    }
}
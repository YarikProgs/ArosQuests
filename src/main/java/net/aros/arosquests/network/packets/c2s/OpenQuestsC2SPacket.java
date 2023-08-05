package net.aros.arosquests.network.packets.c2s;

import net.aros.arosquests.network.ModMessages;
import net.aros.arosquests.network.packets.s2c.OpenQuestsS2CPacket;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.world.QuestState;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.Collection;

public class OpenQuestsC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Collection<QuestInstance> questData = QuestState.getQuestData(player.getWorld()).values();

        ServerPlayNetworking.send(player, ModMessages.OPEN_QUESTS_S2C, OpenQuestsS2CPacket.toBytes(questData));
    }
}

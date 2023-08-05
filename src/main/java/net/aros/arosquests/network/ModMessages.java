package net.aros.arosquests.network;

import net.aros.arosquests.network.packets.c2s.OpenQuestsC2SPacket;
import net.aros.arosquests.network.packets.s2c.OpenQuestsS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class ModMessages {
    public static final Identifier OPEN_QUESTS_C2S = new Identifier(MOD_ID, "open_quests_c2s");
    public static final Identifier OPEN_QUESTS_S2C = new Identifier(MOD_ID, "open_quests_s2c");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(OPEN_QUESTS_C2S, OpenQuestsC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(OPEN_QUESTS_S2C, OpenQuestsS2CPacket::receive);
    }
}

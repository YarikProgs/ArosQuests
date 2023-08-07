package net.aros.arosquests.network.packets.s2c;

import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.screen.QuestScreen;
import net.aros.arosquests.util.AQRegistry;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.Collection;

public class OpenQuestsS2CPacket {
    public static PacketByteBuf toBytes(Collection<QuestInstance> instances) {
        PacketByteBuf buf = PacketByteBufs.create();
        instances.forEach(instance -> {
            buf.writeIdentifier(instance.getQuest().getId());
            buf.writeInt(instance.getStatus().asInt());
            buf.writeInt(instance.getTime());
        });
        return buf;
    }

    public static Collection<QuestInstance> fromBytes(PacketByteBuf buf) {
        Collection<QuestInstance> collection = new ArrayList<>();

        for (int i = 0; i < AQRegistry.QUEST.size(); i++)
            collection.add(new QuestInstance(Quest.byId(buf.readIdentifier()), QuestStatus.byInt(buf.readInt()), buf.readInt()));

        return collection;
    }

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        QuestScreen screen = QuestScreen.simple();
        screen.addQuests(fromBytes(buf));

        client.execute(() -> client.setScreen(screen));
    }
}

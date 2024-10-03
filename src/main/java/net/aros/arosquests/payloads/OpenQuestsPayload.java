package net.aros.arosquests.payloads;

import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.screen.QuestScreen;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Collection;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public record OpenQuestsPayload(Collection<QuestInstance> quests) implements CustomPayload {
    public static final Id<OpenQuestsPayload> ID = new Id<>(Identifier.of(MOD_ID, "open_quests"));
    public static final PacketCodec<RegistryByteBuf, OpenQuestsPayload> CODEC = PacketCodec.of(OpenQuestsPayload::write, OpenQuestsPayload::read);

    public static OpenQuestsPayload read(RegistryByteBuf buf) {
        return new OpenQuestsPayload(
            buf.readList(buf_ -> new QuestInstance(Quest.byId(buf_.readIdentifier()), QuestStatus.byInt(buf_.readInt()), buf_.readInt()))
        );
    }

    public static void write(OpenQuestsPayload payload, RegistryByteBuf buf) {
        buf.writeCollection(payload.quests, (buf_, inst) -> {
            buf_.writeIdentifier(inst.getQuest().getId());
            buf_.writeInt(inst.getStatus().asInt());
            buf_.writeInt(inst.getTime());
        });
    }

    public static void receive(OpenQuestsPayload payload, IPayloadContext ignored) {
        MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(new QuestScreen(payload.quests)));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

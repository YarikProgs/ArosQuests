package net.aros.arosquests.payloads;

import net.aros.arosquests.world.QuestState;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public record CollectQuestsPayload() implements CustomPayload {
    public static final Id<CollectQuestsPayload> ID = new Id<>(Identifier.of(MOD_ID, "collect_quests"));
    public static final PacketCodec<RegistryByteBuf, CollectQuestsPayload> CODEC = PacketCodec.of((value, buf) -> {
    }, buf -> new CollectQuestsPayload());


    public static void receive(CollectQuestsPayload ignore, @NotNull IPayloadContext context) {
        PacketDistributor.sendToPlayer((ServerPlayerEntity) context.player(),
            new OpenQuestsPayload(QuestState.getQuestData(context.player().getServer()).values())
        );
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

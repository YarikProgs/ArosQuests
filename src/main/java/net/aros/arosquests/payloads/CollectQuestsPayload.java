package net.aros.arosquests.payloads;

import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.world.QuestState;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class CollectQuestsPayload implements CustomPayload {
    public static final CollectQuestsPayload INSTANCE = new CollectQuestsPayload();
    public static final Id<CollectQuestsPayload> ID = new Id<>(Identifier.of(MOD_ID, "collect_quests"));
    public static final PacketCodec<RegistryByteBuf, CollectQuestsPayload> CODEC = new PacketCodec<>() {
        @Override
        public CollectQuestsPayload decode(RegistryByteBuf buf) {
            return INSTANCE;
        }

        @Override
        public void encode(RegistryByteBuf buf, CollectQuestsPayload value) {
        }
    };


    public static void receive(@NotNull ServerPlayerEntity player) {
        Collection<QuestInstance> questData = QuestState.getQuestData(player.getServerWorld()).values();

        ServerPlayNetworking.send(player, new OpenQuestsPayload(questData));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

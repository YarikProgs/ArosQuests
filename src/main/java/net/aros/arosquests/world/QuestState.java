package net.aros.arosquests.world;

import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.util.AQRegistry;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class QuestState extends PersistentState {
    private final Map<Quest, QuestInstance> QUEST_DATA = genQuests();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        NbtCompound questData = new NbtCompound();
        for (Quest quest : QUEST_DATA.keySet()) {
            QuestInstance instance = QUEST_DATA.get(quest);

            NbtCompound questInstanceData = new NbtCompound();
            questInstanceData.putInt("Status", instance.getStatus().asInt());
            questInstanceData.putInt("Time", instance.getTime());

            questData.put(quest.getId().toString(), questInstanceData);
        }
        nbt.put("QuestData", questData);
        return nbt;
    }

    private static QuestState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        QuestState state = new QuestState();

        // {"QuestData": {"arosquest:example": {"Status": 1, "Time": 100}, ...}}

        NbtCompound questData = nbt.getCompound("QuestData");
        for (String questN : questData.getKeys()) {
            NbtCompound questInstanceData = questData.getCompound(questN);
            Quest quest = Quest.byId(Identifier.of(questN));

            state.QUEST_DATA.put(quest, new QuestInstance(quest, QuestStatus.byInt(questInstanceData.getInt("Status")), questInstanceData.getInt("Time")));
        }
        return state;
    }

    private static QuestState getQuestState(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(
            new Type<>(QuestState::new, QuestState::fromNbt, DataFixTypes.LEVEL),
            MOD_ID
        );
    }

    private static Map<Quest, QuestInstance> genQuests() {
        Map<Quest, QuestInstance> quests = new HashMap<>();
        AQRegistry.QUEST.forEach(quest -> quests.put(quest, new QuestInstance(quest, QuestStatus.NOT_STARTED)));
        return quests;
    }

    public static Map<Quest, QuestInstance> getQuestData(ServerWorld world) {
        return new HashMap<>(getQuestState(world).QUEST_DATA);
    }

    public static QuestInstance getQuestInstance(Quest quest, ServerWorld world) {
        return getQuestState(world).QUEST_DATA.get(quest);
    }

    public static void setQuestInstance(Quest quest, QuestInstance instance, @NotNull MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            var data = getQuestState(world);
            data.QUEST_DATA.replace(quest, instance);
            data.markDirty();
        }
    }

    public static void setQuestStatus(Quest quest, QuestStatus status, @NotNull MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            var data = getQuestState(world);
            data.QUEST_DATA.get(quest).setStatus(status, server);
            data.markDirty();
        }
    }

    public static void setQuestTime(Quest quest, int time, @NotNull MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            var data = getQuestState(world);
            data.QUEST_DATA.get(quest).setTime(time);
            data.markDirty();
        }
    }
}

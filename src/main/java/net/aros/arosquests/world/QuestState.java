package net.aros.arosquests.world;

import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.util.AQRegistry;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.Contract;
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

    private static @NotNull QuestState fromNbt(@NotNull NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
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

    private static QuestState getQuestState(@NotNull MinecraftServer server) {
        return server.getOverworld().getPersistentStateManager().getOrCreate(
            new Type<>(QuestState::new, QuestState::fromNbt, DataFixTypes.LEVEL),
            MOD_ID
        );
    }

    private static @NotNull Map<Quest, QuestInstance> genQuests() {
        Map<Quest, QuestInstance> quests = new HashMap<>();
        AQRegistry.QUESTS.forEach(quest -> quests.put(quest, new QuestInstance(quest, QuestStatus.NOT_STARTED)));
        return quests;
    }

    @Contract("_ -> new")
    public static @NotNull Map<Quest, QuestInstance> getQuestData(MinecraftServer server) {
        return new HashMap<>(getQuestState(server).QUEST_DATA);
    }

    public static QuestInstance getQuestInstance(MinecraftServer server, Quest quest) {
        return getQuestState(server).QUEST_DATA.get(quest);
    }

    public static void setQuestInstance(@NotNull MinecraftServer server, Quest quest, QuestInstance instance) {
        var data = getQuestState(server);
        data.QUEST_DATA.replace(quest, instance);
        data.markDirty();
    }

    public static void setQuestStatus(@NotNull MinecraftServer server, Quest quest, QuestStatus status) {
        var data = getQuestState(server);
        data.QUEST_DATA.get(quest).setStatus(status, server);
        data.markDirty();
    }

    public static void setQuestTime(@NotNull MinecraftServer server, Quest quest, int time) {
        var data = getQuestState(server);
        data.QUEST_DATA.get(quest).setTime(time);
        data.markDirty();
    }
}

package net.aros.arosquests.util;

import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.world.QuestState;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.jetbrains.annotations.NotNull;

import static net.aros.arosquests.ArosQuests.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public final class QuestInstance {
    private final Quest quest;
    private QuestStatus status;

    private int time;

    public QuestInstance(@NotNull Quest quest, @NotNull QuestStatus status) {
        this(quest, status, quest.getDefaultTime().getTime());
    }

    public QuestInstance(@NotNull Quest quest, @NotNull QuestStatus status, int time) {
        this.quest = quest;
        this.status = status;
        this.time = time;
    }

    public Quest getQuest() {
        return quest;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void setStatus(QuestStatus status, MinecraftServer server) {
        this.status = status;
        this.quest.onStatusChange(status, server);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void tick(ServerTickEvent.@NotNull Post event) {
        for (QuestInstance instance : QuestState.getQuestData(event.getServer()).values()) {
            if (instance.status == QuestStatus.COMPLETING && instance.time > 0) {
                instance.time--;

                if (instance.time == 0) instance.quest.onTimeout(event.getServer(), instance);
            }
        }
    }
}

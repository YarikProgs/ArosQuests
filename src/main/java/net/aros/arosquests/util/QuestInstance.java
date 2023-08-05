package net.aros.arosquests.util;

import net.aros.arosquests.quests.base.Quest;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;

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

        ServerTickEvents.START_SERVER_TICK.register(this::tick);
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

    private void tick(MinecraftServer server) {
        if (status == QuestStatus.COMPLETING && time != -1 && time > 0) {
            time--;

            if (time == 0) quest.onTimeout(server, this);
        }
    }
}

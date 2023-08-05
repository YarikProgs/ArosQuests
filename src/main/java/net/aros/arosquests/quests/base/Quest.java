package net.aros.arosquests.quests.base;

import net.aros.arosquests.util.AQRegistry;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.util.QuestTime;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collection;

public abstract class Quest {
    public abstract Text getName();
    public abstract Text getAuthor();
    public abstract Collection<Text> getDescription();
    public abstract QuestTime getDefaultTime();

    public final Identifier getId() {
        return AQRegistry.QUEST.getId(this);
    }

    public static Quest byId(Identifier id) {
        return AQRegistry.QUEST.get(id);
    }

    public abstract void onStatusChange(QuestStatus status, MinecraftServer server);
    public abstract void onTimeout(MinecraftServer server, QuestInstance instance);
}

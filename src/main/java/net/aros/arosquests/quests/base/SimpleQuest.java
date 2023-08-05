package net.aros.arosquests.quests.base;

import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.util.QuestTime;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.io.Serializable;
import java.util.Collection;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class SimpleQuest extends Quest {
    private final Text name, author, description;
    private final QuestTime defaultTime;
    private final Integer authorColor;

    public SimpleQuest(Text name, Text author, Integer authorColor, Text description, QuestTime defaultTime) {
        this.name = name;
        this.author = author;
        this.authorColor = authorColor;
        this.description = description;
        this.defaultTime = defaultTime;
    }

    @Override
    public Text getName() {
        return name;
    }

    @Override
    public Text getAuthor() {
        return author;
    }

    @Override
    public Integer getAuthorColor() {
        return authorColor;
    }

    @Override
    public Text getDescription() {
        return description;
    }

    @Override
    public QuestTime getDefaultTime() {
        return defaultTime;
    }

    @Override
    public void onStatusChange(QuestStatus status, MinecraftServer server) {
        server.getPlayerManager().getPlayerList().forEach(player -> player.sendMessage(Text.translatable("quest." + MOD_ID + ".update").fillStyle(Style.EMPTY.withItalic(true)), true));
    }

    @Override
    public void onTimeout(MinecraftServer server, QuestInstance instance) {
        instance.setStatus(QuestStatus.FAILED, server);
    }
}

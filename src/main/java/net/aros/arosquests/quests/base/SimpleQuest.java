package net.aros.arosquests.quests.base;

import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.util.QuestTime;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.aros.arosquests.ArosQuests.MOD_ID;

@SuppressWarnings("unused")
public class SimpleQuest implements Quest {
    private final Text name, author, description;
    private final QuestTime defaultTime;
    private final int authorColor;
    private final EntityType<? extends LivingEntity> authorEntity;

    public SimpleQuest(Text name, Text author, @Nullable EntityType<? extends LivingEntity> authorEntity, int authorColor, Text description, QuestTime defaultTime) {
        this.name = name;
        this.author = author;
        this.authorEntity = authorEntity;
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
    public @Nullable EntityType<? extends LivingEntity> getAuthorEntityType() {
        return authorEntity;
    }

    @Override
    public int getAuthorColor() {
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
    public void onStatusChange(QuestStatus status, @NotNull MinecraftServer server) {
        server.getPlayerManager().getPlayerList().forEach(player -> player.sendMessage(Text.translatable("quest." + MOD_ID + ".update").fillStyle(Style.EMPTY.withItalic(true)), true));
    }

    @Override
    public void onTimeout(MinecraftServer server, @NotNull QuestInstance instance) {
        instance.setStatus(QuestStatus.FAILED, server);
    }
}

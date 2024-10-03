package net.aros.arosquests.quests.base;

import net.aros.arosquests.util.AQRegistry;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.util.QuestTime;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface Quest {
    Text getName();

    Text getAuthor();

    int getAuthorColor();

    @Nullable EntityType<? extends LivingEntity> getAuthorEntityType();

    Text getDescription();

    QuestTime getDefaultTime();

    void onStatusChange(QuestStatus status, MinecraftServer server);

    void onTimeout(MinecraftServer server, QuestInstance instance);

    default Identifier getId() {
        return AQRegistry.QUESTS.getId(this);
    }

    static Quest byId(Identifier id) {
        return AQRegistry.QUESTS.get(id);
    }
}

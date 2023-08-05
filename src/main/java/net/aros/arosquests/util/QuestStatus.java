package net.aros.arosquests.util;

import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public enum QuestStatus {
    NOT_STARTED(0),
    COMPLETING(1),
    COMPLETED(2),
    FAILED(3),
    FROZEN(4);

    private final int value;

    QuestStatus(int value) {
        this.value = value;
    }

    public int asInt() {
        return value;
    }

    @Nullable
    public static QuestStatus byInt(int value) {
        for (QuestStatus status : values()) if (status.value == value) return status;
        return null;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public Text asText() {
        return Text.translatable("status." + MOD_ID + "." + this);
    }
}

package net.aros.arosquests.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public enum QuestStatus {
    NOT_STARTED(0, null),
    COMPLETING(1, Formatting.WHITE.getColorValue()),
    COMPLETED(2, Formatting.GRAY.getColorValue()),
    FAILED(3, Formatting.RED.getColorValue()),
    FROZEN(4, Formatting.AQUA.getColorValue());

    private final int value;
    private final Integer color;

    QuestStatus(int value, Integer color) {
        this.value = value;
        this.color = color;
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

    public Integer getColor() {
        return color;
    }
}

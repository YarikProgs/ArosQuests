package net.aros.arosquests.util;

import net.minecraft.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class QuestTime {
    protected final int time;

    public static final int MINECRAFT_DAY = 24000;
    public static final int HOUR = 72000;
    public static final int REAL_DAY = HOUR * 24;
    public static final int MINUTE = 1200;

    protected QuestTime(int time) {
        this.time = time;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull QuestTime byMinecraftDays(int days) {
        return new QuestTime(MINECRAFT_DAY * days);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull QuestTime byRealDays(int days) {
        return new QuestTime(REAL_DAY * days);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull QuestTime byHours(int hours) {
        return new QuestTime(HOUR * hours);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull QuestTime byMinutes(int minutes) {
        return new QuestTime(MINUTE * minutes);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull QuestTime infinite() {
        return new QuestTime(-1);
    }

    public int getTime() {
        return time;
    }

    public Text getTimeAsText() {
        if (time == -1) return getTimeAsInfinite();
        if (time < MINECRAFT_DAY) return getTimeAsMinutes();
        if (time < HOUR) return getTimeAsMinecraftDays();
        if (time < REAL_DAY) return getTimeAsHours();
        return getTimeAsRealDays();
    }

    public Text getTimeAsMinutes() {
        return Text.literal(String.valueOf(time / MINUTE)).append(" ").append(Text.translatable("time." + MOD_ID + ".minutes"));
    }

    public Text getTimeAsHours() {
        return Text.literal(String.valueOf(time / HOUR)).append(" ").append(Text.translatable("time." + MOD_ID + ".hours"));
    }

    public Text getTimeAsMinecraftDays() {
        return Text.literal(String.valueOf(time / MINECRAFT_DAY)).append(" ").append(Text.translatable("time." + MOD_ID + ".minecraft_days"));
    }

    public Text getTimeAsRealDays() {
        return Text.literal(String.valueOf(time / REAL_DAY)).append(" ").append(Text.translatable("time." + MOD_ID + ".real_days"));
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull Text getTimeAsInfinite() {
        return Text.translatable("time." + MOD_ID + ".infinite");
    }
}

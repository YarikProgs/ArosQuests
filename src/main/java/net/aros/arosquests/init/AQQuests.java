package net.aros.arosquests.init;

import net.aros.arosquests.quests.ExampleQuest;
import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.quests.base.SimpleQuest;
import net.aros.arosquests.util.AQRegistry;
import net.aros.arosquests.util.QuestTime;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class AQQuests {
    public static final ExampleQuest EXAMPLE_QUEST = register("example_quest", new ExampleQuest());
    public static final SimpleQuest EASY_QUEST = register("easy_quest", new SimpleQuest(Text.literal("Простой квест"), Text.literal("Aros"), EntityType.PLAYER, Formatting.YELLOW.getColorValue(), Text.literal("Очень простой квест!"), QuestTime.byMinecraftDays(4)));
    public static final SimpleQuest HARD_QUEST = register("hard_quest", new SimpleQuest(Text.literal("Сложный квест"), Text.literal("Aros"), EntityType.PLAYER, Formatting.YELLOW.getColorValue(), Text.literal("Очень сложный квест!"), QuestTime.byMinutes(5)));
    public static final SimpleQuest LONG_QUEST = register("long_quest", new SimpleQuest(Text.literal("Долгий квест"), Text.literal("Aros"), EntityType.PLAYER, Formatting.YELLOW.getColorValue(), Text.literal("Очень долгий квест!"), QuestTime.byRealDays(1)));
    public static final SimpleQuest STRANGE_QUEST = register("strange_quest", new SimpleQuest(Text.literal("Странный квест"), Text.literal("Aros"), EntityType.PLAYER, Formatting.YELLOW.getColorValue(), Text.literal("Очень странный квест!"), QuestTime.bySeconds(10)));
    public static final SimpleQuest Q00 = register("0_quest", new SimpleQuest(Text.literal("Квест №0"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q01 = register("1_quest", new SimpleQuest(Text.literal("Квест №1"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q02 = register("2_quest", new SimpleQuest(Text.literal("Квест №2"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q03 = register("3_quest", new SimpleQuest(Text.literal("Квест №3"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q04 = register("4_quest", new SimpleQuest(Text.literal("Квест №4"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q05 = register("5_quest", new SimpleQuest(Text.literal("Квест №5"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q06 = register("6_quest", new SimpleQuest(Text.literal("Квест №6"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q07 = register("7_quest", new SimpleQuest(Text.literal("Квест №7"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q08 = register("8_quest", new SimpleQuest(Text.literal("Квест №8"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q09 = register("9_quest", new SimpleQuest(Text.literal("Квест №9"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q10 = register("10_quest", new SimpleQuest(Text.literal("Квест №10"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q11 = register("11_quest", new SimpleQuest(Text.literal("Квест №11"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q12 = register("12_quest", new SimpleQuest(Text.literal("Квест №12"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));
    public static final SimpleQuest Q13 = register("13_quest", new SimpleQuest(Text.literal("Квест №13"), Text.literal("???"), null, Formatting.BLACK.getColorValue(), Text.empty(), QuestTime.infinite()));

    static <T extends Quest> T register(String name, T quest) {
        return Registry.register(AQRegistry.QUEST, Identifier.of(MOD_ID, name), quest);
    }

    public static void init() {}
}

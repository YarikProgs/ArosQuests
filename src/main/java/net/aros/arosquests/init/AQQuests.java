package net.aros.arosquests.init;

import net.aros.arosquests.quests.ExampleQuest;
import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.util.AQRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class AQQuests {
    public static final ExampleQuest EXAMPLE_QUEST = register("example_quest", new ExampleQuest());

    static <T extends Quest> T register(String name, T quest) {
        return Registry.register(AQRegistry.QUEST, new Identifier(MOD_ID, name), quest);
    }

    public static void init() {}
}

package net.aros.arosquests.util;

import net.aros.arosquests.ArosQuests;
import net.aros.arosquests.quests.base.Quest;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class AQRegistry {
    public static SimpleRegistry<Quest> QUEST = FabricRegistryBuilder.createSimple(Quest.class, new Identifier(MOD_ID, "quest")).buildAndRegister();
    public static RegistryKey<Quest> QUEST_KEY = RegistryKey.of(RegistryKey.ofRegistry(new Identifier(MOD_ID, "quest")), new Identifier(MOD_ID, "quest_key"));

    public static void init() {
        ArosQuests.LOGGER.debug("Initializing: Custom registry");
    }
}
package net.aros.arosquests.util;

import com.mojang.serialization.Lifecycle;
import net.aros.arosquests.ArosQuests;
import net.aros.arosquests.quests.base.Quest;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.NotNull;

import static net.aros.arosquests.ArosQuests.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AQRegistry {
    public static RegistryKey<Registry<Quest>> QUEST_KEY = RegistryKey.ofRegistry(Identifier.of(MOD_ID, "quest"));
    public static SimpleRegistry<Quest> QUESTS = new SimpleRegistry<>(QUEST_KEY, Lifecycle.stable());

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void newRegistryEvent(@NotNull NewRegistryEvent event) {
        event.register(QUESTS);
    }

    public static void init() {
        ArosQuests.LOGGER.debug("Initializing: Custom registry");
    }
}

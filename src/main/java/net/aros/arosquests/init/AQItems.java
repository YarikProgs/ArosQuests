package net.aros.arosquests.init;

import net.aros.arosquests.items.TestRod1;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class AQItems {
    public static final Item TEST_ROD_1 = register("test_rod_1", new TestRod1());

    static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
    }

    public static void init() {}
}

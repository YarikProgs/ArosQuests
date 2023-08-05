package net.aros.arosquests.init;

import net.aros.arosquests.screen.QuestScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class AQScreenHandlers {
    public static ExtendedScreenHandlerType<QuestScreenHandler> QUEST_SCREEN_HANDLER = new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new QuestScreenHandler());

    public static void init() {
        QUEST_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(MOD_ID, "quest"), QUEST_SCREEN_HANDLER);
    }
}

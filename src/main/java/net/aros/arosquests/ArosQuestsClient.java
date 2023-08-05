package net.aros.arosquests;

import net.aros.arosquests.events.KeyInputHandler;
import net.aros.arosquests.init.AQScreenHandlers;
import net.aros.arosquests.network.ModMessages;
import net.aros.arosquests.screen.QuestScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ArosQuestsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(AQScreenHandlers.QUEST_SCREEN_HANDLER, QuestScreen::new);
        KeyInputHandler.register();
        ModMessages.registerS2CPackets();
    }
}

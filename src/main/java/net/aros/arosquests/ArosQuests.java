package net.aros.arosquests;

import net.aros.arosquests.commands.QuestCommand;
import net.aros.arosquests.init.AQItems;
import net.aros.arosquests.init.AQQuests;
import net.aros.arosquests.init.AQScreenHandlers;
import net.aros.arosquests.network.ModMessages;
import net.aros.arosquests.util.AQRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArosQuests implements ModInitializer {
	public static final String MOD_ID = "arosquests";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Aros Quests!");

		AQRegistry.init();
		AQQuests.init();
		AQItems.init();
		AQScreenHandlers.init();
		ModMessages.registerC2SPackets();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> QuestCommand.register(dispatcher));
	}
}

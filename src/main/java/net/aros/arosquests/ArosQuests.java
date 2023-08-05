package net.aros.arosquests;

import net.aros.arosquests.init.AQItems;
import net.aros.arosquests.init.AQQuests;
import net.aros.arosquests.util.AQRegistry;
import net.fabricmc.api.ModInitializer;
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
	}
}

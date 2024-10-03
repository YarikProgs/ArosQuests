package net.aros.arosquests;

import net.aros.arosquests.commands.QuestCommand;
import net.aros.arosquests.payloads.CollectQuestsPayload;
import net.aros.arosquests.util.AQRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.aros.arosquests.ArosQuests.MOD_ID;

@Mod(MOD_ID)
public class ArosQuests {
    public static final String MOD_ID = "arosquests";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public ArosQuests(@NotNull IEventBus bus) {
        LOGGER.info("Hello Aros Quests!");

        AQRegistry.init();

        bus.addListener(this::onPayloadRegister);
        NeoForge.EVENT_BUS.addListener(this::commandRegistrationEvent);
    }

    @SubscribeEvent
    private void commandRegistrationEvent(@NotNull RegisterCommandsEvent event) {
        QuestCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    private void onPayloadRegister(@NotNull RegisterPayloadHandlersEvent event) {
        event.registrar("3.0.0").playToServer(CollectQuestsPayload.ID, CollectQuestsPayload.CODEC, CollectQuestsPayload::receive);
    }
}

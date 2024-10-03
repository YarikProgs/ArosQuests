package net.aros.arosquests;

import net.aros.arosquests.events.KeyInputHandler;
import net.aros.arosquests.payloads.OpenQuestsPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.NotNull;

import static net.aros.arosquests.ArosQuests.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
@SuppressWarnings("unused")
public class ArosQuestsClientEvents {
    @SubscribeEvent
    public static void onPayloadRegister(RegisterPayloadHandlersEvent event) {
        event.registrar("3.0.0").playToClient(OpenQuestsPayload.ID, OpenQuestsPayload.CODEC, OpenQuestsPayload::receive);
    }

    @SubscribeEvent
    public static void keyRegisterEvent(@NotNull RegisterKeyMappingsEvent event) {
        KeyInputHandler.register(event::register);
    }
}

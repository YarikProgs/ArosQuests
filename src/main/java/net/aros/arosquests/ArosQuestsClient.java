package net.aros.arosquests;

import net.aros.arosquests.events.KeyInputHandler;
import net.aros.arosquests.payloads.CollectQuestsPayload;
import net.aros.arosquests.payloads.OpenQuestsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ArosQuestsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        PayloadTypeRegistry.playS2C().register(OpenQuestsPayload.ID, OpenQuestsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CollectQuestsPayload.ID, CollectQuestsPayload.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(OpenQuestsPayload.ID, (payload, ctx) -> OpenQuestsPayload.receive(ctx.client(), payload));
    }
}

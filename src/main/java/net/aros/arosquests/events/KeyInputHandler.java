package net.aros.arosquests.events;

import net.aros.arosquests.payloads.CollectQuestsPayload;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

import static net.aros.arosquests.ArosQuests.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
@SuppressWarnings("unused")
public class KeyInputHandler {
    public static final String KEY_CATEGORY = "key.category." + MOD_ID;
    public static final String KEY_OPEN_QUESTS = "key." + MOD_ID + ".open_quests";

    public static KeyBinding openQuestsKey;

    @SubscribeEvent
    public static void tickEvent(ClientTickEvent.Post event) {
        if (openQuestsKey.wasPressed()) PacketDistributor.sendToServer(new CollectQuestsPayload());
    }

    public static void register(@NotNull Consumer<KeyBinding> registerer) {
        openQuestsKey = new KeyBinding(
            KEY_OPEN_QUESTS, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I, KEY_CATEGORY
        );
        registerer.accept(openQuestsKey);
    }
}

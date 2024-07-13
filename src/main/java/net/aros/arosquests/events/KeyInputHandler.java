package net.aros.arosquests.events;

import net.aros.arosquests.payloads.CollectQuestsPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_GODBORN = "key.category." + MOD_ID;
    public static final String KEY_OPEN_QUESTS = "key." + MOD_ID + ".open_quests";

    public static KeyBinding openQuestsKey;

    public static void registerKeyInputs() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (openQuestsKey.wasPressed()) ClientPlayNetworking.send(new CollectQuestsPayload());
        });
    }

    public static void register() {
        openQuestsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_OPEN_QUESTS, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I, KEY_CATEGORY_GODBORN
        ));

        registerKeyInputs();
    }
}

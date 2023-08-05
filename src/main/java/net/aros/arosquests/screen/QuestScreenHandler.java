package net.aros.arosquests.screen;

import net.aros.arosquests.init.AQScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class QuestScreenHandler extends ScreenHandler {
    public QuestScreenHandler() {
        super(AQScreenHandlers.QUEST_SCREEN_HANDLER, 0);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}

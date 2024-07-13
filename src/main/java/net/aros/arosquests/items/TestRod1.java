package net.aros.arosquests.items;

import net.aros.arosquests.init.AQQuests;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.world.QuestState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestRod1 extends Item {
    public TestRod1() {
        super(new Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            QuestState.setQuestStatus(AQQuests.EASY_QUEST, QuestStatus.COMPLETING, world.getServer());
//            for (int i = 0; i < 14; i++) {
//                QuestState.setQuestStatus(AQRegistry.QUEST.get(Identifier.of(MOD_ID, i + "_quest")), QuestStatus.COMPLETED, world.getServer());
//            }
        }

        return TypedActionResult.success(stack);
    }
}

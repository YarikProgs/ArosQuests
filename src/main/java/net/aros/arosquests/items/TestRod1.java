package net.aros.arosquests.items;

import net.aros.arosquests.init.AQQuests;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.world.QuestState;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestRod1 extends Item {
    public TestRod1() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            if (!user.isSneaking()) {
                user.sendMessage(AQQuests.EXAMPLE_QUEST.getName());
                user.sendMessage(AQQuests.EXAMPLE_QUEST.getAuthor());
                user.sendMessage(AQQuests.EXAMPLE_QUEST.getDefaultTime().getTimeAsText());
                user.sendMessage(Text.literal(QuestState.getQuestInstance(AQQuests.EXAMPLE_QUEST, (ServerWorld) world).getStatus().toString()));
                user.sendMessage(QuestState.getQuestInstance(AQQuests.EXAMPLE_QUEST, (ServerWorld) world).getStatus().asText());
            } else {
                QuestState.setQuestStatus(AQQuests.EXAMPLE_QUEST, QuestStatus.COMPLETED, world.getServer());
            }
        }

        return TypedActionResult.success(stack);
    }
}

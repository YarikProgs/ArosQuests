package net.aros.arosquests.items;

import net.aros.arosquests.init.AQQuests;
import net.aros.arosquests.util.AQRegistry;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.world.QuestState;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class TestRod1 extends Item {
    public TestRod1() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            for (int i = 0; i < 14; i++) {
                QuestState.setQuestStatus(AQRegistry.QUEST.get(new Identifier(MOD_ID, i + "_quest")), QuestStatus.COMPLETED, world.getServer());
            }
        }

        return TypedActionResult.success(stack);
    }
}

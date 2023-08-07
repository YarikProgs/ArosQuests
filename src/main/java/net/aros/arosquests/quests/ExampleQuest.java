package net.aros.arosquests.quests;

import net.aros.arosquests.quests.base.SimpleQuest;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestTime;
import net.minecraft.entity.EntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ExampleQuest extends SimpleQuest {
    public ExampleQuest() {
        super(Text.literal("Экзампл"), Text.literal("Aros"), EntityType.PLAYER,
                Formatting.YELLOW.getColorValue(),
                Text.literal("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Libero nunc consequat interdum varius sit amet mattis. Dictum varius duis at consectetur lorem"),
                QuestTime.bySeconds(10));
    }

    @Override
    public void onTimeout(MinecraftServer server, QuestInstance instance) {
        super.onTimeout(server, instance);

        server.getPlayerManager().getPlayerList().forEach(player -> player.sendMessage(Text.literal(Formatting.GOLD + "[>>]" + Formatting.RESET + " ууууу лох!")));
    }
}

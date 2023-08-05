package net.aros.arosquests.quests;

import net.aros.arosquests.quests.base.SimpleQuest;
import net.aros.arosquests.util.QuestTime;
import net.minecraft.text.Text;

import java.util.List;

public class ExampleQuest extends SimpleQuest {
    public ExampleQuest() {
        super(Text.literal("Экзампл"), Text.literal("Я лично"), List.of(), QuestTime.infinite());
    }
}

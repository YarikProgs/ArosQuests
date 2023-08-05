package net.aros.arosquests.quests;

import net.aros.arosquests.quests.base.SimpleQuest;
import net.aros.arosquests.util.QuestTime;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ExampleQuest extends SimpleQuest {
    public ExampleQuest() {
        super(Text.literal("Экзампл"), Text.literal("Я лично"), Formatting.YELLOW.getColorValue(), Text.literal("Короче тестовое описание квеста, хз что тут писать если честно. Я Aros! Я написал этот мод, и я специально тяну описание чтобы проверить сколько текста оно вмещает :)"), QuestTime.bySeconds(10));
    }
}

package net.aros.arosquests.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.aros.arosquests.util.QuestInstance;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class QuestTitleWidget extends ClickableWidget {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/quest_overlay.png");

    public final TextRenderer renderer;
    private final QuestInstance instance;

    public QuestTitleWidget(int x, int y, QuestInstance instance, TextRenderer renderer) {
        super(x, y, Math.round(renderer.getWidth(instance.getQuest().getName()) * 1.5F), 12, Text.empty());
        this.renderer = renderer;
        this.instance = instance;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //super.renderButton(matrices, mouseX, mouseY, delta);
        drawTextWithScale(matrices, instance.getQuest().getName(), x, y, instance.getStatus().getColor(), 1.5F);
        if (isHovered()) drawQuestInfo(matrices, mouseX, mouseY);
    }

    public void drawQuestInfo(MatrixStack matrices, int x, int y) {
        // Texture
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x, y, 0, 0, 65*3, 65*3, 65*3, 65*3);

        Text author = Text.translatable("quest." + MOD_ID + ".author").append(": ");
        Text status = Text.translatable("quest." + MOD_ID + ".status").append(": ");
        Text time = Text.translatable("quest." + MOD_ID + ".time").append(": ");
        Text description = Text.translatable("quest." + MOD_ID + ".description").append(": ");
        int white = 16777215;

        // Quest name
        drawTextWithScale(matrices, instance.getQuest().getName(), x+65, y+8, white, 1.5F);

        // Author name
        renderer.drawWithShadow(matrices, author, x+8, y+24, white);
        renderer.drawWithShadow(matrices, Text.literal("|"), x + 9, y + 36, white);
        renderer.drawWithShadow(matrices, instance.getQuest().getAuthor(), x+16, y+36, instance.getQuest().getAuthorColor());

        // Quest status
        renderer.drawWithShadow(matrices, status, x+8, y+54, white);
        renderer.drawWithShadow(matrices, "|", x + 9, y + 66, white);
        renderer.drawWithShadow(matrices, instance.getStatus().asText(), x+16, y+66, instance.getStatus().getColor());

        // Quest time
        renderer.drawWithShadow(matrices, time, x+8, y+84, white);
        renderer.drawWithShadow(matrices, "|", x + 9, y + 96, white);
        renderer.drawWithShadow(matrices, instance.getQuest().getDefaultTime().getTimeAsText(), x+16, y+96, 16755200);

        // Quest description
        renderer.drawWithShadow(matrices, description, x+8, y+114, white);
        int i = 0;
        for (OrderedText text : renderer.wrapLines(instance.getQuest().getDescription(), 170)) {
            renderer.drawWithShadow(matrices, "|", x + 9, y + 126 + i * 9, white);
            renderer.drawWithShadow(matrices, text, x + 16, y + 126 + i++ * 9, white);
        }
    }

    protected void drawTextWithScale(MatrixStack matrices, Text text, float x, float y, Integer color, float size) {
        matrices.scale(size, size, size);
        renderer.drawWithShadow(matrices, text, x/size, y/size, color);
        matrices.scale(1.0F/size, 1.0F/size, 1.0F/size);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public void playDownSound(SoundManager soundManager) {

    }
}

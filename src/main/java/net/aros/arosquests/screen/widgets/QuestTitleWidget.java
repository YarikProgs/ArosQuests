package net.aros.arosquests.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class QuestTitleWidget extends ClickableWidget { // Мне лично удобнее использовать clickable widget, но можно и как в status widget
    private static final Identifier TEXTURE_1 = new Identifier(MOD_ID, "textures/gui/quest_overlay.png");
    private static final Identifier TEXTURE_2 = new Identifier(MOD_ID, "textures/gui/quest_overlay_2.png");

    public final TextRenderer renderer;
    private final QuestInstance instance;
    private final LivingEntity entity;
    private double mouseX, mouseY;

    public QuestTitleWidget(int x, int y, QuestInstance instance, TextRenderer renderer) {
        super(x, y, Math.round(renderer.getWidth(instance.getQuest().getName()) * 1.5F), 12, Text.empty());
        this.renderer = renderer;
        this.instance = instance;
        this.entity = instance.getQuest().getAuthorEntityType() == EntityType.PLAYER ? MinecraftClient.getInstance().player :
                instance.getQuest().getAuthorEntity(MinecraftClient.getInstance().world);
    }

    // А зачем тут кнопка?
    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    // Опять же, рендерим не на прямую
    public void renderName(MatrixStack matrices) {
        Text text = List.of(QuestStatus.COMPLETED, QuestStatus.FAILED).contains(instance.getStatus()) ? instance.getQuest().getName().copy().formatted(Formatting.STRIKETHROUGH) : instance.getQuest().getName().copy();

        drawTextWithSize(matrices, text, x, y, instance.getStatus().getColor(), 1.5F);
    }

    // Рендерим не напрямую, а в экране, чтобы инфа была выше всех
    public void tryRenderInfo(MatrixStack matrices, int x, int y) {
        if (isHovered()) {
            // Транслейтим чтобы текст квеста не отрисовывался поверх текста квест-инфы
            matrices.translate(0.0, 0.0, 1.0);
            drawQuestInfo(matrices, x, y);
            matrices.translate(0.0, 0.0, -1.0);
        }
    }

    // Тут отрисовывается ВСЯ информация о квесте
    public void drawQuestInfo(MatrixStack matrices, int x, int y) {
        // Texture

        RenderSystem.setShaderTexture(0, TEXTURE_1);
        drawTexture(matrices, x, y, 0, 0, 65*3, 65*3, 65*3, 65*3);

        Text author = Text.translatable("quest." + MOD_ID + ".author").append(": ");
        Text status = Text.translatable("quest." + MOD_ID + ".status").append(": ");
        Text time = Text.translatable("quest." + MOD_ID + ".time").append(": ");
        Text description = Text.translatable("quest." + MOD_ID + ".description").append(": ");
        int white = 16777215;

        // Quest name
        drawCenteredTextWithSize(matrices, instance.getQuest().getName(), x+97, y+8, white, 1.5F);

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
        if (instance.getQuest().getDescription().getString().equals("")) {
            renderer.drawWithShadow(matrices, Text.translatable("quest.arosquests.description.empty").copy().formatted(Formatting.ITALIC), x+16, y+126, Formatting.GRAY.getColorValue());
        } else {
            int i = 0;
            for (OrderedText text : renderer.wrapLines(instance.getQuest().getDescription(), 170)) {
                renderer.drawWithShadow(matrices, "|", x + 9, y + 126 + i * 9, white);
                renderer.drawWithShadow(matrices, text, x + 16, y + 126 + i++ * 9, white);
            }
        }
        if (entity == null) return;

        // Entity texture
        RenderSystem.setShaderTexture(0, TEXTURE_2);
        drawTexture(matrices, x + 65*3, y, 0, 0, 43*3, 64*3, 43*3, 64*3);

        InventoryScreen.drawEntity(x + 65*3 + 65, y+158, 60, (float) mouseX - 15, (float) mouseY - 5, entity);

        drawCenteredTextWithSize(matrices, Text.literal("[ ").append(instance.getQuest().getAuthor()).append(" ]"), x + 259, y+167, instance.getQuest().getAuthorColor(), 1.5F);
    }

    // Удобненько
    protected void drawTextWithSize(MatrixStack matrices, Text text, float x, float y, Integer color, float size) {
        matrices.scale(size, size, size);
        renderer.drawWithShadow(matrices, text, x/size, y/size, color);
        matrices.scale(1.0F/size, 1.0F/size, 1.0F/size);
    }

    // Очень удобненько
    protected void drawCenteredTextWithSize(MatrixStack matrices, Text text, float x, float y, Integer color, float size) {
        matrices.scale(size, size, size);
        //drawCenteredTextWithShadow(matrices, renderer, text.asOrderedText(), Math.round(x/size), Math.round(y/size), color);
        renderer.drawWithShadow(matrices, text, (x - renderer.getWidth(text) / 2.0F) / size - 7, y / size, color);
        matrices.scale(1/size, 1/size, 1/size);
    }

    @Override
    public SelectionType getType() {
        return SelectionType.HOVERED;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    // И звук тут тоже не нужен
    @Override
    public void playDownSound(SoundManager soundManager) {

    }

    // Отслеживаем мышь
    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}

package net.aros.arosquests.screen.widgets;

import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class QuestTitleWidget extends ClickableWidget { // Мне лично удобнее использовать clickable widget, но можно и как в status widget
    private static final Identifier TEXTURE_1 = Identifier.of(MOD_ID, "textures/gui/quest_overlay.png");
    private static final Identifier TEXTURE_2 = Identifier.of(MOD_ID, "textures/gui/quest_overlay_2.png");

    public final TextRenderer renderer;
    private final QuestInstance instance;
    private final LivingEntity entity;
    private double mouseX, mouseY;
    private final int x, y;

    public QuestTitleWidget(int x, int y, QuestInstance instance, TextRenderer renderer) {
        super(x, y, Math.round(renderer.getWidth(instance.getQuest().getName()) * 1.5F), 12, Text.empty());
        this.x = x;
        this.y = y;
        this.renderer = renderer;
        this.instance = instance;
        this.entity = instance.getQuest().getAuthorEntityType() == EntityType.PLAYER ? MinecraftClient.getInstance().player :
            instance.getQuest().getAuthorEntity(MinecraftClient.getInstance().world);
    }

    // А зачем тут кнопка?

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    // Опять же, рендерим не на прямую
    public void renderName(DrawContext ctx) {
        Text text = List.of(QuestStatus.COMPLETED, QuestStatus.FAILED).contains(instance.getStatus()) ? instance.getQuest().getName().copy().formatted(Formatting.STRIKETHROUGH) : instance.getQuest().getName().copy();

        drawTextWithSize(ctx, text, x, y, instance.getStatus().getColor(), 1.5F);
    }

    // Рендерим не напрямую, а в экране, чтобы инфа была выше всех
    public void tryRenderInfo(DrawContext ctx, int x, int y) {
        if (isHovered()) {
            // Транслейтим чтобы текст квеста не отрисовывался поверх текста квест-инфы
            ctx.getMatrices().translate(0.0, 0.0, 1.0);
            drawQuestInfo(ctx, x, y);
            ctx.getMatrices().translate(0.0, 0.0, -1.0);
        }
    }

    // Тут отрисовывается ВСЯ информация о квесте
    public void drawQuestInfo(DrawContext ctx, int x, int y) {
        // Texture
        MatrixStack matrices = ctx.getMatrices();

        ctx.drawTexture(TEXTURE_1, x, y, 0, 0, 65 * 3, 65 * 3, 65 * 3, 65 * 3);

        Text author = Text.translatable("quest." + MOD_ID + ".author").append(": ");
        Text status = Text.translatable("quest." + MOD_ID + ".status").append(": ");
        Text time = Text.translatable("quest." + MOD_ID + ".time").append(": ");
        Text description = Text.translatable("quest." + MOD_ID + ".description").append(": ");
        int white = 16777215;

        // Quest name
        drawCenteredTextWithSize(ctx, instance.getQuest().getName(), x + 97, y + 8, white, 1.5F);

        // Author name
        ctx.drawTextWithShadow(renderer, author, x + 8, y + 24, white);
        ctx.drawTextWithShadow(renderer, Text.literal("|"), x + 9, y + 36, white);
        ctx.drawTextWithShadow(renderer, instance.getQuest().getAuthor(), x + 16, y + 36, instance.getQuest().getAuthorColor());

        // Quest status
        ctx.drawTextWithShadow(renderer, status, x + 8, y + 54, white);
        ctx.drawTextWithShadow(renderer, "|", x + 9, y + 66, white);
        ctx.drawTextWithShadow(renderer, instance.getStatus().asText(), x + 16, y + 66, instance.getStatus().getColor());

        // Quest time
        ctx.drawTextWithShadow(renderer, time, x + 8, y + 84, white);
        ctx.drawTextWithShadow(renderer, "|", x + 9, y + 96, white);
        ctx.drawTextWithShadow(renderer, instance.getQuest().getDefaultTime().getTimeAsText(), x + 16, y + 96, 16755200);

        // Quest description
        ctx.drawTextWithShadow(renderer, description, x + 8, y + 114, white);
        if (instance.getQuest().getDescription().getString().equals("")) {
            ctx.drawTextWithShadow(renderer, Text.translatable("quest.arosquests.description.empty").copy().formatted(Formatting.ITALIC), x + 16, y + 126, Formatting.GRAY.getColorValue());
        } else {
            int i = 0;
            for (OrderedText text : renderer.wrapLines(instance.getQuest().getDescription(), 170)) {
                ctx.drawTextWithShadow(renderer, "|", x + 9, y + 126 + i * 9, white);
                ctx.drawTextWithShadow(renderer, text, x + 16, y + 126 + i++ * 9, white);
            }
        }
        if (entity == null) return;

        // Entity texture
        ctx.drawTexture(TEXTURE_2, x + 65 * 3, y, 0, 0, 43 * 3, 64 * 3, 43 * 3, 64 * 3);

        int x_ = x + 65 * 3 + 65;
        int y_ = y + 158;
        InventoryScreen.drawEntity(ctx, x_, y_, x_ + 49, y_ + 70, 60, 0.0625f, (float) mouseX - 15, (float) mouseY - 5, entity);

        drawCenteredTextWithSize(ctx, Text.literal("[ ").append(instance.getQuest().getAuthor()).append(" ]"), x + 259, y + 167, instance.getQuest().getAuthorColor(), 1.5F);
    }

    // Удобненько
    protected void drawTextWithSize(DrawContext ctx, Text text, float x, float y, Integer color, float size) {
        ctx.getMatrices().scale(size, size, size);
        ctx.drawTextWithShadow(renderer, text, (int) (x / size), (int) (y / size), color);
        ctx.getMatrices().scale(1.0F / size, 1.0F / size, 1.0F / size);
    }

    // Очень удобненько
    protected void drawCenteredTextWithSize(DrawContext ctx, Text text, float x, float y, Integer color, float size) {
        ctx.getMatrices().scale(size, size, size);
        //drawCenteredTextWithShadow(matrices, renderer, text.asOrderedText(), Math.round(x/size), Math.round(y/size), color);
        ctx.drawTextWithShadow(renderer, text, (int) ((x - renderer.getWidth(text) / 2.0F) / size - 7), (int) (y / size), color);
        ctx.getMatrices().scale(1 / size, 1 / size, 1 / size);
    }

    @Override
    public SelectionType getType() {
        return SelectionType.HOVERED;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    protected void appendDefaultNarrations(NarrationMessageBuilder builder) {

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

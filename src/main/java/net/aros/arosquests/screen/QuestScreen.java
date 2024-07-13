package net.aros.arosquests.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.aros.arosquests.screen.widgets.MoveButton;
import net.aros.arosquests.screen.widgets.QuestTitleWidget;
import net.aros.arosquests.screen.widgets.StatusWidget;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class QuestScreen extends Screen {
    private static final Identifier TEXTURE = Identifier.of(MOD_ID, "textures/gui/quests.png");
    private final Collection<QuestInstance> questData = new ArrayList<>();
    private List<QuestInstance> sorted = new ArrayList<>();
    private final List<StatusWidget> statusWidgets = new ArrayList<>();
    private final List<QuestTitleWidget> questTitleWidgets = new ArrayList<>();
    private final List<MoveButton> moveButtons = new ArrayList<>();
    protected int backgroundWidth = 176;
    protected int backgroundHeight = 166;
    protected int x, y;
    private int startIndex = 0;

    public QuestScreen(Collection<QuestInstance> quests) {
        super(Text.empty());
        this.questData.addAll(quests);
    }

    @Override
    protected void init() {
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;

        if (questData.isEmpty()) return;
        sorted = new ArrayList<>(questData);

        sorted.removeIf(i -> i.getStatus() == QuestStatus.NOT_STARTED);
        sorted.sort((i1, i2) -> Integer.compare(i2.getStatus().getPriority(), i1.getStatus().getPriority()));

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        for (QuestInstance instance : sorted.size() > 15 ? getSub(startIndex) : sorted) {
            int localY = y - 50 + sorted.indexOf(instance) * 15 - startIndex * 15;
            questTitleWidgets.add(addDrawableChild(new QuestTitleWidget(x - 54, localY, instance, textRenderer)));
            statusWidgets.add(addDrawableChild(new StatusWidget(x - 70, localY, 12, 12, instance.getStatus())));
        }

        if (sorted.size() > 15) {
            moveButtons.add(addDrawableChild(new MoveButton(x + 200, y - 60, 20, 20, Text.literal("▲"), this::buttonUp)));
            moveButtons.add(addDrawableChild(new MoveButton(x + 200, y + 160, 20, 20, Text.literal("▼"), this::buttonDown)));
        }
    }

    // Листаем квесты вверх
    private boolean buttonUp() {
        if (startIndex > 0) {
            startIndex -= 1;
            redraw();
            return true;
        }
        return false;
    }

    // Листаем квесты вниз
    private boolean buttonDown() {
        if (startIndex < sorted.size() && getSub(startIndex).size() == 15 && getSub(startIndex + 1).size() > 14) {
            startIndex += 1;
            redraw();
            return true;
        }
        return false;
    }

    // Перерисовка
    private void redraw() {
        clearChildren();
        questTitleWidgets.clear();
        statusWidgets.clear();
        init();
    }

    // Удобненько
    private @NotNull List<QuestInstance> getSub(int startIndex) {
        return sorted.subList(Math.max(0, startIndex), Math.min(startIndex + 15, sorted.size()));
    }

    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth * 2) / 2;
        int y = (height - backgroundHeight * 2) / 2;

        int size = MinecraftClient.getInstance().options.getGuiScale().getValue() == 4 ? 256 : 384;

        ctx.drawTexture(TEXTURE, x, y, 0, 0, size, size, size, size);

        // Рендерим всё объекты здесь, чтобы они не наслаивались на квест-инфу

        moveButtons.forEach(button -> button.renderWidget(ctx, mouseX, mouseY, delta));
        questTitleWidgets.forEach(title -> title.renderName(ctx));
        questTitleWidgets.forEach(title -> title.tryRenderInfo(ctx, mouseX, mouseY));
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {}

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.applyBlur(delta);
        this.renderDarkening(ctx);
        drawBackground(ctx, delta, mouseX, mouseY);
        super.render(ctx, mouseX, mouseY, delta);
        for (StatusWidget widget : statusWidgets)
            if (widget.isHovered()) ctx.drawTooltip(textRenderer, widget.getStatus().asText(), mouseX, mouseY);
    }
}

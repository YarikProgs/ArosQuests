package net.aros.arosquests.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.aros.arosquests.screen.widgets.MoveButton;
import net.aros.arosquests.screen.widgets.QuestTitleWidget;
import net.aros.arosquests.screen.widgets.StatusWidget;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class QuestScreen extends HandledScreen<QuestScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/quests.png");
    private final Collection<QuestInstance> questData = new ArrayList<>();
    private List<QuestInstance> sorted = new ArrayList<>();
    private final List<StatusWidget> statusWidgets = new ArrayList<>();
    private final List<QuestTitleWidget> questTitleWidgets = new ArrayList<>();
    private final List<MoveButton> moveButtons = new ArrayList<>();
    private int startIndex = 0;

    public QuestScreen(QuestScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Contract(" -> new")
    public static @NotNull QuestScreen simple() {
        return new QuestScreen(new QuestScreenHandler(), new PlayerInventory(null), Text.empty());
    }

    // Добавляем сюда квесты
    public void addQuests(Collection<QuestInstance> questData) {
        this.questData.addAll(questData);
    }

    @Override
    protected void init() {
        super.init();
        playerInventoryTitleX = -999999;

        if (questData.isEmpty()) return;
        sorted = new ArrayList<>(questData);

        sorted.removeIf(i -> i.getStatus() == QuestStatus.NOT_STARTED);
        sorted.sort((i1, i2) -> Integer.compare(i2.getStatus().getPriority(), i1.getStatus().getPriority()));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        for (QuestInstance instance : sorted.size() > 15 ? getSub(startIndex) : sorted) {
            int localY = y - 50 + sorted.indexOf(instance) * 15 - startIndex * 15;
            questTitleWidgets.add(addDrawableChild(new QuestTitleWidget(x-54, localY, instance, textRenderer)));
            statusWidgets.add(addDrawableChild(new StatusWidget(x-70, localY, 12, 12, instance.getStatus())));
        }

        if (sorted.size() > 15) {
            moveButtons.add(addDrawableChild(new MoveButton(x+200, y-60, 20, 20,  Text.literal("▲"), this::buttonUp)));
            moveButtons.add(addDrawableChild(new MoveButton(x+200, y+160, 20, 20, Text.literal("▼"), this::buttonDown)));
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
        if (startIndex < sorted.size() && getSub(startIndex).size() == 15 && getSub(startIndex+1).size() > 14) {
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
        return sorted.subList(Math.max(0, startIndex), Math.min(startIndex+15, sorted.size()));
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth * 2) / 2;
        int y = (height - backgroundHeight * 2) / 2;
        
        int size = MinecraftClient.getInstance().options.getGuiScale().getValue() == 4 ? 256 : 384;
        
        drawTexture(matrices, x, y, 0, 0, size, size, size, size);

        // Рендерим всё объекты здесь, чтобы они не наслаивались на квест-инфу

        moveButtons.forEach(button -> button.renderButton(matrices, mouseX, mouseY, delta));
        questTitleWidgets.forEach(title -> title.renderName(matrices));
        questTitleWidgets.forEach(title -> title.tryRenderInfo(matrices, mouseX, mouseY));
    }

    // Прикольно
    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        for (StatusWidget widget : statusWidgets) if (widget.isHovered()) renderTooltip(matrices, widget.getStatus().asText(), x, y);
    }

    @Override
    public boolean shouldPause() {
        return true;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}

package net.aros.arosquests.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.screen.widgets.QuestTitleWidget;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class QuestScreen extends HandledScreen<QuestScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/quests.png");
    private final Collection<QuestInstance> questData = new ArrayList<>();

    public QuestScreen(QuestScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.empty());
    }

    public static QuestScreen simple() {
        return new QuestScreen(new QuestScreenHandler(), new PlayerInventory(null), Text.empty());
    }

    public void addQuests(Collection<QuestInstance> questData) {
        this.questData.addAll(questData);
    }

    @Override
    protected void init() {
        super.init();
        playerInventoryTitleX = -9999;

        if (questData.isEmpty()) return;
        List<QuestInstance> sorted = new ArrayList<>();

        for (QuestInstance instance : questData) {
            switch (instance.getStatus()) {
                case NOT_STARTED -> {}
                case COMPLETED -> sorted.add(instance);
                default -> sorted.add(0, instance);
            }
        }

        for (QuestInstance instance : sorted) {
            int localY = y - 50 + sorted.indexOf(instance) * 15;
            addDrawableChild(new QuestTitleWidget(330, localY, instance, textRenderer));
        }
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
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
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

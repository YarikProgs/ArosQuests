package net.aros.arosquests.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.aros.arosquests.util.QuestStatus;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static net.aros.arosquests.ArosQuests.MOD_ID;

public class StatusWidget extends DrawableHelper implements Drawable, Element, Selectable {
    private final int x, y, width, height;
    private final QuestStatus status;
    private boolean isHovered;

    public StatusWidget(int x, int y, int width, int height, QuestStatus status) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.status = status;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        RenderSystem.setShaderTexture(0, new Identifier(MOD_ID, "textures/gui/status_icons/" + status.toString() + ".png"));
        drawTexture(matrices, x, y, 0, 0, width, height, width, height);
    }

    public boolean isHovered() {
        return isHovered;
    }

    public QuestStatus getStatus() {
        return status;
    }

    @Override
    public SelectionType getType() {
        return SelectionType.HOVERED;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {}
}

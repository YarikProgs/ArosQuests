package net.aros.arosquests.screen.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class MoveButton extends PressableWidget {
    private final Supplier<Boolean> onPress;

    public MoveButton(int x, int y, int width, int height, Text message, Supplier<Boolean> onPress) {
        super(x, y, width, height, message);
        this.onPress = onPress;
    }

    @Override
    public void onPress() {
        if (this.onPress.get()) playDownSound(MinecraftClient.getInstance().getSoundManager());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            // Рендерим кнопку в экране, чтобы она не наслаивалась на квест-инфу
        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (visible) super.renderButton(matrices, mouseX, mouseY, delta);
    }

    // Мне кажется этот звук в данной ситуации будет покруче
    @Override
    public void playDownSound(SoundManager soundManager) {
        soundManager.play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
}

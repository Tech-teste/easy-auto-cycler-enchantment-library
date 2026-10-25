package br.com.du.enchantbrowser;
import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
final class StateButton extends Button {
    private final BooleanSupplier enabled;
    StateButton(int x, int y, int w, int h, BooleanSupplier enabled, Runnable action) {
        super(x, y, w, h, Component.literal(enabled.getAsBoolean() ? Text.t("active") : Text.t("inactive")), b -> action.run(), DEFAULT_NARRATION);
        this.enabled = enabled;
    }
    @Override protected void extractContents(GuiGraphicsExtractor g, int mx, int my, float dt) {
        boolean on = enabled.getAsBoolean();
        String label = getWidth()>90 ? Text.t(on ? "active_search" : "inactive_search") : on ? "+" : "X";
        setMessage(Component.literal(label).withStyle(on ? net.minecraft.ChatFormatting.GREEN : net.minecraft.ChatFormatting.RED));
        // Follow Button.Plain's native rendering contract, including its widget text collector.
        extractDefaultSprite(g);
        extractDefaultLabel(g.textRendererForWidget(this, GuiGraphicsExtractor.HoveredTextEffects.NONE));
    }
}

package br.com.du.enchantbrowser;
import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
final class StateButton extends Button {
    private final BooleanSupplier enabled;
    StateButton(int x, int y, int w, int h, BooleanSupplier enabled, Runnable action) {
        super(x, y, w, h, Component.literal(enabled.getAsBoolean() ? Text.t("active") : Text.t("inactive")), b -> action.run(), DEFAULT_NARRATION);
        this.enabled = enabled;
    }
    @Override protected void renderWidget(GuiGraphics g, int mx, int my, float dt) {
        boolean on = enabled.getAsBoolean();
        setMessage(Component.literal(on ? Text.t("disable") : Text.t("enable")));
        int x = getX(), y = getY(), w = getWidth(), h = getHeight();
        int border = isHoveredOrFocused() ? 0xFFFFFFFF : on ? 0xFF81D89C : 0xFFF19595;
        int fill = !active ? 0xFF44444A : on ? 0xFF23633E : 0xFF783838;
        g.fill(x,y,x+w,y+h,border); g.fill(x+1,y+1,x+w-1,y+h-1,fill);
        int ix = w > 90 ? x + 10 : x + w/2 - 5, iy = y + h/2 - 5;
        if (on) {
            for (int i=0;i<4;i++) g.fill(ix+i,iy+4+i,ix+i+2,iy+6+i,0xFFFFFFFF);
            for (int i=0;i<7;i++) g.fill(ix+3+i,iy+7-i,ix+5+i,iy+9-i,0xFFFFFFFF);
        } else for (int i=0;i<9;i++) {
            g.fill(ix+i,iy+i,ix+i+2,iy+i+2,0xFFFFFFFF);
            g.fill(ix+8-i,iy+i,ix+10-i,iy+i+2,0xFFFFFFFF);
        }
        if (w > 90) g.drawCenteredString(Minecraft.getInstance().font, on ? Text.t("active_search") : Text.t("inactive_search"), x+w/2+8,y+(h-8)/2,0xFFFFFFFF);
    }
}

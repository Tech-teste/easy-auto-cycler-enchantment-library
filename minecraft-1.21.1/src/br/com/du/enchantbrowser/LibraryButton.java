package br.com.du.enchantbrowser;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
/** Emphasizes the current row and the apply action without changing button behavior. */
final class LibraryButton extends Button {
    private final boolean emphasized, primary;
    LibraryButton(int x,int y,int w,int h,Component label,boolean emphasized,boolean primary,Runnable action) {
        super(x,y,w,h,label,b -> action.run(),DEFAULT_NARRATION);
        this.emphasized=emphasized;
        this.primary=primary;
    }
    @Override protected void renderWidget(GuiGraphics g, int mx, int my, float dt) {
        if (!emphasized) { super.renderWidget(g,mx,my,dt); return; }
        int x=getX(), y=getY(), w=getWidth(), h=getHeight();
        g.fill(x,y,x+w,y+h,isHoveredOrFocused() ? 0xFFFFFFFF : primary ? 0xFF83C99A : 0xFFE0BC64);
        g.fill(x+1,y+1,x+w-1,y+h-1,primary ? 0xFF244F38 : 0xFF5A4827);
        renderString(g, net.minecraft.client.Minecraft.getInstance().font, 0xFFFFFFFF);
    }
}

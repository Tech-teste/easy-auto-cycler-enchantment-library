package br.com.du.enchantbrowser;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
final class CategoryScreen extends Screen {
    private final BrowserScreen parent;
    private final String current;
    private int panelLeft, panelTop, panelWidth, panelHeight;
    CategoryScreen(BrowserScreen parent, String current) { super(Component.literal(Text.t("category_title"))); this.parent = parent; this.current = current; }
    @Override protected void init() {
        int w = Math.min(420, width - 30), cell = (w - 12) / 3, left = (width - w) / 2;
        panelLeft = left - 8; panelWidth = w + 16;
        int rows = (Catalog.CATEGORIES.length + 2) / 3;
        panelHeight = 40 + rows * 22 + 32;
        panelTop = Math.max(2, (height - panelHeight) / 2);
        for (int i = 0; i < Catalog.CATEGORIES.length; i++) {
            String category = Catalog.CATEGORIES[i];
            var label = Component.literal(Text.category(category));
            if (category.equals(current)) label.withStyle(net.minecraft.ChatFormatting.YELLOW);
            addRenderableWidget(new EquipmentButton(left + (i % 3) * (cell + 6), panelTop + 40 + (i / 3) * 22, cell, 20, label, category, () -> { parent.chooseCategory(category); onClose(); }));
        }
        addRenderableWidget(Button.builder(Component.literal(Text.t("back")), b -> onClose()).bounds(width / 2 - 60, panelTop + panelHeight - 27, 120, 20).build());
    }
    @Override public void extractBackground(GuiGraphicsExtractor g, int x, int y, float dt) {
        super.extractBackground(g, x, y, dt);
        g.fill(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xF023242C);
        g.centeredText(font, title, width / 2, panelTop + 12, 0xFFF2D38B);
        g.centeredText(font, Text.t("category_hint"), width / 2, panelTop + 26, 0xFFCCD2DF);
    }
    @Override public void onClose() { minecraft.setScreen(parent); }
    @Override public boolean isPauseScreen() { return false; }
}

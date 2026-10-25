package br.com.du.enchantbrowser;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

final class DescriptionScreen extends Screen {
    private final BrowserScreen parent;
    private final Catalog.Entry entry;
    private int scroll;
    private boolean technical;
    private int panelTop, panelHeight, contentWidth;
    DescriptionScreen(BrowserScreen parent, Catalog.Entry entry) { super(Component.literal(entry.name())); this.parent = parent; this.entry = entry; }
    private String content() {
        String text = Text.t("effect") + "\n" + entry.description() + "\n\n" + Text.t("equipment") + "\n" + Text.categories(entry.categories()) + "\n\n" + Text.t("trade_maximum", entry.maximum());
        if (technical) text += "\n\n" + Text.t("technical_details", entry.id().getNamespace(), entry.id());
        return text;
    }
    @Override protected void init() {
        contentWidth = Math.min(480, width - 80);
        panelHeight = Math.min(height - 12, Math.max(180, font.split(Component.literal(content()), contentWidth).size() * 12 + 72));
        panelTop = (height - panelHeight) / 2;
        addRenderableWidget(Button.builder(Component.literal(Text.t("back_to_list")), b -> onClose()).bounds(width / 2 - 146, panelTop + panelHeight - 25, 140, 20).build());
        addRenderableWidget(Button.builder(Component.literal(technical ? Text.t("hide_details") : Text.t("show_details")), b -> { technical = !technical; scroll = 0; rebuildWidgets(); }).bounds(width / 2 + 6, panelTop + panelHeight - 25, 140, 20).build());
        addRenderableWidget(Button.builder(Component.literal("↑"), b -> scroll = Math.max(0, scroll - 44)).bounds(width / 2 + contentWidth / 2 + 14, panelTop + 35, 20, 20).build());
        addRenderableWidget(Button.builder(Component.literal("↓"), b -> scroll += 44).bounds(width / 2 + contentWidth / 2 + 14, panelTop + panelHeight - 55, 20, 20).build());
    }
    @Override public void extractBackground(GuiGraphicsExtractor g, int x, int y, float dt) {
        super.extractBackground(g, x, y, dt);
        int w = contentWidth, l = (width - w) / 2;
        g.fill(l - 12, panelTop, l + w + 12, panelTop + panelHeight - 30, 0xF023242C);
        g.centeredText(font, title, width / 2, panelTop + 12, 0xFFF2D38B);
        var lines = font.split(Component.literal(content()), w);
        scroll = Math.min(scroll, Math.max(0, lines.size() * 12 - (panelHeight - 78)));
        g.enableScissor(l, panelTop + 36, l + w, panelTop + panelHeight - 38);
        int top = panelTop + 36 - scroll;
        for (var line : lines) { g.text(font, line, l, top, 0xFFD9D6CA); top += 12; }
        g.disableScissor();
    }
    @Override public boolean mouseScrolled(double x, double y, double dx, double dy) { scroll = Math.max(0, scroll + (dy < 0 ? 24 : -24)); return true; }
    @Override public void onClose() { minecraft.setScreen(parent); }
    @Override public boolean isPauseScreen() { return false; }
}

package br.com.du.enchantbrowser;

import java.util.*;
import com.uncraftbar.easyautocycler.AutomationManager;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

final class BrowserScreen extends Screen {
    private final Screen previous, original;
    private final Catalog catalog = new Catalog();
    final UserLibrary library = UserLibrary.load();
    private final List<Button> rowWidgets = new ArrayList<>();
    private List<Catalog.Entry> visible = List.of();
    private Catalog.Entry selected;
    private EditBox search, price;
    private Button toggle, maximum, backPage, nextPage, detail, mode, shown;
    private String query = "", category = "Todos";
    private boolean favoritesOnly;
    private String notice="";
    private boolean onlySelected, matchAny = AutomationManager.INSTANCE.isMatchAny();
    private int page, pageSize, left, right, panelWidth, listWidth, rowsTop, rowsBottom, detailScroll;
    private boolean updatingPrice;
    private String error = "";
    BrowserScreen(Screen previous, Screen original) {
        super(Component.literal(Text.t("title")));
        this.previous = previous;
        this.original = original;
        var state = BrowserState.load();
        category = state.category();
        query = state.query();
        onlySelected = state.onlySelected();
        page = state.page();
        selected = catalog.entries.stream().filter(e -> e.id().toString().equals(state.selectedId())).findFirst().orElse(null);
    }
    private Button button(String text, int x, int y, int w, Runnable action) {
        return addRenderableWidget(Button.builder(Component.literal(text), b -> action.run()).bounds(x, y, w, 20).build());
    }
    @Override protected void init() {
        rowWidgets.clear();
        panelWidth = Math.min(680, width - 16);
        left = (width - panelWidth) / 2;
        listWidth = (int)(panelWidth * .54);
        right = left + listWidth + 12;
        int detailWidth = panelWidth - listWidth - 12;
        rowsTop = 116;
        rowsBottom = height - 70;
        pageSize = Math.max(1, (rowsBottom - rowsTop) / 25);
        search = addRenderableWidget(new EditBox(font, left + 44, 29, panelWidth - 44, 20, Component.literal(Text.t("search_accessible"))));
        search.setMaxLength(100);
        search.setHint(Component.literal(Text.t("search_hint")));
        search.setValue(query);
        search.setResponder(value -> { query = value; page = 0; refresh(); });
        addRenderableWidget(new EquipmentButton(left, 54, listWidth, 20, Component.literal(Text.t("equipment_filter", Text.category(category))), category, () -> minecraft.setScreen(new CategoryScreen(this, category))));
        shown=button("", right, 54, detailWidth, () -> { onlySelected = !onlySelected; page=0; rebuildWidgets(); });
        int toolWidth=(listWidth-6)/2;
        button(Text.t(favoritesOnly ? "favorites_only" : "favorites_all"),left,79,toolWidth,()->{favoritesOnly=!favoritesOnly;page=0;rebuildWidgets();})
                .setTooltip(Tooltip.create(Component.literal(Text.t("favorites_tip"))));
        button(Text.t("saved_lists"),left+toolWidth+6,79,listWidth-toolWidth-6,()->minecraft.setScreen(new SavedListsScreen(this)));
        button(Text.t("clear_selected"),right,79,detailWidth,()->minecraft.setScreen(new net.minecraft.client.gui.screens.ConfirmScreen(confirmed->{
            if(confirmed) { catalog.entries.forEach(entry->entry.filter().setEnabled(false)); notice=Text.t("cleared_draft"); }
            minecraft.setScreen(this);
        },Component.literal(Text.t("clear_selected")),Component.literal(Text.t("clear_warning")))));
        backPage = button("<", left, height - 66, 25, () -> { page--; refresh(); });
        nextPage = button(">", left + listWidth - 25, height - 66, 25, () -> { page++; refresh(); });
        detail = button(Text.t("description"), right, rowsTop, detailWidth, () -> { if (selected != null) minecraft.setScreen(new DescriptionScreen(this, selected)); });
        toggle = addRenderableWidget(new StateButton(right, rowsTop + 40, detailWidth, 22, () -> selected != null && selected.filter().isEnabled(), () -> {
            if (selected != null) { selected.filter().setEnabled(!selected.filter().isEnabled()); refresh(); }
        }));
        maximum = button(Text.t("use_maximum"), right, rowsTop + 65, detailWidth, () -> { if (selected != null) { selected.filter().setEnchantmentLevel(selected.maximum()); refresh(); } });
        price = addRenderableWidget(new EditBox(font, right + Math.max(0, detailWidth - 42), rowsTop + 90, 42, 18, Component.literal(Text.t("price_accessible"))));
        price.setMaxLength(2);
        price.setTooltip(Tooltip.create(Component.literal(Text.t("price_tip"))));
        price.setResponder(value -> {
            if (updatingPrice || selected == null) return;
            try { int n = Integer.parseInt(value); if (n >= 1 && n <= 64) { selected.filter().setMaxPrice(n); error = ""; } else error = Text.t("price_range_error"); }
            catch (NumberFormatException e) { error = Text.t("price_required_error"); }
        });
        mode = button(matchAny ? Text.t("match_any") : Text.t("match_all"), right, height - 66, detailWidth, () -> { matchAny = !matchAny; mode.setMessage(Component.literal(matchAny ? Text.t("match_any") : Text.t("match_all"))); });
        mode.setTooltip(Tooltip.create(Component.literal(Text.t("mode_tip", catalog.preserved.size()))));
        int footerWidth = (panelWidth - 12) / 3;
        addRenderableWidget(new LibraryButton(left, height - 25, footerWidth, 20,
                Component.literal(Text.t("save")).withStyle(net.minecraft.ChatFormatting.GREEN), true, true, this::save))
                .setTooltip(Tooltip.create(Component.literal(Text.t("save_tip"))));
        button(Text.t("cancel"), left + footerWidth + 6, height - 25, footerWidth, this::onClose);
        button(Text.t("original_screen"), left + 2 * (footerWidth + 6), height - 25, footerWidth, () -> {
            EnchantBrowser.originalOnce = true;
            minecraft.setScreen(original);
        }).setTooltip(Tooltip.create(Component.literal(Text.t("original_tip"))));
        refresh();
        setInitialFocus(search);
    }
    void chooseCategory(String value) { category = value; page = 0; }
    private void refresh() {
        if (search == null) return;
        for (var widget : rowWidgets) removeWidget(widget);
        rowWidgets.clear();
        String needle = Catalog.normalize(query.strip());
        visible = catalog.entries.stream().filter(e -> (category.equals("Todos") || e.categories().contains(category)) && (!onlySelected || e.filter().isEnabled()) && (!favoritesOnly || library.isFavorite(e.id().toString())) && (Catalog.normalize(e.name() + " " + e.modName() + " " + e.id()).contains(needle))).toList();
        page = Math.max(0, Math.min(page, Math.max(0, (visible.size() - 1) / pageSize)));
        if (selected == null || !visible.contains(selected)) { selected = visible.isEmpty() ? null : visible.get(page * pageSize); detailScroll = 0; }
        for (int i = page * pageSize; i < Math.min(visible.size(), (page + 1) * pageSize); i++) {
            var entry = visible.get(i);
            int y = rowsTop + (i % pageSize) * 25;
            var mark = addRenderableWidget(new StateButton(left, y, 28, 22, () -> entry.filter().isEnabled(), () -> { entry.filter().setEnabled(!entry.filter().isEnabled()); selected = entry; detailScroll = 0; refresh(); }));
            mark.setTooltip(Tooltip.create(Component.literal(Text.t(entry.filter().isEnabled() ? "unselect" : "select", entry.name()))));
            rowWidgets.add(mark);
            String favoriteId=entry.id().toString();
            var favorite=button(library.isFavorite(favoriteId) ? "★" : "☆",left+31,y,25,()->{
                try { library.toggleFavorite(favoriteId); notice=""; } catch(Exception e) { notice=Text.t("library_error"); }
                refresh();
            });
            if(library.isFavorite(favoriteId)) favorite.setMessage(Component.literal("★").withStyle(net.minecraft.ChatFormatting.GOLD));
            favorite.setTooltip(Tooltip.create(Component.literal(Text.t(library.isFavorite(favoriteId) ? "remove_favorite" : "add_favorite",entry.name()))));
            rowWidgets.add(favorite);
            String level = " " + entry.filter().getEnchantmentLevel();
            String label = font.plainSubstrByWidth(entry.name(), listWidth - 80 - font.width(level)) + level;
            var row = addRenderableWidget(new LibraryButton(left + 60, y, listWidth - 60, 20,
                    Component.literal(label), entry == selected, false, () -> { selected = entry; detailScroll = 0; refresh(); }));
            row.setTooltip(Tooltip.create(Component.literal(entry.name() + " — " + entry.modName() + "\n" + Text.categories(entry.categories()))));
            if (entry == selected) row.setMessage(Component.literal(label).withStyle(net.minecraft.ChatFormatting.YELLOW));
            rowWidgets.add(row);
        }
        shown.setMessage(Component.literal(Text.t(onlySelected ? "show_selected_count" : "show_all_count",onlySelected ? catalog.entries.stream().filter(e->e.filter().isEnabled()).count() : catalog.entries.size())));
        backPage.active = page > 0;
        nextPage.active = (page + 1) * pageSize < visible.size();
        toggle.active = maximum.active = detail.active = selected != null;
        maximum.visible = selected != null;
        if (selected != null) {
            detail.setMessage(Component.literal(font.plainSubstrByWidth(selected.name(), panelWidth - listWidth - 28)).withStyle(net.minecraft.ChatFormatting.YELLOW));
            detail.setTooltip(Tooltip.create(Component.literal(Text.t("description_tip", selected.name())+"\n"+Text.t("source_mod",selected.modName()))));
            boolean atMax = selected.filter().getEnchantmentLevel() == selected.maximum();
            maximum.active = !atMax;
            maximum.visible = !atMax;
            maximum.setMessage(Component.literal(Text.t(atMax ? "maximum" : "apply_maximum", selected.maximum())));
        }
        toggle.setMessage(Component.literal(selected != null && selected.filter().isEnabled() ? Text.t("search_yes") : Text.t("search_no")));
        updatingPrice = true;
        price.setValue(selected == null ? "" : Integer.toString(selected.filter().getMaxPrice()));
        updatingPrice = false;
        price.setEditable(selected != null);
        if(selected!=null) price.setTooltip(Tooltip.create(Component.literal(Text.t("price_payment_tip",paymentName()))));
        error = "";
    }
    UserLibrary.SavedList snapshot(String name) {
        var books=catalog.entries.stream().filter(entry->entry.filter().isEnabled()).map(entry->{
            var f=entry.filter();
            return new UserLibrary.Book(entry.id().toString(),f.getEnchantmentLevel(),f.getMaxPrice(),f.getPaymentItemId()==null ? null : f.getPaymentItemId().toString());
        }).toList();
        return new UserLibrary.SavedList(name,matchAny,books);
    }
    void applyList(UserLibrary.SavedList list) {
        var books=new HashMap<String,UserLibrary.Book>();
        list.books().forEach(book->books.put(book.id(),book));
        int loaded=0;
        for(var entry:catalog.entries) {
            var saved=books.get(entry.id().toString());
            entry.filter().setEnabled(saved!=null);
            if(saved!=null) {
                entry.filter().setEnchantmentLevel(saved.level());
                entry.filter().setMaxPrice(saved.maxPrice());
                entry.filter().setPaymentItemId(net.minecraft.resources.Identifier.parse(saved.payment()==null ? "minecraft:emerald" : saved.payment()));
                loaded++;
            }
        }
        matchAny=list.matchAny(); query=""; category="Todos"; onlySelected=true; favoritesOnly=false; page=0; selected=null;
        notice=Text.t("list_loaded",loaded,list.books().size()-loaded);
    }
    private String paymentName() {
        if(selected==null || selected.filter().getPaymentItemId()==null) return net.minecraft.world.item.Items.EMERALD.getDefaultInstance().getHoverName().getString();
        var id=selected.filter().getPaymentItemId();
        return Component.translatableWithFallback("item."+id.getNamespace()+"."+id.getPath(),id.toString()).getString();
    }
    private String compactDescription(String description, int width) {
        // The complete text remains available on the dedicated description screen.
        String text = description.replaceAll("\\s+", " ").strip();
        int limit = Math.max(1, width * 2);
        if (font.width(text) <= limit) return text;
        return font.plainSubstrByWidth(text, Math.max(1, limit - font.width("..."))).stripTrailing() + "...";
    }
    private void save() {
        if (!error.isEmpty()) return;
        try {
            FilterStore.save(catalog.saveEntries(), matchAny);
            minecraft.setScreen(previous);
        } catch (Exception e) {
            error = Text.t("save_error");
            org.slf4j.LoggerFactory.getLogger("du_enchant_browser").error("Unable to save filters", e);
        }
    }
    @Override public void onClose() { minecraft.setScreen(previous); }
    @Override public void removed() {
        new BrowserState(category, query, onlySelected, page, selected == null ? null : selected.id().toString()).save();
        super.removed();
    }
    @Override public boolean isPauseScreen() { return false; }
    @Override public boolean mouseScrolled(double x, double y, double dx, double dy) {
        if (dy == 0) return false;
        if (x >= left && x < right && y >= rowsTop && y < rowsBottom) {
            page += dy < 0 ? 1 : -1; refresh(); return true;
        }
        if (x >= right && x < left + panelWidth && y > rowsTop + 114 && y < rowsBottom) {
            detailScroll = Math.max(0, detailScroll + (dy < 0 ? 20 : -20)); return true;
        }
        return super.mouseScrolled(x, y, dx, dy);
    }
    @Override public void extractBackground(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(g, mouseX, mouseY, partialTick);
        g.fill(left - 7, 5, left + panelWidth + 7, height - 29, 0xF023242C);
        g.centeredText(font, title, width / 2, 12, 0xFFF2D38B);
        g.text(font, Text.t("search"), left, 35, 0xFFD9D6CA);
        int enabled = (int) catalog.entries.stream().filter(e -> e.filter().isEnabled()).count();
        g.text(font, Text.t("count", visible.size(), enabled), left, 104, 0xFFCCD2DF);
        int dw = panelWidth - listWidth - 12;
        g.fill(right - 6, rowsTop, right - 5, rowsBottom, 0xFF575563);
        if (selected == null) {
            g.textWithWordWrap(font, Component.literal(Text.t("empty")), left, rowsTop + 8, listWidth, 0xFFCCD2DF);
        } else {

            g.text(font, font.plainSubstrByWidth(Text.t("source_mod",selected.modName()),dw-4), right, rowsTop+25, 0xFFB5C0CF);
            if (!maximum.visible) g.text(font, Text.t("maximum_level", selected.maximum()), right, rowsTop + 72, 0xFFF2D38B);
            g.text(font, font.plainSubstrByWidth(Text.t("price_payment",paymentName()),dw-48), right, rowsTop + 95, 0xFFCCD2DF);
            int top = rowsTop + 115;
            String text = Text.t("effect") + "\n" + compactDescription(selected.description(), dw - 4) + "\n\n" + Text.t("read_more");
            var lines = font.split(Component.literal(text), dw - 4);
            detailScroll = Math.min(detailScroll, Math.max(0, lines.size() * 11 - (rowsBottom - top)));
            g.enableScissor(right, top, right + dw, Math.max(top, rowsBottom));
            int y = top - detailScroll;
            for (var line : lines) { g.text(font, line, right, y, 0xFFD9D6CA); y += 11; }
            g.disableScissor();
        }
        g.centeredText(font, (page + 1) + " / " + Math.max(1, (visible.size() + pageSize - 1) / pageSize), left + listWidth / 2, height - 59, 0xFFCCD2DF);
        g.centeredText(font, notice.isEmpty() ? Text.t("apply_hint") : notice, width / 2, height - 38, 0xFFB5C0CF);
        if (!error.isEmpty()) g.text(font, error, right, 104, 0xFFFF8888);
    }
}

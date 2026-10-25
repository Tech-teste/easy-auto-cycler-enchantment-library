# Source review — Minecraft 1.21.1 / Addon 1.0.9

This document contains the complete Java sources, build configuration, dependency metadata and translations for convenient browser review. Download the matching sources ZIP from this repository for the original folder structure and Gradle wrapper.

## build.gradle

```groovy
plugins {
    id 'java'
    id 'net.neoforged.moddev' version '2.0.141'
}
version = '1.0.9'
group = 'br.com.du'
base { archivesName = 'easy-auto-cycler-addon-1.21.1' }
java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }
neoForge { version = '21.1.248' }
sourceSets {
    main {
        java.srcDirs = ['src']
        resources.srcDirs = ['resources']
    }
}
dependencies { implementation files('libs/easyautocycler-neoforge-1.21.1-3.1.0.jar') }
tasks.withType(JavaCompile).configureEach { options.encoding = 'UTF-8' }

sourceSets {
    integration {
        java.srcDirs = ['tests']
        compileClasspath += sourceSets.main.output + sourceSets.main.compileClasspath
        runtimeClasspath += sourceSets.main.output + sourceSets.main.runtimeClasspath
    }
}
tasks.register('integrationCheck', JavaExec) {
    dependsOn integrationClasses
    classpath = sourceSets.integration.runtimeClasspath
    mainClass = 'br.com.du.enchantbrowser.IntegrationCheck'
    workingDir = layout.buildDirectory.dir('integration-sandbox').get().asFile
    doFirst { workingDir.mkdirs() }
}
tasks.named('check') { dependsOn integrationCheck }

```

## resources/assets/du_enchant_browser/lang/en_us.json

```json
{
  "du_enchant_browser.title": "Enchantment Library",
  "du_enchant_browser.search_accessible": "Search enchantments",
  "du_enchant_browser.search_hint": "Search by name, mod or ID...",
  "du_enchant_browser.search": "Search",
  "du_enchant_browser.equipment_filter": "Equipment: %s",
  "du_enchant_browser.show_active": "Showing: active",
  "du_enchant_browser.show_all": "Showing: all",
  "du_enchant_browser.description": "Description",
  "du_enchant_browser.use_maximum": "Use maximum level",
  "du_enchant_browser.price_accessible": "Maximum price",
  "du_enchant_browser.price_range_error": "Price: 1 to 64.",
  "du_enchant_browser.price_required_error": "Enter a price.",
  "du_enchant_browser.match_any": "Match any",
  "du_enchant_browser.match_all": "Match all",
  "du_enchant_browser.mode_tip": "Match any: stops when one filter matches. Match all: requires all filters on the same villager. Preserved advanced filters: %s",
  "du_enchant_browser.save": "Save",
  "du_enchant_browser.cancel": "Cancel",
  "du_enchant_browser.original_screen": "Original screen",
  "du_enchant_browser.original_tip": "Opens Auto Cycler's advanced filters. Unsaved changes on this screen will be discarded.",
  "du_enchant_browser.unselect": "Deselect %s",
  "du_enchant_browser.select": "Search for %s",
  "du_enchant_browser.description_tip": "Read description: %s",
  "du_enchant_browser.maximum": "Maximum: %s",
  "du_enchant_browser.apply_maximum": "Use maximum: %s",
  "du_enchant_browser.search_yes": "Search: yes ✓",
  "du_enchant_browser.search_no": "Search: no",
  "du_enchant_browser.save_error": "Could not save; check the log.",
  "du_enchant_browser.count": "%s books | Total active: %s",
  "du_enchant_browser.empty": "No results. Change the category or clear the search.",
  "du_enchant_browser.maximum_level": "Maximum level: %s",
  "du_enchant_browser.price": "Max. price",
  "du_enchant_browser.effect": "Effect",
  "du_enchant_browser.equipment": "Equipment",
  "du_enchant_browser.read_more": "Click the name above to read more.",
  "du_enchant_browser.category_title": "Choose equipment",
  "du_enchant_browser.back": "Back",
  "du_enchant_browser.category_hint": "Choose the equipment for this book.",
  "du_enchant_browser.trade_maximum": "Standard maximum trade level: %s",
  "du_enchant_browser.technical_details": "Mod: %s\nID: %s\n\nThe list follows the enchantments allowed in this world's trades, excluding curses. Custom server rules may change availability.\n\nNames and descriptions use translations supplied by the installed mods.",
  "du_enchant_browser.back_to_list": "Back to list",
  "du_enchant_browser.hide_details": "Hide details",
  "du_enchant_browser.show_details": "Technical details",
  "du_enchant_browser.active": "Active",
  "du_enchant_browser.inactive": "Inactive",
  "du_enchant_browser.disable": "Active — disable",
  "du_enchant_browser.enable": "Inactive — enable",
  "du_enchant_browser.active_search": "Active in search",
  "du_enchant_browser.inactive_search": "Inactive in search",
  "du_enchant_browser.missing_description": "This mod did not provide a description. Check the book in JEI or the mod's guide.",
  "du_enchant_browser.category.all": "All",
  "du_enchant_browser.category.sword": "Sword",
  "du_enchant_browser.category.pickaxe": "Pickaxe",
  "du_enchant_browser.category.axe": "Axe",
  "du_enchant_browser.category.shovel": "Shovel",
  "du_enchant_browser.category.hoe": "Hoe",
  "du_enchant_browser.category.shears": "Shears",
  "du_enchant_browser.category.shield": "Shield",
  "du_enchant_browser.category.helmet": "Helmet",
  "du_enchant_browser.category.chestplate": "Chestplate",
  "du_enchant_browser.category.leggings": "Leggings",
  "du_enchant_browser.category.boots": "Boots",
  "du_enchant_browser.category.elytra": "Elytra",
  "du_enchant_browser.category.bow": "Bow",
  "du_enchant_browser.category.crossbow": "Crossbow",
  "du_enchant_browser.category.trident": "Trident",
  "du_enchant_browser.category.mace": "Mace",
  "du_enchant_browser.category.fishing": "Fishing",
  "du_enchant_browser.category.other": "Other"
}

```

## resources/assets/du_enchant_browser/lang/pt_br.json

```json
{
  "du_enchant_browser.title": "Biblioteca de encantamentos",
  "du_enchant_browser.search_accessible": "Buscar encantamento",
  "du_enchant_browser.search_hint": "Buscar por nome, mod ou ID...",
  "du_enchant_browser.search": "Buscar",
  "du_enchant_browser.equipment_filter": "Equipamento: %s",
  "du_enchant_browser.show_active": "Exibindo: ativos",
  "du_enchant_browser.show_all": "Exibindo: todos",
  "du_enchant_browser.description": "Descrição",
  "du_enchant_browser.use_maximum": "Usar nível máximo",
  "du_enchant_browser.price_accessible": "Preço máximo",
  "du_enchant_browser.price_range_error": "Preço: 1 a 64.",
  "du_enchant_browser.price_required_error": "Informe o preço.",
  "du_enchant_browser.match_any": "Qualquer marcado",
  "du_enchant_browser.match_all": "Todos juntos",
  "du_enchant_browser.mode_tip": "Qualquer marcado: para ao achar um dos filtros. Todos juntos: exige todos no mesmo aldeão. Filtros avançados preservados: %s",
  "du_enchant_browser.save": "Salvar",
  "du_enchant_browser.cancel": "Cancelar",
  "du_enchant_browser.original_screen": "Tela original",
  "du_enchant_browser.original_tip": "Abre os filtros avançados do Auto Cycler. Alterações não salvas nesta tela serão descartadas.",
  "du_enchant_browser.unselect": "Desmarcar %s",
  "du_enchant_browser.select": "Procurar %s",
  "du_enchant_browser.description_tip": "Ler descrição: %s",
  "du_enchant_browser.maximum": "Máximo: %s",
  "du_enchant_browser.apply_maximum": "Usar máximo: %s",
  "du_enchant_browser.search_yes": "Procurar: sim ✓",
  "du_enchant_browser.search_no": "Procurar: não",
  "du_enchant_browser.save_error": "Falha ao salvar; veja o log.",
  "du_enchant_browser.count": "%s livros | Ativos no total: %s",
  "du_enchant_browser.empty": "Nenhum resultado. Troque a categoria ou limpe a busca.",
  "du_enchant_browser.maximum_level": "Nível máximo: %s",
  "du_enchant_browser.price": "Preço máx.",
  "du_enchant_browser.effect": "Efeito",
  "du_enchant_browser.equipment": "Equipamento",
  "du_enchant_browser.read_more": "Clique no nome acima para ler mais.",
  "du_enchant_browser.category_title": "Escolha o equipamento",
  "du_enchant_browser.back": "Voltar",
  "du_enchant_browser.category_hint": "Escolha onde pretende usar o livro.",
  "du_enchant_browser.trade_maximum": "Máximo padrão de troca: %s",
  "du_enchant_browser.technical_details": "Mod: %s\nID: %s\n\nA lista segue os encantamentos permitidos nas trocas do mundo, sem maldições. Regras especiais do servidor podem alterar a disponibilidade.\n\nNomes e descrições usam as traduções fornecidas pelos mods.",
  "du_enchant_browser.back_to_list": "Voltar à lista",
  "du_enchant_browser.hide_details": "Ocultar dados",
  "du_enchant_browser.show_details": "Dados técnicos",
  "du_enchant_browser.active": "Ativo",
  "du_enchant_browser.inactive": "Inativo",
  "du_enchant_browser.disable": "Ativo — desativar",
  "du_enchant_browser.enable": "Inativo — ativar",
  "du_enchant_browser.active_search": "Ativo na busca",
  "du_enchant_browser.inactive_search": "Inativo na busca",
  "du_enchant_browser.missing_description": "Este mod não forneceu uma descrição. Consulte o livro no JEI ou o guia do mod.",
  "du_enchant_browser.category.all": "Todos",
  "du_enchant_browser.category.sword": "Espada",
  "du_enchant_browser.category.pickaxe": "Picareta",
  "du_enchant_browser.category.axe": "Machado",
  "du_enchant_browser.category.shovel": "Pá",
  "du_enchant_browser.category.hoe": "Enxada",
  "du_enchant_browser.category.shears": "Tesoura",
  "du_enchant_browser.category.shield": "Escudo",
  "du_enchant_browser.category.helmet": "Capacete",
  "du_enchant_browser.category.chestplate": "Peitoral",
  "du_enchant_browser.category.leggings": "Calças",
  "du_enchant_browser.category.boots": "Botas",
  "du_enchant_browser.category.elytra": "Élitro",
  "du_enchant_browser.category.bow": "Arco",
  "du_enchant_browser.category.crossbow": "Besta",
  "du_enchant_browser.category.trident": "Tridente",
  "du_enchant_browser.category.mace": "Maça",
  "du_enchant_browser.category.fishing": "Pesca",
  "du_enchant_browser.category.other": "Outros"
}

```

## resources/META-INF/neoforge.mods.toml

```toml
modLoader="javafml"
loaderVersion="[4,)"
license="MIT"
[[mods]]
modId="du_enchant_browser"
version="1.0.9"
displayName="Easy Auto Cycler Addon - Enchantment Library / Biblioteca de Encantamentos"
authors="Personal project, built with OpenAI Codex"
logoFile="logo.png"
description='''Client-side enchantment search, equipment categories and trade filters for Easy Auto Cycler 3.1.0. English and Brazilian Portuguese interface. Biblioteca de encantamentos com busca, categorias e descricoes.'''
[[dependencies.du_enchant_browser]]
modId="neoforge"
type="required"
versionRange="[21.1,)"
ordering="NONE"
side="CLIENT"
[[dependencies.du_enchant_browser]]
modId="minecraft"
type="required"
versionRange="[1.21.1,1.21.2)"
ordering="NONE"
side="CLIENT"
[[dependencies.du_enchant_browser]]
modId="easyautocycler"
type="required"
versionRange="[3.1.0,3.1.1)"
ordering="AFTER"
side="CLIENT"

[[dependencies.du_enchant_browser]]
modId="easy_villagers"
type="required"
versionRange="[1.1.42,)"
ordering="AFTER"
side="CLIENT"

```

## settings.gradle

```groovy
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = 'https://maven.neoforged.net/releases' }
    }
}
rootProject.name = 'du-enchant-browser'

```

## src/br/com/du/enchantbrowser/BrowserScreen.java

```java
package br.com.du.enchantbrowser;

import java.util.*;
import com.uncraftbar.easyautocycler.AutomationManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

final class BrowserScreen extends Screen {
    private final Screen previous, original;
    private final Catalog catalog = new Catalog();
    private final List<Button> rowWidgets = new ArrayList<>();
    private List<Catalog.Entry> visible = List.of();
    private Catalog.Entry selected;
    private EditBox search, price;
    private Button toggle, maximum, backPage, nextPage, detail, mode;
    private String query = "", category = "Todos";
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
        rowsTop = 91;
        rowsBottom = height - 56;
        pageSize = Math.max(1, (rowsBottom - rowsTop) / 25);
        search = addRenderableWidget(new EditBox(font, left + 44, 29, panelWidth - 44, 20, Component.literal(Text.t("search_accessible"))));
        search.setMaxLength(100);
        search.setHint(Component.literal(Text.t("search_hint")));
        search.setValue(query);
        search.setResponder(value -> { query = value; page = 0; refresh(); });
        addRenderableWidget(new EquipmentButton(left, 54, listWidth, 20, Component.literal(Text.t("equipment_filter", Text.category(category))), category, () -> minecraft.setScreen(new CategoryScreen(this, category))));
        button(onlySelected ? Text.t("show_active") : Text.t("show_all"), right, 54, detailWidth, () -> { onlySelected = !onlySelected; rebuildWidgets(); });
        backPage = button("<", left, height - 52, 25, () -> { page--; refresh(); });
        nextPage = button(">", left + listWidth - 25, height - 52, 25, () -> { page++; refresh(); });
        detail = button(Text.t("description"), right, rowsTop, detailWidth, () -> { if (selected != null) minecraft.setScreen(new DescriptionScreen(this, selected)); });
        toggle = addRenderableWidget(new StateButton(right, rowsTop + 30, detailWidth, 22, () -> selected != null && selected.filter().isEnabled(), () -> {
            if (selected != null) { selected.filter().setEnabled(!selected.filter().isEnabled()); refresh(); }
        }));
        maximum = button(Text.t("use_maximum"), right, rowsTop + 55, detailWidth, () -> { if (selected != null) { selected.filter().setEnchantmentLevel(selected.maximum()); refresh(); } });
        price = addRenderableWidget(new EditBox(font, right + Math.max(0, detailWidth - 42), rowsTop + 82, 42, 18, Component.literal(Text.t("price_accessible"))));
        price.setMaxLength(2);
        price.setFilter(value -> value.isEmpty() || value.matches("[0-9]{1,2}"));
        price.setResponder(value -> {
            if (updatingPrice || selected == null) return;
            try { int n = Integer.parseInt(value); if (n >= 1 && n <= 64) { selected.filter().setMaxPrice(n); error = ""; } else error = Text.t("price_range_error"); }
            catch (NumberFormatException e) { error = Text.t("price_required_error"); }
        });
        mode = button(matchAny ? Text.t("match_any") : Text.t("match_all"), right, height - 52, detailWidth, () -> { matchAny = !matchAny; mode.setMessage(Component.literal(matchAny ? Text.t("match_any") : Text.t("match_all"))); });
        mode.setTooltip(Tooltip.create(Component.literal(Text.t("mode_tip", catalog.preserved.size()))));
        int footerWidth = (panelWidth - 12) / 3;
        button(Text.t("save"), left, height - 25, footerWidth, this::save);
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
        visible = catalog.entries.stream().filter(e -> (category.equals("Todos") || e.categories().contains(category)) && (!onlySelected || e.filter().isEnabled()) && (Catalog.normalize(e.name() + " " + e.id()).contains(needle))).toList();
        page = Math.max(0, Math.min(page, Math.max(0, (visible.size() - 1) / pageSize)));
        if (selected == null || !visible.contains(selected)) { selected = visible.isEmpty() ? null : visible.get(page * pageSize); detailScroll = 0; }
        for (int i = page * pageSize; i < Math.min(visible.size(), (page + 1) * pageSize); i++) {
            var entry = visible.get(i);
            int y = rowsTop + (i % pageSize) * 25;
            var mark = addRenderableWidget(new StateButton(left, y, 36, 22, () -> entry.filter().isEnabled(), () -> { entry.filter().setEnabled(!entry.filter().isEnabled()); selected = entry; detailScroll = 0; refresh(); }));
            mark.setTooltip(Tooltip.create(Component.literal(Text.t(entry.filter().isEnabled() ? "unselect" : "select", entry.name()))));
            rowWidgets.add(mark);
            String level = " " + entry.filter().getEnchantmentLevel();
            String label = font.plainSubstrByWidth(entry.name(), listWidth - 60 - font.width(level)) + level;
            var row = button(label, left + 40, y, listWidth - 40, () -> { selected = entry; detailScroll = 0; refresh(); });
            row.setTooltip(Tooltip.create(Component.literal(entry.name() + " — " + Text.categories(entry.categories()))));
            if (entry == selected) row.setMessage(Component.literal(label).withStyle(net.minecraft.ChatFormatting.YELLOW));
            rowWidgets.add(row);
        }
        backPage.active = page > 0;
        nextPage.active = (page + 1) * pageSize < visible.size();
        toggle.active = maximum.active = detail.active = selected != null;
        maximum.visible = selected != null;
        if (selected != null) {
            detail.setMessage(Component.literal(font.plainSubstrByWidth(selected.name(), panelWidth - listWidth - 28)).withStyle(net.minecraft.ChatFormatting.YELLOW));
            detail.setTooltip(Tooltip.create(Component.literal(Text.t("description_tip", selected.name()))));
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
        error = "";
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
        if (x >= right && x < left + panelWidth && y > rowsTop + 106 && y < rowsBottom) {
            detailScroll = Math.max(0, detailScroll + (dy < 0 ? 20 : -20)); return true;
        }
        return super.mouseScrolled(x, y, dx, dy);
    }
    @Override public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(g, mouseX, mouseY, partialTick);
        g.fill(left - 7, 5, left + panelWidth + 7, height - 29, 0xF023242C);
        g.drawCenteredString(font, title, width / 2, 12, 0xF2D38B);
        g.drawString(font, Text.t("search"), left, 35, 0xD9D6CA);
        int enabled = (int) catalog.entries.stream().filter(e -> e.filter().isEnabled()).count();
        g.drawString(font, Text.t("count", visible.size(), enabled), left, 79, 0xCCD2DF);
        int dw = panelWidth - listWidth - 12;
        g.fill(right - 6, rowsTop, right - 5, rowsBottom, 0xFF575563);
        if (selected == null) {
            g.drawWordWrap(font, Component.literal(Text.t("empty")), left, rowsTop + 8, listWidth, 0xCCD2DF);
        } else {

            if (!maximum.visible) g.drawString(font, Text.t("maximum_level", selected.maximum()), right, rowsTop + 62, 0xF2D38B);
            g.drawString(font, Text.t("price"), right, rowsTop + 87, 0xCCD2DF);
            int top = rowsTop + 107;
            String text = Text.t("effect") + "\n" + selected.description() + "\n\n" + Text.t("equipment") + "\n" + Text.categories(selected.categories()) + "\n\n" + Text.t("read_more");
            var lines = font.split(Component.literal(text), dw - 4);
            detailScroll = Math.min(detailScroll, Math.max(0, lines.size() * 11 - (rowsBottom - top)));
            g.enableScissor(right, top, right + dw, Math.max(top, rowsBottom));
            int y = top - detailScroll;
            for (var line : lines) { g.drawString(font, line, right, y, 0xD9D6CA); y += 11; }
            g.disableScissor();
        }
        g.drawCenteredString(font, (page + 1) + " / " + Math.max(1, (visible.size() + pageSize - 1) / pageSize), left + listWidth / 2, height - 45, 0xCCD2DF);
        if (!error.isEmpty()) g.drawString(font, error, right, 79, 0xFF8888);
    }
}

```

## src/br/com/du/enchantbrowser/BrowserState.java

```java
package br.com.du.enchantbrowser;

import java.nio.file.*;
import java.io.IOException;
import java.util.Arrays;
import com.google.gson.Gson;

/** Navigation only: never stores pending edits to Auto Cycler filters. */
record BrowserState(String category, String query, boolean onlySelected, int page, String selectedId) {
    private static final Path FILE = Path.of("config/du-enchant-browser-view.json");
    private static final Gson JSON = new Gson();

    BrowserState {
        if (category == null || !Arrays.asList(Catalog.CATEGORIES).contains(category)) category = "Todos";
        if (query == null) query = "";
        if (query.length() > 100) query = query.substring(0, 100);
        page = Math.max(0, page);
    }

    static BrowserState load() {
        try {
            if (Files.exists(FILE)) {
                var state = JSON.fromJson(Files.readString(FILE), BrowserState.class);
                if (state != null) return state;
            }
        } catch (IOException | RuntimeException e) {
            org.slf4j.LoggerFactory.getLogger("du_enchant_browser").warn("Could not restore browser navigation", e);
        }
        return new BrowserState("Todos", "", false, 0, null);
    }

    void save() {
        Path temporary = FILE.resolveSibling(FILE.getFileName() + ".tmp");
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(temporary, JSON.toJson(this));
            try {
                Files.move(temporary, FILE, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporary, FILE, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | RuntimeException e) {
            org.slf4j.LoggerFactory.getLogger("du_enchant_browser").warn("Could not save browser navigation", e);
        }
    }
}

```

## src/br/com/du/enchantbrowser/Catalog.java

```java
package br.com.du.enchantbrowser;

import java.text.Normalizer;
import java.util.*;
import com.uncraftbar.easyautocycler.AutomationManager;
import com.uncraftbar.easyautocycler.filter.FilterEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;

final class Catalog {
    static final String[] CATEGORIES = {"Todos", "Espada", "Picareta", "Machado", "Pá", "Enxada", "Tesoura", "Escudo", "Capacete", "Peitoral", "Calças", "Botas", "Élitro", "Arco", "Besta", "Tridente", "Maça", "Pesca", "Outros"};
    record Entry(ResourceLocation id, String name, String description, int maximum, Set<String> categories, FilterEntry filter) {}
    final List<Entry> entries = new ArrayList<>();
    final List<FilterEntry> preserved = new ArrayList<>();
    final List<FilterEntry> originals;
    int excluded;
    Catalog() {
        originals = AutomationManager.INSTANCE.getFilterEntries().stream().map(FilterEntry::new).toList();
        var registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        var tradeable = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.withDefaultNamespace("tradeable"));
        var curse = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.withDefaultNamespace("curse"));
        Set<FilterEntry> consumed = Collections.newSetFromMap(new IdentityHashMap<>());
        registry.holders().forEach(holder -> {
            var id = holder.key().location();
            if (!holder.is(tradeable) || holder.is(curse) || id.getPath().contains("curse")) {
                excluded++;
                return;
            }
            var enchantment = holder.value();
            FilterEntry filter = originals.stream().filter(f -> id.equals(f.getEnchantmentId()) && ResourceLocation.withDefaultNamespace("enchanted_book").equals(f.getItemId())).findFirst().orElse(null);
            if (filter == null) {
                filter = new FilterEntry();
                filter.setEnabled(false);
                filter.setItemId(ResourceLocation.withDefaultNamespace("enchanted_book"));
                filter.setMinCount(1);
                filter.setEnchantmentId(id);
                filter.setEnchantmentLevel(enchantment.getMaxLevel());
                filter.setPaymentItemId(ResourceLocation.withDefaultNamespace("emerald"));
                filter.setMaxPrice(64);
            } else consumed.add(filter);
            String key = "enchantment." + id.getNamespace() + "." + id.getPath() + ".desc";
            String description = I18n.exists(key) ? I18n.get(key) : Text.t("missing_description");
            entries.add(new Entry(id, enchantment.description().getString(), description, enchantment.getMaxLevel(), categories(enchantment), filter));
        });
        // Preserve custom item filters and duplicates; unavailable enchantments must not keep cycling forever.
        for (var original : originals) {
            if (consumed.contains(original)) continue;
            var id = original.getEnchantmentId();
            if (id != null) {
                var holder = registry.getHolder(id);
                if (holder.isEmpty() || !holder.get().is(tradeable) || holder.get().is(curse) || id.getPath().contains("curse")) continue;
            }
            preserved.add(original);
        }
        entries.sort(Comparator.comparing(e -> normalize(e.name())));
    }
    static String normalize(String value) { return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT); }
    static Set<String> categories(Enchantment e) {
        Set<String> result = new LinkedHashSet<>();
        Item[][] probes = {{Items.DIAMOND_SWORD}, {Items.DIAMOND_PICKAXE}, {Items.DIAMOND_AXE}, {Items.DIAMOND_SHOVEL}, {Items.DIAMOND_HOE}, {Items.SHEARS}, {Items.SHIELD}, {Items.DIAMOND_HELMET, Items.TURTLE_HELMET}, {Items.DIAMOND_CHESTPLATE}, {Items.DIAMOND_LEGGINGS}, {Items.DIAMOND_BOOTS}, {Items.ELYTRA}, {Items.BOW}, {Items.CROSSBOW}, {Items.TRIDENT}, {Items.MACE}, {Items.FISHING_ROD}};
        for (int i = 0; i < probes.length; i++) for (Item item : probes[i]) if (e.isSupportedItem(item.getDefaultInstance())) { result.add(CATEGORIES[i + 1]); break; }
        if (result.isEmpty()) result.add("Outros");
        return result;
    }
    List<FilterEntry> saveEntries() {
        List<FilterEntry> result = new ArrayList<>(preserved);
        entries.forEach(e -> result.add(new FilterEntry(e.filter())));
        return result;
    }
}

```

## src/br/com/du/enchantbrowser/CategoryScreen.java

```java
package br.com.du.enchantbrowser;
import net.minecraft.client.gui.GuiGraphics;
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
    @Override public void renderBackground(GuiGraphics g, int x, int y, float dt) {
        super.renderBackground(g, x, y, dt);
        g.fill(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xF023242C);
        g.drawCenteredString(font, title, width / 2, panelTop + 12, 0xF2D38B);
        g.drawCenteredString(font, Text.t("category_hint"), width / 2, panelTop + 26, 0xCCD2DF);
    }
    @Override public void onClose() { minecraft.setScreen(parent); }
    @Override public boolean isPauseScreen() { return false; }
}

```

## src/br/com/du/enchantbrowser/DescriptionScreen.java

```java
package br.com.du.enchantbrowser;

import net.minecraft.client.gui.GuiGraphics;
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
    @Override public void renderBackground(GuiGraphics g, int x, int y, float dt) {
        super.renderBackground(g, x, y, dt);
        int w = contentWidth, l = (width - w) / 2;
        g.fill(l - 12, panelTop, l + w + 12, panelTop + panelHeight - 30, 0xF023242C);
        g.drawCenteredString(font, title, width / 2, panelTop + 12, 0xF2D38B);
        var lines = font.split(Component.literal(content()), w);
        scroll = Math.min(scroll, Math.max(0, lines.size() * 12 - (panelHeight - 78)));
        g.enableScissor(l, panelTop + 36, l + w, panelTop + panelHeight - 38);
        int top = panelTop + 36 - scroll;
        for (var line : lines) { g.drawString(font, line, l, top, 0xD9D6CA); top += 12; }
        g.disableScissor();
    }
    @Override public boolean mouseScrolled(double x, double y, double dx, double dy) { scroll = Math.max(0, scroll + (dy < 0 ? 24 : -24)); return true; }
    @Override public void onClose() { minecraft.setScreen(parent); }
    @Override public boolean isPauseScreen() { return false; }
}

```

## src/br/com/du/enchantbrowser/EnchantBrowser.java

```java
package br.com.du.enchantbrowser;

import net.neoforged.fml.common.Mod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import com.uncraftbar.easyautocycler.gui.ConfigScreen;

@Mod(value = "du_enchant_browser", dist = Dist.CLIENT)
public final class EnchantBrowser {
    static boolean originalOnce;
    public EnchantBrowser() { NeoForge.EVENT_BUS.addListener(EnchantBrowser::opening); }
    private static void opening(ScreenEvent.Opening event) {
        if (!(event.getNewScreen() instanceof ConfigScreen original)) return;
        if (originalOnce) { originalOnce = false; return; }
        if (Minecraft.getInstance().level == null) return;
        try {
            var field = ConfigScreen.class.getDeclaredField("previousScreen");
            field.setAccessible(true);
            Screen previous = (Screen) field.get(original);
            event.setNewScreen(new BrowserScreen(previous, original));
        } catch (ReflectiveOperationException | RuntimeException e) {
            org.slf4j.LoggerFactory.getLogger("du_enchant_browser").error("Could not open enchantment browser; keeping original screen", e);
        }
    }
}

```

## src/br/com/du/enchantbrowser/EquipmentButton.java

```java
package br.com.du.enchantbrowser;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
final class EquipmentButton extends Button {
    private final ItemStack icon;
    EquipmentButton(int x,int y,int w,int h, Component text,String category,Runnable action) {
        super(x,y,w,h,text,b -> action.run(),DEFAULT_NARRATION);
        icon = switch(category) {
            case "Espada" -> Items.DIAMOND_SWORD.getDefaultInstance();
            case "Picareta" -> Items.DIAMOND_PICKAXE.getDefaultInstance();
            case "Machado" -> Items.DIAMOND_AXE.getDefaultInstance();
            case "Pá" -> Items.DIAMOND_SHOVEL.getDefaultInstance();
            case "Enxada" -> Items.DIAMOND_HOE.getDefaultInstance();
            case "Tesoura" -> Items.SHEARS.getDefaultInstance();
            case "Escudo" -> Items.SHIELD.getDefaultInstance();
            case "Capacete" -> Items.DIAMOND_HELMET.getDefaultInstance();
            case "Peitoral" -> Items.DIAMOND_CHESTPLATE.getDefaultInstance();
            case "Calças" -> Items.DIAMOND_LEGGINGS.getDefaultInstance();
            case "Botas" -> Items.DIAMOND_BOOTS.getDefaultInstance();
            case "Élitro" -> Items.ELYTRA.getDefaultInstance();
            case "Arco" -> Items.BOW.getDefaultInstance();
            case "Besta" -> Items.CROSSBOW.getDefaultInstance();
            case "Tridente" -> Items.TRIDENT.getDefaultInstance();
            case "Maça" -> Items.MACE.getDefaultInstance();
            case "Pesca" -> Items.FISHING_ROD.getDefaultInstance();
            case "Todos" -> Items.ENCHANTED_BOOK.getDefaultInstance();
            default -> Items.CHEST.getDefaultInstance();
        };
    }
    @Override protected void renderWidget(GuiGraphics g,int x,int y,float dt) {
        super.renderWidget(g,x,y,dt);
        g.renderItem(icon,getX()+4,getY()+(getHeight()-16)/2);
    }
    @Override public void renderString(GuiGraphics g, Font font, int color) {
        g.drawString(font,Component.literal(font.plainSubstrByWidth(getMessage().getString(),getWidth()-29)).withStyle(getMessage().getStyle()),getX()+25,getY()+(getHeight()-8)/2,color);
    }
}

```

## src/br/com/du/enchantbrowser/FilterStore.java

```java
package br.com.du.enchantbrowser;

import java.nio.file.*;
import java.util.*;
import com.google.gson.*;
import com.uncraftbar.easyautocycler.AutomationManager;
import com.uncraftbar.easyautocycler.filter.FilterEntry;

final class FilterStore {
    static void save(List<FilterEntry> entries, boolean matchAny) throws Exception {
        Path file = Path.of("config/easyautocycler-filters.json");
        byte[] before = Files.exists(file) ? Files.readAllBytes(file) : null;
        if (before != null) {
            Path backups = Path.of("config/du-enchant-browser-backups");
            Files.createDirectories(backups);
            Files.write(backups.resolve("filters-" + System.currentTimeMillis() + ".json"), before, StandardOpenOption.CREATE_NEW);
        }
        var manager = AutomationManager.INSTANCE;
        var previous = manager.getFilterEntries().stream().map(FilterEntry::new).toList();
        boolean previousMode = manager.isMatchAny();
        try {
            manager.setFilterEntries(entries);
            manager.setMatchAny(matchAny);
            manager.saveFiltersToConfig();
            verify(Files.readString(file), entries, matchAny);
        } catch (Exception e) {
            manager.setFilterEntries(new ArrayList<>(previous));
            manager.setMatchAny(previousMode);
            if (before != null) Files.write(file, before);
            throw e;
        }
    }
    static void verify(String json, List<FilterEntry> entries, boolean matchAny) {
        var root = JsonParser.parseString(json).getAsJsonObject();
        var filters = root.getAsJsonArray("filters");
        if (root.get("matchAny").getAsBoolean() != matchAny || filters.size() != entries.size()) throw new IllegalStateException("Filter count or mode mismatch");
        for (int i = 0; i < entries.size(); i++) {
            var f = entries.get(i); var stored = filters.get(i).getAsJsonObject();
            if (stored.get("enabled").getAsBoolean() != f.isEnabled()
                || stored.get("minCount").getAsInt() != f.getMinCount()
                || stored.get("enchantmentLevel").getAsInt() != f.getEnchantmentLevel()
                || stored.get("maxPrice").getAsInt() != f.getMaxPrice()
                || !Objects.equals(value(stored, "itemId"), f.getItemId() == null ? null : f.getItemId().toString())
                || !Objects.equals(value(stored, "enchantmentId"), f.getEnchantmentId() == null ? null : f.getEnchantmentId().toString())
                || !Objects.equals(value(stored, "paymentItemId"), f.getPaymentItemId() == null ? null : f.getPaymentItemId().toString())) throw new IllegalStateException("Filter " + i + " mismatch");
        }
    }
    private static String value(JsonObject object, String name) {
        var value = object.get(name); return value == null || value.isJsonNull() || value.getAsString().isEmpty() ? null : value.getAsString();
    }
}

```

## src/br/com/du/enchantbrowser/StateButton.java

```java
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

```

## src/br/com/du/enchantbrowser/Text.java

```java
package br.com.du.enchantbrowser;

import java.util.Collection;
import java.util.stream.Collectors;
import net.minecraft.client.resources.language.I18n;

/** Uses Minecraft's selected language; category IDs stay stable across language changes. */
final class Text {
    static String t(String key, Object... args) { return I18n.get("du_enchant_browser." + key, args); }
    static String category(String category) {
        return switch (category) {
            case "Todos" -> t("category.all");
            case "Espada" -> t("category.sword");
            case "Picareta" -> t("category.pickaxe");
            case "Machado" -> t("category.axe");
            case "Pá" -> t("category.shovel");
            case "Enxada" -> t("category.hoe");
            case "Tesoura" -> t("category.shears");
            case "Escudo" -> t("category.shield");
            case "Capacete" -> t("category.helmet");
            case "Peitoral" -> t("category.chestplate");
            case "Calças" -> t("category.leggings");
            case "Botas" -> t("category.boots");
            case "Élitro" -> t("category.elytra");
            case "Arco" -> t("category.bow");
            case "Besta" -> t("category.crossbow");
            case "Tridente" -> t("category.trident");
            case "Maça" -> t("category.mace");
            case "Pesca" -> t("category.fishing");
            case "Outros" -> t("category.other");
            default -> t("category.other");
        };
    }
    static String categories(Collection<String> categories) {
        return categories.stream().map(Text::category).collect(Collectors.joining(", "));
    }
}

```

## tests/IntegrationCheck.java

```java
package br.com.du.enchantbrowser;
import java.nio.file.*;
import java.util.*;
import com.uncraftbar.easyautocycler.filter.FilterEntry;
import com.uncraftbar.easyautocycler.config.FilterConfig;
import net.minecraft.resources.ResourceLocation;
public final class IntegrationCheck {
    private static void checkLanguages() throws Exception {
        java.util.Set<String> keys = null;
        var setLanguage = net.minecraft.client.resources.language.I18n.class.getDeclaredMethod("setLanguage", net.minecraft.locale.Language.class);
        setLanguage.setAccessible(true);
        for (String locale : List.of("pt_br", "en_us")) {
            var translations = new java.util.HashMap<String, String>();
            try (var stream = IntegrationCheck.class.getResourceAsStream("/assets/du_enchant_browser/lang/" + locale + ".json")) {
                if (stream == null) throw new AssertionError("Missing language resource: " + locale);
                net.minecraft.locale.Language.loadFromJson(stream, translations::put);
            }
            if (keys == null) keys = new java.util.HashSet<>(translations.keySet());
            else if (!keys.equals(translations.keySet())) throw new AssertionError("Language key mismatch");
            var language = new net.minecraft.locale.Language() {
                public String getOrDefault(String key, String fallback) { return translations.getOrDefault(key, fallback); }
                public boolean has(String key) { return translations.containsKey(key); }
                public boolean isDefaultRightToLeft() { return false; }
                public net.minecraft.util.FormattedCharSequence getVisualOrder(net.minecraft.network.chat.FormattedText text) { return net.minecraft.util.FormattedCharSequence.EMPTY; }
            };
            setLanguage.invoke(null, language);
            boolean portuguese = locale.equals("pt_br");
            if (!Text.t("title").equals(portuguese ? "Biblioteca de encantamentos" : "Enchantment Library")) throw new AssertionError("Title did not follow language");
            if (!Text.category("Picareta").equals(portuguese ? "Picareta" : "Pickaxe")) throw new AssertionError("Category did not follow language");
            if (!Text.t("count", 12, 3).equals(portuguese ? "12 livros | Ativos no total: 3" : "12 books | Total active: 3")) throw new AssertionError("Counter formatting failed");
            if (!Text.t("equipment_filter", Text.category("Peitoral")).equals(portuguese ? "Equipamento: Peitoral" : "Equipment: Chestplate")) throw new AssertionError("Category label formatting failed");
            for (String category : Catalog.CATEGORIES) if (Text.category(category).startsWith("du_enchant_browser.")) throw new AssertionError("Missing category translation");
            if (Text.t("technical_details", "example", "example:test").contains("%s")) throw new AssertionError("Unresolved technical detail arguments");
            var saved = new BrowserState("Peitoral", "test", true, 1, "minecraft:protection");
            saved.save();
            if (!saved.equals(BrowserState.load())) throw new AssertionError("Language changed stored category/navigation");
        }
        System.out.println("PASS: Minecraft language loading; pt-BR/en-US titles, categories, counters, tooltips and navigation compatibility.");
    }

    public static void main(String[] args) throws Exception {
        checkLanguages();
        var navigationFile = Path.of("config/du-enchant-browser-view.json");
        Files.deleteIfExists(navigationFile);
        if (!BrowserState.load().category().equals("Todos")) throw new AssertionError("Default navigation failed");
        var navigation = new BrowserState("Peitoral", "proteção", true, 3, "minecraft:protection");
        navigation.save();
        if (!navigation.equals(BrowserState.load())) throw new AssertionError("Navigation was not restored");
        Files.writeString(navigationFile, "{broken");
        if (!BrowserState.load().category().equals("Todos")) throw new AssertionError("Corrupt navigation fallback failed");
        Files.writeString(navigationFile, "{\"category\":\"Removed category\",\"page\":-10}");
        var fallback = BrowserState.load();
        if (!fallback.category().equals("Todos") || !fallback.query().isEmpty() || fallback.page() != 0) throw new AssertionError("Invalid navigation fallback failed");
        navigation.save();
        // Screen.render owns the one background pass and then draws widgets.
        // Overriding render and calling super after custom text reapplies the blur.
        for (String name : List.of("BrowserScreen", "CategoryScreen", "DescriptionScreen")) {
            var screen = Class.forName("br.com.du.enchantbrowser." + name, false, IntegrationCheck.class.getClassLoader());
            for (var method : screen.getDeclaredMethods()) if (method.getName().equals("render")) throw new AssertionError("Duplicate background risk: " + name);
            screen.getDeclaredMethod("renderBackground", net.minecraft.client.gui.GuiGraphics.class, int.class, int.class, float.class);
        }
        FilterEntry book = new FilterEntry();
        book.setItemId(ResourceLocation.parse("minecraft:enchanted_book"));
        book.setEnchantmentId(ResourceLocation.parse("minecraft:sharpness"));
        book.setEnchantmentLevel(5); book.setEnabled(true); book.setMaxPrice(23);
        FilterEntry copy = new FilterEntry(book); copy.setMaxPrice(7); copy.setEnabled(false);
        if (book.getMaxPrice() != 23 || !book.isEnabled()) throw new AssertionError("Cancel isolation failed");
        FilterEntry item = new FilterEntry(); item.setItemId(ResourceLocation.parse("minecraft:diamond")); item.setMinCount(3);
        var expected = List.of(book, item);
        for (boolean mode : new boolean[]{true, false}) {
            FilterConfig.saveFilters(expected, mode);
            String saved = Files.readString(Path.of("config/easyautocycler-filters.json"));
            FilterStore.verify(saved, expected, mode);
            var read = FilterConfig.loadFilters();
            FilterStore.verify(saved, FilterConfig.dataToFilters(read.filters), mode);
            try { FilterStore.verify(saved.replace("\"maxPrice\": 23", "\"maxPrice\": 22"), expected, mode); throw new AssertionError("Corruption accepted"); }
            catch (IllegalStateException correct) { }
        }
        if (!Catalog.normalize("Proteção / MAÇA").equals("protecao / maca")) throw new AssertionError("Accent search failed");
        String filtersBefore = Files.readString(Path.of("config/easyautocycler-filters.json"));
        new BrowserState("Botas", "", false, 0, null).save();
        if (!filtersBefore.equals(Files.readString(Path.of("config/easyautocycler-filters.json")))) throw new AssertionError("Navigation modified filters");
        System.out.println("PASS: persistent navigation; missing/corrupt/invalid state fallback; navigation isolated from filter edits.");
        System.out.println("PASS: original EAC serialization and reload; custom item filter; both match modes; isolated edits; corruption detection; accented search.");
    }
}

```


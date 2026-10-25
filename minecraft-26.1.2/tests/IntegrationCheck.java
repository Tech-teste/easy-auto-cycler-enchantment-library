package br.com.du.enchantbrowser;
import java.nio.file.*;
import java.util.*;
import com.uncraftbar.easyautocycler.filter.FilterEntry;
import com.uncraftbar.easyautocycler.config.FilterConfig;
import net.minecraft.resources.Identifier;
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
        UserLibraryCheck.run();
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
            for (var method : screen.getDeclaredMethods()) if (method.getName().equals("extractRenderState")) throw new AssertionError("Duplicate background risk: " + name);
            screen.getDeclaredMethod("extractBackground", net.minecraft.client.gui.GuiGraphicsExtractor.class, int.class, int.class, float.class);
        }
        FilterEntry book = new FilterEntry();
        book.setItemId(Identifier.parse("minecraft:enchanted_book"));
        book.setEnchantmentId(Identifier.parse("minecraft:sharpness"));
        book.setEnchantmentLevel(5); book.setEnabled(true); book.setMaxPrice(23);
        FilterEntry copy = new FilterEntry(book); copy.setMaxPrice(7); copy.setEnabled(false);
        if (book.getMaxPrice() != 23 || !book.isEnabled()) throw new AssertionError("Cancel isolation failed");
        FilterEntry item = new FilterEntry(); item.setItemId(Identifier.parse("minecraft:diamond")); item.setMinCount(3);
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

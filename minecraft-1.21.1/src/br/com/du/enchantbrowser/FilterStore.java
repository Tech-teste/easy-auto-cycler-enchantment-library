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

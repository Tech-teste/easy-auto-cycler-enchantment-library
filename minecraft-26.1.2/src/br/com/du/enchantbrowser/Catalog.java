package br.com.du.enchantbrowser;

import java.text.Normalizer;
import java.util.*;
import com.uncraftbar.easyautocycler.AutomationManager;
import com.uncraftbar.easyautocycler.filter.FilterEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;

final class Catalog {
    static final String[] CATEGORIES = {"Todos", "Espada", "Picareta", "Machado", "Pá", "Enxada", "Tesoura", "Escudo", "Capacete", "Peitoral", "Calças", "Botas", "Élitro", "Arco", "Besta", "Tridente", "Maça", "Pesca", "Outros"};
    record Entry(Identifier id, String name, String description, int maximum, Set<String> categories, FilterEntry filter) {
        String modName() {
            if(id.getNamespace().equals("minecraft")) return "Minecraft";
            return net.neoforged.fml.ModList.get().getModContainerById(id.getNamespace())
                    .map(container -> container.getModInfo().getDisplayName()).orElse(id.getNamespace());
        }
    }
    final List<Entry> entries = new ArrayList<>();
    final List<FilterEntry> preserved = new ArrayList<>();
    final List<FilterEntry> originals;
    int excluded;
    Catalog() {
        originals = AutomationManager.INSTANCE.getFilterEntries().stream().map(FilterEntry::new).toList();
        var registry = Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var tradeable = TagKey.create(Registries.ENCHANTMENT, Identifier.withDefaultNamespace("tradeable"));
        var curse = TagKey.create(Registries.ENCHANTMENT, Identifier.withDefaultNamespace("curse"));
        Set<FilterEntry> consumed = Collections.newSetFromMap(new IdentityHashMap<>());
        registry.listElements().forEach(holder -> {
            var id = holder.key().identifier();
            if (!holder.is(tradeable) || holder.is(curse) || id.getPath().contains("curse")) {
                excluded++;
                return;
            }
            var enchantment = holder.value();
            FilterEntry filter = originals.stream().filter(f -> id.equals(f.getEnchantmentId()) && Identifier.withDefaultNamespace("enchanted_book").equals(f.getItemId())).findFirst().orElse(null);
            if (filter == null) {
                filter = new FilterEntry();
                filter.setEnabled(false);
                filter.setItemId(Identifier.withDefaultNamespace("enchanted_book"));
                filter.setMinCount(1);
                filter.setEnchantmentId(id);
                filter.setEnchantmentLevel(enchantment.getMaxLevel());
                filter.setPaymentItemId(Identifier.withDefaultNamespace("emerald"));
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
                var holder = registry.get(id);
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

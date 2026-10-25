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

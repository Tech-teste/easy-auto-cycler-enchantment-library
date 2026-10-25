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

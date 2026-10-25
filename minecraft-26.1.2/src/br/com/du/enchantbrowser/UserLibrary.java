package br.com.du.enchantbrowser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/** User favorites and reusable book lists, separate from Auto Cycler's active filters. */
final class UserLibrary {
    record Book(String id, int level, int maxPrice, String payment) {
        Book {
            if (!validId(id) || payment != null && !validId(payment) || level < 1 || level > 32767 || maxPrice < 1 || maxPrice > 64)
                throw new IllegalArgumentException("Invalid saved book");
        }
    }
    record SavedList(String name, boolean matchAny, List<Book> books) {
        SavedList {
            name = cleanName(name);
            if (books == null || books.size() > 4096) throw new IllegalArgumentException("Invalid book list");
            books = List.copyOf(books);
            if (books.stream().map(Book::id).distinct().count() != books.size()) throw new IllegalArgumentException("Duplicate book ID");
        }
    }
    private record Data(int schema, Set<String> favorites, List<SavedList> lists) {}
    private static final Gson JSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path file;
    private final Set<String> favorites = new LinkedHashSet<>();
    private final List<SavedList> lists = new ArrayList<>();
    private boolean writable = true;
    private UserLibrary(Path file) { this.file=file; }
    static UserLibrary load() { return load(Path.of("config/easy-auto-cycler-library.json")); }
    static UserLibrary load(Path file) {
        var library = new UserLibrary(file);
        if (!Files.exists(file)) return library;
        try {
            Data data=JSON.fromJson(Files.readString(file),Data.class);
            if (data == null || data.schema()!=1 || data.favorites()==null || data.lists()==null || data.lists().size()>32
                    || data.favorites().stream().anyMatch(id -> !validId(id))
                    || data.lists().stream().map(SavedList::name).distinct().count()!=data.lists().size()) throw new IOException("Invalid or unsupported library");
            library.favorites.addAll(data.favorites());
            library.lists.addAll(data.lists());
        } catch (IOException | RuntimeException e) {
            // Keep the original bytes intact; do not silently overwrite a damaged/future-format file.
            library.writable=false;
            org.slf4j.LoggerFactory.getLogger("du_enchant_browser").warn("Could not load favorites and saved lists; file left untouched",e);
        }
        return library;
    }
    static boolean validId(String id) { return id!=null && id.matches("[a-z0-9_.-]+:[a-z0-9_./-]+"); }
    static String cleanName(String name) {
        if (name==null) throw new IllegalArgumentException("Missing list name");
        String clean=name.replaceAll("[\\p{Cntrl}]", "").strip();
        if (clean.isEmpty() || clean.length()>48) throw new IllegalArgumentException("List name must contain 1 to 48 characters");
        return clean;
    }
    boolean isFavorite(String id) { return favorites.contains(id); }
    List<SavedList> lists() { return List.copyOf(lists); }
    Optional<SavedList> find(String name) { return lists.stream().filter(list -> list.name().equals(name)).findFirst(); }
    void toggleFavorite(String id) throws IOException {
        if (!validId(id)) throw new IllegalArgumentException("Invalid favorite ID");
        boolean existed=favorites.remove(id);
        if (!existed) favorites.add(id);
        try { save(); } catch (IOException | RuntimeException e) {
            if (existed) favorites.add(id); else favorites.remove(id);
            throw e;
        }
    }
    void put(SavedList list) throws IOException {
        List<SavedList> previous=new ArrayList<>(lists);
        lists.removeIf(old -> old.name().equals(list.name()));
        lists.add(list);
        try {
            if (lists.size()>32) throw new IOException("Saved list limit reached");
            save();
        } catch (IOException | RuntimeException e) { lists.clear(); lists.addAll(previous); throw e; }
    }
    private void save() throws IOException {
        if (!writable) throw new IOException("Existing library could not be read; original file preserved");
        Files.createDirectories(file.toAbsolutePath().getParent());
        Path temporary=file.resolveSibling(file.getFileName()+".tmp");
        Files.writeString(temporary,JSON.toJson(new Data(1,favorites,lists)));
        if (Files.exists(file)) Files.copy(file,file.resolveSibling(file.getFileName()+".bak"),StandardCopyOption.REPLACE_EXISTING);
        try { Files.move(temporary,file,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE); }
        catch (AtomicMoveNotSupportedException e) { Files.move(temporary,file,StandardCopyOption.REPLACE_EXISTING); }
    }
}

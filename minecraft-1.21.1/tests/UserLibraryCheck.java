package br.com.du.enchantbrowser;

import java.nio.file.*;
import java.util.*;

final class UserLibraryCheck {
    static void run() throws Exception {
        Path dir=Files.createTempDirectory(Path.of("."),"library-check-");
        Path file=dir.resolve("library.json");
        Path active=Path.of("config/easyautocycler-filters.json");
        byte[] activeBefore=Files.exists(active) ? Files.readAllBytes(active) : null;
        var library=UserLibrary.load(file);
        library.toggleFavorite("minecraft:mending");
        var list=new UserLibrary.SavedList("Armadura",false,List.of(
                new UserLibrary.Book("minecraft:mending",1,23,"minecraft:diamond"),
                new UserLibrary.Book("othermod:missing_in_another_pack",4,64,null)));
        library.put(list);
        var reloaded=UserLibrary.load(file);
        if(!reloaded.isFavorite("minecraft:mending") || !reloaded.find("Armadura").orElseThrow().equals(list)) throw new AssertionError("Favorites or full filter settings did not survive reopening");
        reloaded.toggleFavorite("minecraft:mending");
        if(UserLibrary.load(file).isFavorite("minecraft:mending")) throw new AssertionError("Unfavorite did not persist");
        reloaded.put(new UserLibrary.SavedList("Armadura",true,List.of()));
        if(UserLibrary.load(file).lists().size()!=1 || !UserLibrary.load(file).lists().getFirst().matchAny()) throw new AssertionError("Replacing list duplicated it or lost match mode");
        if(!Files.exists(dir.resolve("library.json.bak"))) throw new AssertionError("Previous library was not backed up");
        byte[] beforeInvalid=Files.readAllBytes(file);
        try { new UserLibrary.Book("../../invalid",1,1,null); throw new AssertionError("Invalid enchantment ID accepted"); }
        catch(IllegalArgumentException expected) { }
        try { new UserLibrary.SavedList("  ",true,List.of()); throw new AssertionError("Empty list name accepted"); }
        catch(IllegalArgumentException expected) { }
        if(!Arrays.equals(beforeInvalid,Files.readAllBytes(file))) throw new AssertionError("Invalid input changed stored data");
        for(String damaged:List.of("{broken", "{\"schema\":99,\"favorites\":[],\"lists\":[]}")) {
            Files.writeString(file,damaged);
            var protectedLibrary=UserLibrary.load(file);
            try { protectedLibrary.toggleFavorite("minecraft:protection"); throw new AssertionError("Unreadable library overwritten"); }
            catch(java.io.IOException expected) { }
            if(!Files.readString(file).equals(damaged) || protectedLibrary.isFavorite("minecraft:protection")) throw new AssertionError("Failed save changed original file or in-memory favorite");
        }
        byte[] activeAfter=Files.exists(active) ? Files.readAllBytes(active) : null;
        if(!Arrays.equals(activeBefore,activeAfter)) throw new AssertionError("Saving lists or favorites changed active trade filters");
        System.out.println("PASS: favorites and lists persist with levels/prices/payment/mode; replacing lists; backups; corrupt/future files preserved; active filters isolated.");
    }
}

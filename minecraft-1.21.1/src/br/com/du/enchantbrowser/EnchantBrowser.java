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

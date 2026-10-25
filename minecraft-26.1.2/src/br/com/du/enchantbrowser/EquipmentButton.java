package br.com.du.enchantbrowser;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
    @Override protected void extractContents(GuiGraphicsExtractor g,int x,int y,float dt) {
        extractDefaultSprite(g);
        g.item(icon,getX()+4,getY()+(getHeight()-16)/2);
        var font = net.minecraft.client.Minecraft.getInstance().font;
        g.text(font,Component.literal(font.plainSubstrByWidth(getMessage().getString(),getWidth()-29)).withStyle(getMessage().getStyle()),getX()+25,getY()+(getHeight()-8)/2,active ? 0xFFFFFFFF : 0xFFA0A0A0);
    }
}

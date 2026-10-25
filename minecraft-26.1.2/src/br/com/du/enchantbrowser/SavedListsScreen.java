package br.com.du.enchantbrowser;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

final class SavedListsScreen extends Screen {
    private final BrowserScreen parent;
    private String name="", status="";
    private UserLibrary.SavedList selected;
    private int page,left,top,w;
    SavedListsScreen(BrowserScreen parent) { super(Component.literal(Text.t("lists_title"))); this.parent=parent; }
    private Button button(String text,int x,int y,int width,Runnable action) {
        return addRenderableWidget(Button.builder(Component.literal(text),b -> action.run()).bounds(x,y,width,20).build());
    }
    @Override protected void init() {
        w=Math.min(500,width-24); left=(width-w)/2; top=Math.max(4,(height-292)/2);
        var input=addRenderableWidget(new EditBox(font,left+10,top+56,w-160,20,Component.literal(Text.t("list_name"))));
        input.setMaxLength(48); input.setHint(Component.literal(Text.t("list_name_hint"))); input.setValue(name);
        input.setResponder(value -> name=value);
        button(Text.t("save_list"),left+w-140,top+56,130,this::saveList)
                .setTooltip(Tooltip.create(Component.literal(Text.t("save_list_tip"))));
        var lists=parent.library.lists();
        int pages=Math.max(1,(lists.size()+4)/5); page=Math.max(0,Math.min(page,pages-1));
        for(int i=page*5;i<Math.min(lists.size(),(page+1)*5);i++) {
            var list=lists.get(i);
            String label=font.plainSubstrByWidth(list.name(),w-94)+" ("+list.books().size()+")";
            var component=Component.literal(label);
            if(selected!=null && selected.name().equals(list.name())) component.withStyle(net.minecraft.ChatFormatting.YELLOW);
            addRenderableWidget(new LibraryButton(left+10,top+88+(i%5)*23,w-20,20,component,
                    selected!=null && selected.name().equals(list.name()),false,()->{selected=list; name=list.name(); status=""; rebuildWidgets();}));
        }
        button("<",left+10,top+209,25,()->{page--; rebuildWidgets();}).active=page>0;
        button(">",left+w-35,top+209,25,()->{page++; rebuildWidgets();}).active=page+1<pages;
        button(Text.t("load_list"),left+10,top+262,(w-26)/2,()->{
            if(selected!=null) { parent.applyList(selected); onClose(); }
        }).active=selected!=null;
        button(Text.t("back"),left+16+(w-26)/2,top+262,(w-26)/2,this::onClose);
        setInitialFocus(input);
    }
    private void saveList() {
        try {
            String clean=UserLibrary.cleanName(name);
            if(parent.library.find(clean).isPresent()) {
                minecraft.setScreen(new net.minecraft.client.gui.screens.ConfirmScreen(confirmed->{
                    if(confirmed) persist(clean);
                    minecraft.setScreen(this);
                },Component.literal(Text.t("replace_list")),Component.literal(Text.t("replace_list_message",clean))));
            } else { persist(clean); rebuildWidgets(); }
        } catch (IllegalArgumentException e) { status=Text.t("invalid_list_name"); rebuildWidgets(); }
    }
    private void persist(String clean) {
        try { parent.library.put(parent.snapshot(clean)); status=Text.t("list_saved",clean); }
        catch (Exception e) { status=Text.t("library_error"); }
    }
    @Override public void onClose() { minecraft.setScreen(parent); }
    @Override public boolean isPauseScreen() { return false; }
    @Override public void extractBackground(GuiGraphicsExtractor g,int x,int y,float dt) {
        super.extractBackground(g,x,y,dt);
        g.fill(left,top,left+w,top+290,0xF023242C);
        g.centeredText(font,title,width/2,top+10,0xFFF2D38B);
        g.textWithWordWrap(font,Component.literal(Text.t("lists_hint")),left+10,top+28,w-20,0xFFCCD2DF);
        g.text(font,Text.t("list_name"),left+10,top+45,0xFFCCD2DF);
        if(parent.library.lists().isEmpty()) g.textWithWordWrap(font,Component.literal(Text.t("lists_empty")),left+10,top+100,w-20,0xFFCCD2DF);
        g.centeredText(font,(page+1)+" / "+Math.max(1,(parent.library.lists().size()+4)/5),width/2,top+215,0xFFCCD2DF);
        if(!status.isEmpty()) g.textWithWordWrap(font,Component.literal(status),left+10,top+234,w-20,0xFFF2D38B);
    }
}

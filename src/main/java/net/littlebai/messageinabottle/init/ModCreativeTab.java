package net.littlebai.messageinabottle.init;

import net.littlebai.messageinabottle.MessageInaBottle;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MessageInaBottle.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TABS = TABS.register(MessageInaBottle.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + MessageInaBottle.MOD_ID + ".name"))
            .icon(() -> ModItems.FRIENDSHIP_BRACELET.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.FRIENDSHIP_BRACELET.get());
            }).build());
}

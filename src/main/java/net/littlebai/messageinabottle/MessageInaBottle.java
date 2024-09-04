package net.littlebai.messageinabottle;

import net.littlebai.messageinabottle.init.ModCreativeTab;
import net.littlebai.messageinabottle.init.ModData;
import net.littlebai.messageinabottle.init.ModItems;
import net.littlebai.messageinabottle.init.ModMenu;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(MessageInaBottle.MOD_ID)
public class MessageInaBottle {
    public static final String MOD_ID = "message_in_a_bottle";

    public MessageInaBottle(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.ITEMS.register(modEventBus);
        ModItems.DATA_COMPONENTS.register(modEventBus);
        ModCreativeTab.TABS.register(modEventBus);
        ModData.ATTACHMENT_TYPES.register(modEventBus);
        ModMenu.MENU_TYPES.register(modEventBus);
    }
}

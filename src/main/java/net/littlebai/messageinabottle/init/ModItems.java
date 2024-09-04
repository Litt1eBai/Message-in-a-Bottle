package net.littlebai.messageinabottle.init;

import net.littlebai.messageinabottle.MessageInaBottle;
import net.littlebai.messageinabottle.item.ItemMessageinaBottle;
import net.littlebai.messageinabottle.item.component.MessageinaBottleContents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MessageInaBottle.MOD_ID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(MessageInaBottle.MOD_ID);

    public static DeferredItem<Item> FRIENDSHIP_BRACELET = ITEMS.register("friendship_bracelet", ItemMessageinaBottle::new);
    public static DeferredHolder<DataComponentType<?>, DataComponentType<MessageinaBottleContents>> FRIENDSHIP_CONTENTS = DATA_COMPONENTS.registerComponentType(
            "friendship_bracelet", builder -> builder.persistent(MessageinaBottleContents.CODEC).networkSynchronized(MessageinaBottleContents.STREAM_CODEC).cacheEncoding()
    );
}

package net.littlebai.messageinabottle.client.init;

import net.littlebai.messageinabottle.MessageInaBottle;
import net.littlebai.messageinabottle.client.input.BraceletKey;
import net.littlebai.messageinabottle.client.screen.FriendshipScreen;
import net.littlebai.messageinabottle.inventory.FriendshipContainer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = MessageInaBottle.MOD_ID)
public class ClientSetup {
    @SubscribeEvent
    public static void onRegisterScreen(RegisterMenuScreensEvent event) {
        event.register(FriendshipContainer.TYPE, FriendshipScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(BraceletKey.BRACELET_KEY);
    }
}
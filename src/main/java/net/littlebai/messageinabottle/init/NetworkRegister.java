package net.littlebai.messageinabottle.init;

import net.littlebai.messageinabottle.MessageInaBottle;
import net.littlebai.messageinabottle.network.RequestServerPayload;
import net.littlebai.messageinabottle.network.SyncClientMessageinaBottlePayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = MessageInaBottle.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkRegister {
    public static final String VERSION = "1.0.0";

    @SubscribeEvent
    public static void registerPayload(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(VERSION);
        registrar.playToServer(RequestServerPayload.NETWORK_TYPE, RequestServerPayload.STREAM_CODEC, RequestServerPayload::serverHandler);
        registrar.playToClient(SyncClientMessageinaBottlePayload.NETWORK_TYPE, SyncClientMessageinaBottlePayload.STREAM_CODEC, SyncClientMessageinaBottlePayload::clientHandler);
    }
}

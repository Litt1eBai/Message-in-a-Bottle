package net.littlebai.messageinabottle.event;

import net.littlebai.messageinabottle.MessageInaBottle;
import net.littlebai.messageinabottle.entity.data.FriendshipData;
import net.littlebai.messageinabottle.init.ModData;
import net.littlebai.messageinabottle.network.SyncClientMessageinaBottlePayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = MessageInaBottle.MOD_ID)
public class PlayerTrackEvent {
    @SubscribeEvent
    public static void onPlayerTrack(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player trackPlayer && event.getEntity() instanceof ServerPlayer myself) {
            FriendshipData data = myself.getData(ModData.FRIENDSHIP_BRACELET.get());
            // 当追踪实体为自己的朋友时，同步朋友数据，用于客户端渲染
            if (trackPlayer.getUUID().equals(data.getFriendId())) {
                PacketDistributor.sendToPlayer(myself, new SyncClientMessageinaBottlePayload(data));
            }
        }
    }
}

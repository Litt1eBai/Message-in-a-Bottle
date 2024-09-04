package net.littlebai.messageinabottle.network;

import net.littlebai.messageinabottle.MessageInaBottle;
import net.littlebai.messageinabottle.entity.data.FriendshipData;
import net.littlebai.messageinabottle.init.ModData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class SyncClientMessageinaBottlePayload implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncClientMessageinaBottlePayload> NETWORK_TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MessageInaBottle.MOD_ID, "sync_friendship"));
    public static final StreamCodec<ByteBuf, SyncClientMessageinaBottlePayload> STREAM_CODEC = StreamCodec.composite(FriendshipData.STREAM_CODEC, SyncClientMessageinaBottlePayload::getFriendshipData, SyncClientMessageinaBottlePayload::new);
    private final FriendshipData friendshipData;

    public SyncClientMessageinaBottlePayload(FriendshipData friendshipData) {
        this.friendshipData = friendshipData;
    }

    public static void clientHandler(final SyncClientMessageinaBottlePayload data, final IPayloadContext context) {
        context.enqueueWork(() -> setClientFriendshipData(data));
    }

    @OnlyIn(Dist.CLIENT)
    private static void setClientFriendshipData(final SyncClientMessageinaBottlePayload friendshipData) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        player.setData(ModData.FRIENDSHIP_BRACELET.get(), friendshipData.friendshipData);
    }

    public FriendshipData getFriendshipData() {
        return friendshipData;
    }

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return NETWORK_TYPE;
    }
}

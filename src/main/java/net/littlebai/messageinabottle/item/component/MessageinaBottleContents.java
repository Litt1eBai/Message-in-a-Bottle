package net.littlebai.messageinabottle.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.Util;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record MessageinaBottleContents(UUID ownerId, UUID friendId, String ownerName, String friendName) {
    public static final Codec<MessageinaBottleContents> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    UUIDUtil.CODEC.fieldOf("owner_id").forGetter(MessageinaBottleContents::ownerId),
                    UUIDUtil.CODEC.fieldOf("friend_id").forGetter(MessageinaBottleContents::friendId),
                    Codec.STRING.fieldOf("owner_name").forGetter(MessageinaBottleContents::ownerName),
                    Codec.STRING.fieldOf("friend_name").forGetter(MessageinaBottleContents::friendName)
            ).apply(instance, MessageinaBottleContents::new)
    );
    public static final StreamCodec<ByteBuf, MessageinaBottleContents> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, MessageinaBottleContents::ownerId,
            UUIDUtil.STREAM_CODEC, MessageinaBottleContents::friendId,
            ByteBufCodecs.STRING_UTF8, MessageinaBottleContents::ownerName,
            ByteBufCodecs.STRING_UTF8, MessageinaBottleContents::friendName,
            MessageinaBottleContents::new
    );

    public static MessageinaBottleContents empty() {
        return new MessageinaBottleContents(Util.NIL_UUID, Util.NIL_UUID, StringUtils.EMPTY, StringUtils.EMPTY);
    }
}

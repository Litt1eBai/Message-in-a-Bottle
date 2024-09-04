package net.littlebai.messageinabottle.init;

import net.littlebai.messageinabottle.MessageInaBottle;
import net.littlebai.messageinabottle.entity.data.FriendshipData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModData {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MessageInaBottle.MOD_ID);

    public static final Supplier<AttachmentType<FriendshipData>> FRIENDSHIP_BRACELET = ATTACHMENT_TYPES.register("friendship_bracelet", () -> FriendshipData.ATTACHMENT_TYPE);
}

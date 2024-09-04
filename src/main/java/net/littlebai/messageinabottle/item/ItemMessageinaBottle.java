package net.littlebai.messageinabottle.item;

import net.littlebai.messageinabottle.client.input.BraceletKey;
import net.littlebai.messageinabottle.entity.data.FriendshipData;
import net.littlebai.messageinabottle.init.ModItems;
import net.littlebai.messageinabottle.item.component.MessageinaBottleContents;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ItemMessageinaBottle extends Item {
    public ItemMessageinaBottle() {
        super(new Properties().stacksTo(1).component(ModItems.FRIENDSHIP_CONTENTS.value(), MessageinaBottleContents.empty()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (usedHand == InteractionHand.OFF_HAND) {
            return super.use(level, player, usedHand);
        }

        ItemStack itemInHand = player.getItemInHand(usedHand);
        MessageinaBottleContents contents = itemInHand.getOrDefault(ModItems.FRIENDSHIP_CONTENTS.value(), MessageinaBottleContents.empty());
        UUID ownerId = contents.ownerId();
        UUID friendId = contents.friendId();
        UUID userId = player.getUUID();

        String ownerName = contents.ownerName();
        String userName = player.getScoreboardName();

        // 如果主人栏为空，写入主人
        if (Util.NIL_UUID.equals(ownerId)) {
            itemInHand.set(ModItems.FRIENDSHIP_CONTENTS.value(), new MessageinaBottleContents(userId, Util.NIL_UUID, userName, StringUtils.EMPTY));
            sendMessage(player, Component.translatable("message.friendship_bracelet.friendship_bracelet.owner.success"));
            return InteractionResultHolder.success(itemInHand);
        }
        // 如果朋友栏为空
        if (Util.NIL_UUID.equals(friendId)) {
            // 和主人栏 ID 不同，那就是朋友
            if (!ownerId.equals(userId)) {
                itemInHand.set(ModItems.FRIENDSHIP_CONTENTS.value(), new MessageinaBottleContents(ownerId, userId, ownerName, userName));
                sendMessage(player, Component.translatable("message.friendship_bracelet.friendship_bracelet.friend.success", ownerName));
                return InteractionResultHolder.success(itemInHand);
            } else {
                sendMessage(player, Component.translatable("message.friendship_bracelet.friendship_bracelet.owner.same"));
                return InteractionResultHolder.fail(itemInHand);
            }
        }
        // 是自己的，装上
        if (ownerId.equals(userId)) {
            player.setData(FriendshipData.ATTACHMENT_TYPE, FriendshipData.getInstance(contents));
            player.getItemInHand(usedHand).shrink(1);
            sendMessage(player, Component.translatable("message.friendship_bracelet.friendship_bracelet.owner.equip"));
            return InteractionResultHolder.consume(itemInHand);
        } else {
            sendMessage(player, Component.translatable("message.friendship_bracelet.friendship_bracelet.owner.not"));
            return InteractionResultHolder.fail(itemInHand);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        MessageinaBottleContents contents = stack.get(ModItems.FRIENDSHIP_CONTENTS.value());
        if (contents != null) {
            UUID ownerId = contents.ownerId();
            String ownerName = contents.ownerName();
            UUID friendId = contents.friendId();
            String friendName = contents.friendName();
            boolean hasInfo = false;

            if (!Util.NIL_UUID.equals(ownerId)) {
                components.add(Component.translatable("tooltip.friendship_bracelet.friendship_bracelet.owner", ownerName).withStyle(ChatFormatting.GOLD));
                hasInfo = true;
            }
            if (!Util.NIL_UUID.equals(friendId)) {
                components.add(Component.translatable("tooltip.friendship_bracelet.friendship_bracelet.friend", friendName).withStyle(ChatFormatting.GREEN));
                hasInfo = true;
            }
            if (hasInfo) {
                components.add(CommonComponents.space());
            }
        }
        components.add(Component.translatable("tooltip.friendship_bracelet.friendship_bracelet.usage.1").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("tooltip.friendship_bracelet.friendship_bracelet.usage.2").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("tooltip.friendship_bracelet.friendship_bracelet.usage.3").withStyle(ChatFormatting.GRAY));
        String keyName = Component.keybind(BraceletKey.BRACELET_KEY.getName()).getString().toUpperCase(Locale.ENGLISH);
        components.add(Component.translatable("tooltip.friendship_bracelet.friendship_bracelet.usage.4", keyName).withStyle(ChatFormatting.GRAY));
    }

    private void sendMessage(Player player, MutableComponent component) {
        if (!player.level().isClientSide()) {
            player.sendSystemMessage(component);
        }
    }
}

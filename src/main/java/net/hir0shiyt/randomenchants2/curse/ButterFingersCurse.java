package net.hir0shiyt.randomenchants2.curse;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.enchantment.ModEnchantments;
import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class ButterFingersCurse extends Enchantment {
    public ButterFingersCurse(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 25;
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.butterFingersConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.butterFingersConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @SubscribeEvent
    public static void droppingItem(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        if (EnchantUtils.hasEnch(heldItem, ModEnchantments.BUTTER_FINGERS_CURSE)) {
            if (Math.random() > .50) return;
            ItemEntity itemStack = new ItemEntity(player.getLevel(), player.getX(), player.getY(), player.getZ(), player.getItemBySlot(EquipmentSlot.MAINHAND).copy());
            player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            player.drop(player.getItemBySlot(EquipmentSlot.MAINHAND), true, true);
            player.getLevel().addFreshEntity(itemStack);
        }
    }

    @SubscribeEvent
    public static void droppingSword(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) return;
        Player player = (Player) entity;
        ItemStack heldItem = player.getMainHandItem();
        if (EnchantUtils.hasEnch(heldItem, ModEnchantments.BUTTER_FINGERS_CURSE)) {
            if (Math.random() > .50) return;
            ItemEntity itemStack = new ItemEntity(player.getLevel(), player.getX(), player.getY(), player.getZ(), player.getItemBySlot(EquipmentSlot.MAINHAND).copy());
            player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            player.drop(player.getItemBySlot(EquipmentSlot.MAINHAND), true, true);
            player.getLevel().addFreshEntity(itemStack);
        }
    }
}
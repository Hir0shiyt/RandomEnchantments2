package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class QuickDraw extends Enchantment {
    public QuickDraw(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 15 * (level - 1);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.quickDrawConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.ServerConfig.quickDrawConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.quickDrawConfig.get() == ModConfig.Restriction.ENABLED;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.quickDrawConfig.get() == ModConfig.Restriction.TREASURE;
    }

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (!(heldItem.getItem() instanceof BowItem || heldItem.getItem() instanceof CrossbowItem)) {
                heldItem = player.getItemInHand(InteractionHand.OFF_HAND);
            }

            if (!(heldItem.getItem() instanceof BowItem || heldItem.getItem() instanceof CrossbowItem) || !EnchantUtils.hasEnch(player, ModEnchantments.QUICK_DRAW.get())) {
                return;
            }

            if (player.isUsingItem()) {
                for (int i = 0; i < EnchantmentHelper.getEnchantmentLevel(ModEnchantments.QUICK_DRAW.get(), player); i++) {
                    updateActiveHand(player);
                }
            }
        }
    }

    private static void updateActiveHand(Player player) {
        try {
            Method m = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "updatingUsingItem");
                    m.setAccessible(true);
                    m.invoke(player);
        } catch (Exception e) {
            RandomEnchants2.logger.error("Reflection error " + e);
        }
    }
}
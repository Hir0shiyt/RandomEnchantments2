package net.hir0shiyt.randomenchants2.curse;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.enchantment.ModEnchantments;
import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class ShadowsCurse extends Enchantment {
    public ShadowsCurse(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 25;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.shadowsConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.shadowsConfig.get() == ModConfig.Restriction.TREASURE;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    private static EquipmentSlot[] list = new EquipmentSlot[]{EquipmentSlot.HEAD,
            EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET,
            EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    @SubscribeEvent
    public static void applyShadows(TickEvent.PlayerTickEvent event) {
        if (Math.random() > .05) return;
        Player player = event.player;
        if (player.getCommandSenderWorld().isClientSide) return;
        for (EquipmentSlot slot : list) {
            ItemStack stack = player.getItemBySlot(slot);
            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SHADOWS_CURSE, stack) == 0) continue;
            if (EnchantUtils.isDark(player)) {
                    stack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(player1.getUsedItemHand()));
            }
        }
    }
}
package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Eternal extends Enchantment {
    public Eternal(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost (int level) {
        return 15;
    }

    @Override
    public int getMaxLevel () {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.eternalConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.eternalConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.eternalConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.eternalConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment){
        return !(enchantment instanceof SolarEnchant) &&
                !(enchantment instanceof MendingEnchantment) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void onItemDespawn(ItemExpireEvent event) {
        ItemEntity entityItem = event.getEntity();
        ItemStack stack = entityItem.getItem();

        if (hasEternal(stack)) {
            event.setExtraLife(Integer.MAX_VALUE);
            event.setCanceled(true);
        }
    }

    private static boolean hasEternal(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ETERNAL.get(), stack) > 0;
    }
}

package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentConfigs extends Enchantment {

    private ModConfig.Restriction config;
    private final boolean isCurse;

    protected EnchantmentConfigs(Rarity rarityIn, EnchantmentCategory categoryIn, EquipmentSlot[] slots, boolean curse, ModConfig.Restriction defaultRestriction) {
        super(rarityIn, categoryIn, slots);
        config = defaultRestriction;
        isCurse = curse;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return config != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return config != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return config == ModConfig.Restriction.ENABLED;
    }

    @Override
    public boolean isTreasureOnly() {
        return isCurse() || config == ModConfig.Restriction.TREASURE;
    }

    public boolean isCurse() {
        return isCurse;
    }

    public boolean isDiscoverable() {
        return config != ModConfig.Restriction.DISABLED;
    }

    public void setRestriction(ModConfig.Restriction restriction) {
        config = restriction;
    }
}

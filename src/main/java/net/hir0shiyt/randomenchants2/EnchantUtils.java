package net.hir0shiyt.randomenchants2;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantUtils {

    public static boolean hasEnch(ItemStack stack, Enchantment enchantment) {
        return enchantment != null && EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) > 0;
    }

    public static boolean hasEnch(LivingEntity entity, Enchantment enchantment) {
        return enchantment != null && EnchantmentHelper.getEnchantmentLevel(enchantment, entity) > 0;
    }
}

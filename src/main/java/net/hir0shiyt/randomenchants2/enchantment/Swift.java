package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Swift extends Enchantment {
    public Swift(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category ,slots);
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
        return ModConfig.swiftConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem) {
            return ModConfig.swiftConfig.isEnabled.get();
        } else {
            return false;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.swiftConfig.isEnabled.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.swiftConfig.isEnabled.get() && ModConfig.swiftConfig.isTradeable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.swiftConfig.isEnabled.get() && ModConfig.swiftConfig.isTreasureOnly.get();
    }

    @SubscribeEvent
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!EnchantUtils.hasEnch(player, ModEnchantments.SWIFT)) return;
        int swing = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class, player, "f_20922_");
        swing += EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SWIFT, player);
        ObfuscationReflectionHelper.setPrivateValue(LivingEntity.class, player, swing, "f_20922_");
    }
}

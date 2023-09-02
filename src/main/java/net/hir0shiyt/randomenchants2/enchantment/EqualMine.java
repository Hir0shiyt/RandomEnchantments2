package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EqualMine extends Enchantment {
    public EqualMine(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 15;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.equalMineConfig.isEnabled.get() && ModConfig.equalMineConfig.canApplyAtEnchantingTable.get();
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.equalMineConfig.isEnabled.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.equalMineConfig.isEnabled.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.equalMineConfig.isEnabled.get() && ModConfig.equalMineConfig.isTradeable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.equalMineConfig.isEnabled.get() && ModConfig.equalMineConfig.isTreasureOnly.get();
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        BlockState state = event.getState();
        Level world = player.getLevel();
        BlockPos pos = event.getPos();
        float hardness = state.getBlock().defaultDestroyTime();
        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.EQUAL_MINE, heldItem) > 0) {
            float oldSpeed = event.getOriginalSpeed();
            if (hardness<1) hardness =1;
            float newSpeed= hardness * oldSpeed;
            event.setNewSpeed(newSpeed);
        }
    }
}

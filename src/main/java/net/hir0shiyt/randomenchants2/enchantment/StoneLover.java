package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class StoneLover extends Enchantment {

    public StoneLover(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 15; // Minimum cost required for the enchantment to appear
    }

    @Override
    public int getMaxLevel() {
        return 1; // Maximum enchantment level
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof PickaxeItem) {
            return ModConfig.stoneLoverConfig.isEnabled.get() && ModConfig.stoneLoverConfig.canApplyAtEnchantingTable.get();
        } else {
            return false;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.stoneLoverConfig.isEnabled.get();
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.stoneLoverConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.stoneLoverConfig.isEnabled.get() && ModConfig.stoneLoverConfig.isTradeable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.stoneLoverConfig.isEnabled.get() && ModConfig.stoneLoverConfig.isTreasureOnly.get();
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            ItemStack mainHandStack = player.getMainHandItem();
            int mainHandLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.STONE_LOVER, mainHandStack);

            if (mainHandLevel > 0) {
                Block block = event.getState().getBlock();
                if (block == Blocks.STONE && player.getRandom().nextFloat() < 0.8f) {
                    int repairAmount = 2;
                    mainHandStack.setDamageValue(Math.max(0, mainHandStack.getDamageValue() - repairAmount));
                    mainHandStack.setPopTime(5); // Show the "pop" animation for a brief time
                }
            }
        }
    }
}
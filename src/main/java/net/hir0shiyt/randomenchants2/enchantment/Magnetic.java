package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Magnetic extends Enchantment {

    public Magnetic(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
    protected boolean checkCompatibility (Enchantment enchantment) {
        return !(enchantment instanceof Randomness) &&
                super.checkCompatibility(enchantment);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.magneticConfig.isEnabled.get() && ModConfig.magneticConfig.canApplyAtEnchantingTable.get() && this.category.equals(EnchantmentCategory.DIGGER);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.magneticConfig.isEnabled.get() && this.category.equals(EnchantmentCategory.DIGGER);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.magneticConfig.isEnabled.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.magneticConfig.isEnabled.get() && ModConfig.magneticConfig.isTradeable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.magneticConfig.isEnabled.get() && ModConfig.magneticConfig.isTreasureOnly.get();
    }


    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        Block block = state.getBlock();
        ItemStack toolStack = event.getPlayer().getMainHandItem();

        if (hasMagnetic(toolStack) && !event.getWorld().isClientSide()) {
            List<ItemStack> drops = Block.getDrops(state, (ServerLevel) event.getWorld(), event.getPos(), null); // Passing null here

            if (!drops.isEmpty()) {
                // Add drops to player's inventory
                for (ItemStack drop : drops) {
                    event.getPlayer().getInventory().placeItemBackInInventory(drop);
                }
                event.getWorld().destroyBlock(event.getPos(), false); // Destroy the block
            }
        }
    }

    private static boolean hasMagnetic(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGNETIC, stack) > 0;
    }
}
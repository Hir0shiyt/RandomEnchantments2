package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class ObsidianBuster extends Enchantment {
    public ObsidianBuster(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 10;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Randomness) &&
                super.checkCompatibility(enchantment);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof PickaxeItem) {
            return ModConfig.obsidianBusterConfig.isEnabled.get();
        } else {
            return false;
        }
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.obsidianBusterConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.obsidianBusterConfig.isEnabled.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.obsidianBusterConfig.isEnabled.get() && ModConfig.obsidianBusterConfig.isTradeable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.obsidianBusterConfig.isEnabled.get() && ModConfig.obsidianBusterConfig.isTreasureOnly.get();
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getItemInHand(player.getUsedItemHand());

        // Check if the enchantment is enabled
        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.OBSIDIAN_BUSTER, heldItem) <= 0) {
            return;
        }

        if (event.getState().getBlock() == Blocks.OBSIDIAN) {
            // Apply the custom breaking speed boost for obsidian
            float newSpeed = event.getNewSpeed() + 100F; // Adjust the speed boost as needed
            event.setNewSpeed(newSpeed);
        }
    }
}

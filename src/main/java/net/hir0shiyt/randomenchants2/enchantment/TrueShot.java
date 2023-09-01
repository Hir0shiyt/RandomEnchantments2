package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class TrueShot extends Enchantment {
    public TrueShot(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.trueShotConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof BowItem && stack.getItem() instanceof CrossbowItem) {
            return ModConfig.trueShotConfig.isEnabled.get() && ModConfig.trueShotConfig.canApplyAtEnchantingTable.get();
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.trueShotConfig.isEnabled.get() && ModConfig.trueShotConfig.isTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.trueShotConfig.isEnabled.get() && ModConfig.trueShotConfig.isTradeable.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.trueShotConfig.isEnabled.get();
    }

    @SubscribeEvent
    public static void arrowSpawn(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof AbstractArrow)) return;
        Entity shooter = ((AbstractArrow) entity).getOwner();
        if (!(shooter instanceof Player)) return;
        Player player = (Player) shooter;
        if (shooter instanceof Player) {
            Player player1 = (Player) shooter;
            ItemStack heldItem = player1.getMainHandItem();
            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.TRUE_SHOT, heldItem) > 0) {
                entity.setNoGravity(true);
            }
        }
    }
}
package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.EnchantUtils;
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
import net.minecraft.world.item.enchantment.MultiShotEnchantment;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Homing extends Enchantment {
    public Homing(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
    public boolean canEnchant(@Nonnull ItemStack stack) {
        return ModConfig.ServerConfig.homingConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) {
            return ModConfig.ServerConfig.homingConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
        } else {
            return false;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.homingConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.homingConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof MultiShotEnchantment) &&
                !(enchantment instanceof Torches) &&
                !(enchantment instanceof Transposition) &&
                !(enchantment instanceof Teleportation) &&
                !(enchantment instanceof TrueShot) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void arrowLoose(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof AbstractArrow)) return;
        Entity shooter = ((AbstractArrow) entity).getOwner();
        if (!(shooter instanceof Player)) return;
        Player player = (Player) shooter;
        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.HOMING, player.getMainHandItem()) > 0) {
            entity.setNoGravity(true);
        }
    }
}
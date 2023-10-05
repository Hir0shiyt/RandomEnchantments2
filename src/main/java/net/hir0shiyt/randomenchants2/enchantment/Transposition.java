package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MultiShotEnchantment;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Transposition extends Enchantment {
    public Transposition(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.ServerConfig.transpositionConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.ServerConfig.transpositionConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.transpositionConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.transpositionConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Teleportation) &&
                !(enchantment instanceof Torches) &&
                !(enchantment instanceof MultiShotEnchantment) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void onArrowHit(ProjectileImpactEvent event) {
        if (!(event.getRayTraceResult() instanceof EntityHitResult)) return;
        Entity targetEntity = ((EntityHitResult) event.getRayTraceResult()).getEntity();
        Entity arrowEntity = event.getEntity();
        if (arrowEntity instanceof AbstractArrow) {
            AbstractArrow arrow= (AbstractArrow) arrowEntity;
            Entity shooter = arrow.getOwner();
            if (shooter instanceof Player) {
                Player playerShooter = (Player) shooter;
                ItemStack heldItem = playerShooter.getMainHandItem();
                if (EnchantUtils.hasEnch(heldItem, ModEnchantments.TRANSPOSITION.get())) {
                    BlockPos shooterPos = playerShooter.getOnPos();
                    BlockPos targetPos = targetEntity.getOnPos();
                        shooter.teleportTo(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ());
                        targetEntity.teleportTo(shooterPos.getX(), shooterPos.getY() + 1, shooterPos.getZ());
                    arrow.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        }
    }
}

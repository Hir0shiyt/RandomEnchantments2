package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Teleportation extends Enchantment {
    public Teleportation(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.teleportationConfig.isEnabled.get() && this.category.equals(RandomEnchants2.SHOOTABLE);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.teleportationConfig.isEnabled.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.teleportationConfig.isEnabled.get() && ModConfig.teleportationConfig.canApplyAtEnchantingTable.get() && this.category.equals(RandomEnchants2.SHOOTABLE);
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.teleportationConfig.isEnabled.get() && ModConfig.teleportationConfig.isTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.teleportationConfig.isEnabled.get() && ModConfig.teleportationConfig.isTradeable.get();
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof TridentRiptideEnchantment) &&
                !(enchantment instanceof MultiShotEnchantment) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void teleportArrow(ProjectileImpactEvent e) {
        if (!(e.getRayTraceResult() instanceof BlockHitResult)) return;
        if (!(e.getProjectile() instanceof AbstractArrow) || e.getEntity().level.isClientSide) return;
        AbstractArrow arrow = (AbstractArrow) e.getProjectile();
        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof LivingEntity)) return;

        if (!(shooter instanceof Player)) return;
        Player player = (Player) shooter;
        ItemStack heldItem = player.getMainHandItem();

        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.TELEPORTATION.get(), heldItem) > 0) return;

        BlockPos pos = ((BlockHitResult) e.getRayTraceResult()).getBlockPos();
        if (arrow.level.getBlockState(pos.above()).getMaterial() == Material.LAVA) return;

        shooter.teleportTo(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);

        arrow.remove(Entity.RemovalReason.DISCARDED);
    }

    @SubscribeEvent
    public static void looseArrow(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof AbstractArrow) {
            AbstractArrow arrow = (AbstractArrow) e.getEntity();
            Entity owner = arrow.getOwner();
            if (owner instanceof Player) {
                Player player = (Player) owner;
                ItemStack heldItem = player.getMainHandItem();
                if (owner instanceof LivingEntity && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.TELEPORTATION.get(), heldItem) > 0) {
                    arrow.getPersistentData().putBoolean(ModEnchantments.TELEPORTATION.getKey().toString(), true);
                }
            }
        }
    }
}
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
        return ModConfig.ServerConfig.teleportationConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.ServerConfig.teleportationConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.teleportationConfig.get() == ModConfig.Restriction.ENABLED;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.teleportationConfig.get() == ModConfig.Restriction.TREASURE;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof TridentRiptideEnchantment) &&
                !(enchantment instanceof MultiShotEnchantment) &&
                !(enchantment instanceof Torches) &&
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

        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.TELEPORTATION, heldItem) > 0) {

            BlockPos pos = ((BlockHitResult) e.getRayTraceResult()).getBlockPos();
        if (arrow.level.getBlockState(pos.above()).getMaterial() == Material.LAVA) return;

        shooter.teleportTo(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);

        arrow.remove(Entity.RemovalReason.DISCARDED);
    }

}
    @SubscribeEvent
    public static void looseArrow(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof AbstractArrow) {
            AbstractArrow arrow = (AbstractArrow) e.getEntity();
            Entity owner = arrow.getOwner();
            if (owner instanceof Player) {
                Player player = (Player) owner;
                ItemStack heldItem = player.getMainHandItem();
                if (owner instanceof LivingEntity && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.TELEPORTATION, heldItem) > 0) {
                    arrow.getPersistentData().putBoolean(ModEnchantments.TELEPORTATION.toString(), true);
                }
            }
        }
    }
}
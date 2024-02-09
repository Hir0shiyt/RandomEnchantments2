package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Deflect extends Enchantment {
    public Deflect(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 30;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.deflectConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.deflectConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.deflectConfig.get() == ModConfig.Restriction.ENABLED;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.deflectConfig.get() == ModConfig.Restriction.TREASURE;
    }

    @SubscribeEvent
    public static void deflect(ProjectileImpactEvent e) {
        if (!(e.getRayTraceResult() instanceof EntityHitResult))return;
        Entity projectile = e.getEntity();
        Entity target = ((EntityHitResult) e.getRayTraceResult()).getEntity();
        if (!(target instanceof Player)) return;
        Player player = (Player) target;
        if (EnchantUtils.hasEnch(player, ModEnchantments.DEFLECT)) {
            Vec3 projectileMotion = projectile.getDeltaMovement();
            projectile.setDeltaMovement(projectileMotion.scale(-1.0));
            if (projectile instanceof AbstractHurtingProjectile) {
                AbstractHurtingProjectile hurtingProjectile = (AbstractHurtingProjectile) projectile;
                hurtingProjectile.xPower *= -1;
                hurtingProjectile.yPower *= -1;
                hurtingProjectile.zPower *= -1;
            }
            e.setCanceled(true);
        }
    }
}

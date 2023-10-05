package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Reflect extends Enchantment {
    public Reflect(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.ServerConfig.reflectConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.reflectConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.reflectConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.reflectConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof EntityHitResult) {
            EntityHitResult entityHitResult = (EntityHitResult) event.getRayTraceResult();
            Entity targetEntity = entityHitResult.getEntity();
            Entity projectile = event.getEntity();
            if (projectile instanceof AbstractArrow && targetEntity instanceof Player) {
                Player player = (Player) targetEntity;
                ItemStack shield = player.getItemInHand(InteractionHand.OFF_HAND);
                if (EnchantUtils.hasEnch(shield, ModEnchantments.REFLECT.get())) {
                    Vec3 originalDirection = projectile.getDeltaMovement().normalize();
                    Vec3 reflectedDirection = originalDirection.scale(-1);
                    if (player.isUsingItem() && player.getUseItem() == shield) {
                        float originalDamage = (float) ((AbstractArrow) projectile).getBaseDamage();
                        float reflectedDamage = originalDamage * 2;

                        AbstractArrow reflectedArrow = new Arrow(player.level, player);
                        reflectedArrow.setPos(player.getX(), player.getY() + 1.0, player.getZ());
                        reflectedArrow.shoot(reflectedDirection.x, reflectedDirection.y, reflectedDirection.z, 1.5F, 0);
                        reflectedArrow.setBaseDamage(reflectedDamage);

                        shield.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                        player.level.addFreshEntity(reflectedArrow);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}

package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Ricochet extends Enchantment {
    private static final Map<AbstractArrow, Long> lastCollitionTime = new HashMap<>();

    public Ricochet(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 25;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.ricochetConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.ServerConfig.ricochetConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.ricochetConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.ricochetConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Torches) &&
                !(enchantment instanceof Teleportation) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void arrowHit(ProjectileImpactEvent event) {
        HitResult result = event.getRayTraceResult();
        if (!(result instanceof BlockHitResult)) return;
        Entity entity = event.getEntity();
        if (!(entity instanceof AbstractArrow)) return;
        AbstractArrow arrow = (AbstractArrow) entity;
        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof Player)) return;
        Player player = (Player) shooter;
        ItemStack heldItem = player.getMainHandItem();
        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RICOCHET.get(), heldItem) > 0) {
            Direction facing = ((BlockHitResult) event.getRayTraceResult()).getDirection();
            if (!lastCollitionTime.containsKey(arrow)) {
                lastCollitionTime.put(arrow, System.currentTimeMillis());
            }  else {
                long currentTime = System.currentTimeMillis();
                long lastCollition = lastCollitionTime.get(arrow);

                if (currentTime - lastCollition >= 10 * 1000) {
                    arrow.remove(Entity.RemovalReason.DISCARDED);
                    lastCollitionTime.remove(arrow);
                    return;
                }
            }

            double x = arrow.getDeltaMovement().x();
            double y = arrow.getDeltaMovement().y();
            double z = arrow.getDeltaMovement().z();

            //A little bit of improvising with Vec3 for calculating correctly & avoiding issues
            Vec3 arrowMotion = arrow.getDeltaMovement();

            switch (facing) {
                case UP, DOWN:
                    arrowMotion = new Vec3(arrowMotion.x, -arrowMotion.y, arrowMotion.z);
                    break;
                case EAST, WEST:
                    arrowMotion = new Vec3(-arrowMotion.x, arrowMotion.y, arrowMotion.z);
                    break;
                case NORTH, SOUTH:
                    arrowMotion = new Vec3(arrowMotion.x, arrowMotion.y, -arrowMotion.z);
                    break;
                default:
                    throw new IllegalStateException("INVALID ENUM DETECTED" + facing);
            }
            lastCollitionTime.put(arrow, System.currentTimeMillis());
            arrow.setDeltaMovement(arrowMotion);
            event.setCanceled(true);

        }
    }
}
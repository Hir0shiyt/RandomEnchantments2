package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
        return ModConfig.transpositionConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof BowItem) {
            return ModConfig.transpositionConfig.isEnabled.get();
        } else {
            return false;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.transpositionConfig.isEnabled.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.transpositionConfig.isEnabled.get() && ModConfig.transpositionConfig.isTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.transpositionConfig.isEnabled.get() && ModConfig.transpositionConfig.isTradeable.get();
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Teleportation) &&
                !(enchantment instanceof Torches) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void teleportArrow(ProjectileImpactEvent e) {
        if (!(e.getRayTraceResult() instanceof EntityHitResult) || !(((EntityHitResult) e.getRayTraceResult()).getEntity() instanceof LivingEntity)) return;
        if (!(e.getEntity() instanceof AbstractArrow) || e.getEntity().level.isClientSide) return;
        AbstractArrow arrow = (AbstractArrow) e.getEntity();
        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof LivingEntity)) return;
        LivingEntity e1 = (LivingEntity) shooter;
        if (shooter instanceof Player) {
            if (!(EnchantUtils.hasEnch(e1, ModEnchantments.TRANSPOSITION))) return;
            float[] pos1 = new float[]{(float)e1.getX(), (float)e1.getY(), (float)e1.getZ(), e1.getXRot(), e1.getYHeadRot()};
            LivingEntity e2 = (LivingEntity) ((EntityHitResult) e.getRayTraceResult()).getEntity();
            float[] pos2 = new float[]{(float)e2.getX(), (float)e2.getY(), (float)e2.getZ(), e2.getXRot(), e2.getYHeadRot()};
            e1.setPos(pos2[0], pos2[1], pos2[2]);
            e2.setPos(pos1[0], pos1[1], pos1[2]);
            arrow.remove(Entity.RemovalReason.DISCARDED);
        }
    }
}

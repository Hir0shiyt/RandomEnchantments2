package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class CursedJumping extends Enchantment {
    private CursedJumping(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.ServerConfig.cursedJumpingConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) {
            return ModConfig.ServerConfig.cursedJumpingConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
        } else {
            return false;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.cursedJumpingConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.cursedJumpingConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Paralysis) &&
                !(enchantment instanceof Transposition) &&
                !(enchantment instanceof Teleportation) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        AbstractArrow arrow = (AbstractArrow) event.getEntity();
        LivingEntity shooter = (LivingEntity) arrow.getOwner();
        if (shooter instanceof Player) {
            Player player = (Player) shooter;
            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.PARALYSIS, player.getMainHandItem()) > 0) {
                if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY) {
                    Entity target = ((EntityHitResult) event.getRayTraceResult()).getEntity();
                    if (target instanceof LivingEntity) {
                        LivingEntity livingTarget = (LivingEntity) target;
                        applyEffects(livingTarget);
                    }
                }
            }
        }
    }

    private static void applyEffects(LivingEntity entity) {
        MobEffect jump_boost = MobEffects.JUMP;
        entity.addEffect(new MobEffectInstance(jump_boost, 40, 127));
    }
}
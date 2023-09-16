package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Floating extends Enchantment {
    public Floating(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 15;
    }

    @Override
    public int getMaxLevel(){
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.floatingConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.floatingConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.floatingConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.floatingConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        Entity arrow = event.getEntity();
        if (arrow instanceof ThrowableProjectile || arrow instanceof Fireball || arrow instanceof DragonFireball || arrow instanceof ThrownTrident || arrow instanceof FireworkRocketEntity || arrow instanceof FishingHook) {return;}
        if (arrow instanceof AbstractArrow) {
            LivingEntity shooter = (LivingEntity) ((AbstractArrow) arrow).getOwner();
            if (shooter instanceof Player) {
                Player player = (Player) shooter;
                if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FLOATING, player.getMainHandItem()) > 0) {
                    if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY) {
                        Entity target = ((EntityHitResult) event.getRayTraceResult()).getEntity();
                        if (target instanceof LivingEntity) {
                            LivingEntity livingTarget = (LivingEntity) target;
                            applyEffects(livingTarget);
                            BlockPos targetPos = target.getOnPos();
                            livingTarget.teleportTo(targetPos.getX(), targetPos.getY() + 256, targetPos.getZ());
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerAttackEntity(AttackEntityEvent event) {
        Entity target = event.getTarget();
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            Entity attacker = event.getEntity();
            if (attacker instanceof Player) {
                Player player = (Player) attacker;
                ItemStack heldItem = player.getMainHandItem();
                if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FLOATING, heldItem) > 0) {
                    applyEffects(livingTarget);
                    BlockPos targetPos = target.getOnPos();
                    livingTarget.teleportTo(targetPos.getX(), targetPos.getY() + 256, targetPos.getZ());
                }
            }
        }
    }

    private static void applyEffects(LivingEntity entity) {
        MobEffect levitation = MobEffects.LEVITATION;
        entity.addEffect(new MobEffectInstance(levitation, 200, 2));
    }
}
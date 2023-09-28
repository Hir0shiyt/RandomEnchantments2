package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class ChaosStrike extends Enchantment {
    public ChaosStrike(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 15 + level * 2;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.chaosStrikeConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.chaosStrikeConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.chaosStrikeConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.chaosStrikeConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        Random random = new Random();
        MobEffect[] effects = {
                MobEffects.POISON,
                MobEffects.BLINDNESS,
                MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.LEVITATION,
                MobEffects.REGENERATION,
                MobEffects.DAMAGE_BOOST,
                MobEffects.WEAKNESS
        };

        int triggerChance = level * 10;
        if (random.nextInt(100) < triggerChance) {
            MobEffect selectedEffect = effects[random.nextInt(effects.length)];
            int duration = 20 * (10 + random.nextInt(20));
            int amplifier = random.nextInt(3);
            MobEffectInstance effectInstance = new MobEffectInstance(selectedEffect, duration, amplifier);
            if (target instanceof LivingEntity) {
                ((LivingEntity) target).addEffect(effectInstance);
            }
        }
    }
}

package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.MultiShotEnchantment;
import net.minecraft.world.level.levelgen.structure.templatesystem.AxisAlignedLinearPosTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Discord extends Enchantment {
    public Discord(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.discordConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) {
            return ModConfig.discordConfig.isEnabled.get();
        } else {
            return false;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.discordConfig.isEnabled.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.discordConfig.isEnabled.get() && ModConfig.discordConfig.isTradeable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.discordConfig.isEnabled.get() && ModConfig.discordConfig.isTreasureOnly.get();
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof MultiShotEnchantment) &&
                super.checkCompatibility(enchantment);
    }

    private static boolean handled = false;

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if (handled) {
            handled = false;
            return;
        }
        if (!(target instanceof LivingEntity)) return;
        int r = 64;
        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();
        List<Entity> aggro = target.level.getEntitiesOfClass(Entity.class, new AABB(x- r, y - r, z - r, x + r, y + r, z + r));
        for (Entity triggered : aggro) {
            if (triggered instanceof LivingEntity) {
                ((LivingEntity) triggered).setLastHurtByMob((LivingEntity) target);
            }
        }
        handled = true;
    }
}
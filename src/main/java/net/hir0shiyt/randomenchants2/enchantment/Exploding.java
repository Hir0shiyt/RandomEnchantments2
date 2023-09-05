package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Exploding extends Enchantment {
    public Exploding(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 5 + 10 * (level - 1);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.explodingConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) {
            return ModConfig.explodingConfig.isEnabled.get();
        } else {
            return false;
        }
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.explodingConfig.isEnabled.get() && ModConfig.explodingConfig.isTradeable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.explodingConfig.isEnabled.get() && ModConfig.explodingConfig.isTreasureOnly.get();
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Torches) &&
                !(enchantment instanceof Teleportation) &&
                !(enchantment instanceof Transposition) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            ItemStack heldItem = player.getMainHandItem();
            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.EXPLODING, heldItem) > 0) {
                float explosionSize = 1.0f + (float) EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.EXPLODING, heldItem);
                Entity damagedEntity = event.getEntityLiving();
                damagedEntity.level.explode(null, damagedEntity.getX(), damagedEntity.getY(), damagedEntity.getZ(), explosionSize, Explosion.BlockInteraction.NONE);
            }
        }
    }
}
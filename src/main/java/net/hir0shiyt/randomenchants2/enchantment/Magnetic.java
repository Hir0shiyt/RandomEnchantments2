package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.MultiShotEnchantment;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Magnetic extends Enchantment {

    public Magnetic(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Randomness) &&
                !(enchantment instanceof MultiShotEnchantment) &&
                super.checkCompatibility(enchantment);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.magneticConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.ServerConfig.magneticConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.magneticConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.magneticConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @SubscribeEvent
    public static void onEnemyKilled(LivingDropsEvent e) {
        Entity attacker = e.getSource().getDirectEntity();

        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGNETIC.get(), player.getMainHandItem()) > 0) {
                List<ItemStack> stacks = getStacksFromEntityItems(e.getDrops());

                for (ItemEntity itemEntity : e.getDrops()) {
                    if (player.addItem(itemEntity.getItem())) {
                        stacks.remove(itemEntity.getItem());
                    }
                }

            }
        }
        else if (attacker instanceof AbstractArrow) {
            AbstractArrow arrow = (AbstractArrow) attacker;
            Entity shooter = arrow.getOwner();

            if (shooter instanceof Player) {
                Player player = (Player) shooter;
                ItemStack heldItem = player.getMainHandItem();

                if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGNETIC.get(), heldItem) > 0) {
                    List<ItemStack> stacks = getStacksFromEntityItems(e.getDrops());

                    for (ItemEntity itemEntity : e.getDrops()) {
                        if (player.addItem(itemEntity.getItem())) {
                            stacks.remove(itemEntity.getItem());
                        }
                    }
                }
            }
        }
    }

    public static List<ItemStack> getStacksFromEntityItems(Collection<ItemEntity> l) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemEntity item : l) {
            stacks.add(item.getItem());
        }
        return stacks;
    }
}
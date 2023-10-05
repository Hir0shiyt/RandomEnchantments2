package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Resistant extends Enchantment {
    public Resistant(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.ServerConfig.resistantConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.ServerConfig.resistantConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.resistantConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.resistantConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @SubscribeEvent
    public static void itemSpawn(ItemTossEvent event) {
        ItemEntity entityItem = event.getEntity();
        ItemStack stack = entityItem.getItem();
        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RESISTANT.get(), stack) > 0) {
            entityItem.setInvulnerable(true);
        }
    }

    @SubscribeEvent
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        Iterator<Entity> iterator = event.getAffectedEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity instanceof ItemEntity) {
                ItemEntity itemEntity = (ItemEntity) entity;
                ItemStack stack = itemEntity.getItem();
                if (!stack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RESISTANT.get(), stack) > 0) {
                    iterator.remove();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();

        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RESISTANT.get(), heldItem) > 0) {
            event.setExpToDrop(0);
        }
    }
}
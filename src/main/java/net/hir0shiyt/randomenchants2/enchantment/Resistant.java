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
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
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
        return ModConfig.resistantConfig.isEnabled.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.resistantConfig.isEnabled.get() && ModConfig.resistantConfig.canApplyAtEnchantingTable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.resistantConfig.isEnabled.get() && ModConfig.resistantConfig.isTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.resistantConfig.isEnabled.get() && ModConfig.resistantConfig.isTradeable.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.resistantConfig.isEnabled.get();
    }

    @SubscribeEvent
    public static void itemSpawn(ItemTossEvent event) {
        ItemEntity entityItem = event.getEntityItem();
        ItemStack stack = entityItem.getItem();
        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RESISTANT, stack) > 0) {
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
                if (!stack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RESISTANT, stack) > 0) {
                    iterator.remove();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();

        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RESISTANT, heldItem) > 0) {
            event.setExpToDrop(0);
        }
    }
}
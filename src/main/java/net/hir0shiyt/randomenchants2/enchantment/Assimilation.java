package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Assimilation extends Enchantment {
    public Assimilation(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.ServerConfig.assimilationConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof Item) {
            return ModConfig.ServerConfig.assimilationConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
        } else {
            return false;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.assimilationConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.assimilationConfig.get() == ModConfig.Restriction.ANVIL;
    }

    public static void repair(Player player, List<ItemStack> drops) {
        Iterator<ItemStack> iterator = drops.iterator();
        ItemStack tool = player.getMainHandItem();
        while (iterator.hasNext()) {
            ItemStack stack = iterator.next();
            int fuelValue = (int) Math.ceil(ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) / 50.0D);
            if (fuelValue >= 0) {
                int toRepair = tool.getDamageValue();
                if (toRepair >= fuelValue) {
                    tool.setDamageValue(toRepair - fuelValue);
                    iterator.remove();
                }
            }
        }
    }
}
package net.hir0shiyt.randomenchants2.util;

import net.hir0shiyt.randomenchants2.enchantment.Assimilation;
import net.hir0shiyt.randomenchants2.enchantment.ModEnchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class AssimilationEventHandler {
    public static List<ItemStack> assimilationActivate(LootTable table, LootContext context) {
        Entity entity = context.getParam(LootContextParams.THIS_ENTITY);
        ItemStack tool = context.getParam(LootContextParams.TOOL);
        List<ItemStack> contents = table.getRandomItems(context);
        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (EnchantUtils.hasEnch(tool, ModEnchantments.ASSIMILATION)) {
                Assimilation.repair(player, contents);
            }
        }

        return contents;
    }
}

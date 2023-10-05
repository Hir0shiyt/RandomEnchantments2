package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Harvest extends Enchantment {
    public Harvest(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 25;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.harvestConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.harvestConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.harvestConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.harvestConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @SubscribeEvent
    public static void arrowHit(ProjectileImpactEvent event) {
        Entity projectile = event.getEntity();
        if (!(projectile instanceof AbstractArrow)) return;
        HitResult result = event.getRayTraceResult();
        AbstractArrow arrow = (AbstractArrow) projectile;
        if (!(result instanceof BlockHitResult)) return;
        BlockPos pos = ((BlockHitResult) result).getBlockPos();
        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof Player)) return;
        Player player = (Player) shooter;
        if (!EnchantUtils.hasEnch(player, ModEnchantments.HARVEST.get())) return;
        Block plant = projectile.level.getBlockState(pos).getBlock();
        if (!(isPlant(plant))) return;

        if (player.mayUseItemAt(pos, null, ItemStack.EMPTY))
            projectile.level.destroyBlock(pos, true);
        event.setCanceled(true);
    }

    private static boolean isPlant(Block plant) {
        return plant instanceof MelonBlock ||
                plant instanceof ChorusPlantBlock ||
                plant instanceof CocoaBlock ||
                plant instanceof PumpkinBlock ||
                plant instanceof CactusBlock;
    }
}

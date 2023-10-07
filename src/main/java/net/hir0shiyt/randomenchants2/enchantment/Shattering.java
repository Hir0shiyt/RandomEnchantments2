package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MultiShotEnchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Shattering extends Enchantment {
    public Shattering(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.ServerConfig.ricochetConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.ServerConfig.ricochetConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.shatteringConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.ricochetConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof MultiShotEnchantment) &&
                !(enchantment instanceof Teleportation) &&
                !(enchantment instanceof Ricochet) &&
                !(enchantment instanceof Phasing) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void arrowHit(ProjectileImpactEvent event) {
        HitResult result = event.getRayTraceResult();
        if (!(result instanceof BlockHitResult)) return;
        Entity arrow = event.getEntity();
        if (!(arrow instanceof AbstractArrow)) return;
        Entity shooter = ((AbstractArrow) arrow).getOwner();
        if (!(shooter instanceof Player)) return;
        Player player = (Player) ((AbstractArrow) arrow).getOwner();
        if (player == null) return;
        if (!EnchantUtils.hasEnch(player.getMainHandItem(), ModEnchantments.SHATTERING)) return;
        BlockPos pos = ((BlockHitResult) result).getBlockPos();
        Block glass = arrow.level.getBlockState(pos).getBlock();
        if (!(glass instanceof GlassBlock || glass instanceof StainedGlassBlock || glass instanceof StainedGlassPaneBlock)) return;
        arrow.getCommandSenderWorld().destroyBlock(pos, true);
        event.setCanceled(true);
    }
}
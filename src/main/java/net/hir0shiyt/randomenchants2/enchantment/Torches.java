package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Torches extends Enchantment {
    public Torches(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
    public boolean isAllowedOnBooks() {
        return ModConfig.torchesConfig.isEnabled.get();
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.torchesConfig.isEnabled.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.torchesConfig.isEnabled.get() && ModConfig.torchesConfig.canApplyAtEnchantingTable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.teleportationConfig.isEnabled.get() && ModConfig.teleportationConfig.isTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.teleportationConfig.isEnabled.get() && ModConfig.torchesConfig.isTradeable.get();
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof ArrowInfiniteEnchantment) &&
                !(enchantment instanceof Teleportation) &&
                !(enchantment instanceof MultiShotEnchantment) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void onBlockHit(ProjectileImpactEvent e) {
        Entity arrow = e.getEntity();
        if (!(arrow instanceof AbstractArrow)) return;
        BlockHitResult result = (BlockHitResult) e.getRayTraceResult();
        if (!(result instanceof BlockHitResult)) return;
        Entity shooter = ((AbstractArrow) arrow).getOwner();
        if (!(shooter instanceof LivingEntity)) return;
        if (result.getType() == BlockHitResult.Type.MISS) return;
        LivingEntity user = (LivingEntity) ((AbstractArrow) arrow).getOwner();
        if (shooter instanceof Player) {
            Player player = (Player) shooter;
            ItemStack heldItem = player.getMainHandItem();
            if (user == null) return;
            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.TORCHES, heldItem) > 0) return;
            Level level = arrow.level;

            if (!level.isClientSide) {
                BlockPos pos = result.getBlockPos().relative(result.getDirection());
                BlockState blockState;
                if (result.getDirection() == Direction.UP) {
                    blockState = Blocks.TORCH.defaultBlockState();
                } else if (result.getDirection() != Direction.DOWN) {
                    blockState = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, result.getDirection());
                } else {
                    return;
                }

                ItemStack arrowStack = new ItemStack(Items.ARROW, 1);
                player.getInventory().removeItem(arrowStack);

                if (level.getBlockState(pos).getMaterial().isReplaceable()) {
                    level.setBlockAndUpdate(pos, blockState);
                    arrow.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        }
    }
}

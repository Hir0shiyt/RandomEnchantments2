package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
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
        return ModConfig.torchesConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem || stack.getItem() instanceof TridentItem) {
            return ModConfig.torchesConfig.isEnabled.get();
        } else {
            return false;
        }
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
        if (arrow instanceof ThrownEgg ||
                arrow instanceof Snowball ||
                arrow instanceof ThrownEnderpearl ||
                arrow instanceof ThrownPotion ||
                arrow instanceof ThrownExperienceBottle ||
                arrow instanceof FishingHook ||
                arrow instanceof FireworkRocketEntity) {
            return;
        }

        LivingEntity shooter = null;

        if (arrow instanceof AbstractArrow) {
            shooter = (LivingEntity) ((AbstractArrow) arrow).getOwner();
        } else if (arrow instanceof ThrowableProjectile) {
            shooter = (LivingEntity) ((ThrowableProjectile) arrow).getOwner();
        }

        if (shooter instanceof Player) {
            Player player = (Player) shooter;
            ItemStack heldItem = player.getMainHandItem();

            if (!heldItem.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.TORCHES, heldItem) > 0) {
                HitResult result = e.getRayTraceResult();

                if (result instanceof BlockHitResult) {
                    BlockHitResult blockHitResult = (BlockHitResult) result;
                    Level level = arrow.level;
                    BlockPos pos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
                    BlockState blockState;

                    if (!level.isClientSide) {
                        if (blockHitResult.getDirection() == Direction.UP) {
                            blockState = Blocks.TORCH.defaultBlockState();
                        } else if (blockHitResult.getDirection() != Direction.DOWN) {
                            blockState = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, blockHitResult.getDirection());
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
                } else if (result instanceof EntityHitResult) {
                    EntityHitResult entityHitResult = (EntityHitResult) result;
                    Entity entity = entityHitResult.getEntity();

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.setSecondsOnFire(8);
                    }
                }
            }
        }
    }
}
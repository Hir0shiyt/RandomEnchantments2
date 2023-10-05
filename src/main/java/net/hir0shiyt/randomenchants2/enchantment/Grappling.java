package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Grappling extends Enchantment {
    public Grappling(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 30;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.grapplingConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof FishingRodItem) {
            return ModConfig.ServerConfig.grapplingConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
        } else {
            return false;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.grapplingConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.grapplingConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.getItem() instanceof FishingRodItem && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.GRAPPLING.get(), heldItem) > 0) {
            FishingHook hookEntity = player.fishing;

            if (hookEntity != null && hookEntity.isOnGround()) {
                Vec3 hookPos = hookEntity.position();
                Vec3 playerPos = player.position();

                double dx = hookPos.x - playerPos.x;
                double dy = hookPos.y - playerPos.y;
                double dz = hookPos.z - playerPos.z;

                    player.setDeltaMovement(dx, dy, dz);
                    player.hurtMarked = true;

                BlockPos blockPos = new BlockPos(hookPos);
                BlockState blockState = player.level.getBlockState(blockPos);

                if(!blockState.isAir()) {
                    player.setDeltaMovement(dx, dy, dz);
                    player.hurtMarked = true;
                }
            }
        }
    }
}
package net.hir0shiyt.randomenchants2.enchantment;

import com.mojang.math.Vector3d;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

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
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.grapplingConfig.isEnabled.get() && ModConfig.grapplingConfig.canApplyAtEnchantingTable.get() && this.category.equals(EnchantmentCategory.FISHING_ROD);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.grapplingConfig.isEnabled.get() && this.category.equals(EnchantmentCategory.FISHING_ROD);
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.grapplingConfig.isEnabled.get() && ModConfig.grapplingConfig.isTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.grapplingConfig.isEnabled.get() && ModConfig.grapplingConfig.isTradeable.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.grapplingConfig.isEnabled.get();
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
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
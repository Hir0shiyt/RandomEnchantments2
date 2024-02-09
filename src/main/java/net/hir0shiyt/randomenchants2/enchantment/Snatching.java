package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Snatching extends Enchantment {
    public Snatching(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.ServerConfig.snatchingConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return ModConfig.ServerConfig.snatchingConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.snatchingConfig.get() == ModConfig.Restriction.ENABLED;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.snatchingConfig.get() == ModConfig.Restriction.TREASURE;
    }

    private static final List<EquipmentSlot> ARMOR_SLOTS = new ArrayList<>();

    static {
        ARMOR_SLOTS.add(EquipmentSlot.HEAD);
        ARMOR_SLOTS.add(EquipmentSlot.CHEST);
        ARMOR_SLOTS.add(EquipmentSlot.LEGS);
        ARMOR_SLOTS.add(EquipmentSlot.FEET);
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.getItem() instanceof FishingRodItem && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SNATCHING, heldItem) > 0) {
            if (player.fishing == null || player.level.isClientSide) return;

            Entity hookedEntity = player.fishing.getHookedIn();
            if (!(hookedEntity instanceof LivingEntity)) return;

            LivingEntity victim = (LivingEntity) hookedEntity;
            List<ItemStack> armorPieces = removeArmor(victim);

            if (!armorPieces.isEmpty()) {
                Level world = player.level;
                BlockPos dropPos = victim.blockPosition();
                Random random = world.random;

                for (ItemStack armorPiece : armorPieces) {
                    ItemEntity entityItem = new ItemEntity(world, dropPos.getX(), dropPos.getY(), dropPos.getZ(), armorPiece);
                    Vec3 velocityVector = victim.position().subtract(player.position()).normalize().scale(0.5 + random.nextDouble());
                    entityItem.setDeltaMovement(velocityVector);
                    world.addFreshEntity(entityItem);
                }
            }
        }
    }

    public static List<ItemStack> removeArmor(LivingEntity victim) {
        List<ItemStack> armorPieces = new ArrayList<>();

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack armorPiece = victim.getItemBySlot(slot);

                if (!armorPiece.isEmpty() && armorPiece.isDamageableItem()) {
                    armorPieces.add(armorPiece.copy());
                    victim.setItemSlot(EquipmentSlot.MAINHAND, armorPiece.copy());
                    victim.setItemSlot(slot, ItemStack.EMPTY);
                    break;
                }
            }
        }
        return armorPieces;
    }
}
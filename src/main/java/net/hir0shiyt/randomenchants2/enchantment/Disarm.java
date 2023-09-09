package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Disarm extends Enchantment {
    public Disarm(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
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
        return ModConfig.disarmConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) {
            return ModConfig.disarmConfig.isEnabled.get();
        } else {
            return false;
        }
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.disarmConfig.isEnabled.get() && ModConfig.disarmConfig.isTradeable.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.disarmConfig.isEnabled.get() && ModConfig.disarmConfig.isTreasureOnly.get();
    }

    @SubscribeEvent
    public static void onPlayerAttackEntity(AttackEntityEvent event) {
        if (event.getTarget() instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) event.getTarget();
            ItemStack targetItem = target.getMainHandItem();
            if (!targetItem.isEmpty() && EnchantUtils.hasEnch(event.getPlayer().getMainHandItem(), ModEnchantments.DISARM)) {
                event.getPlayer().addItem(targetItem.copy());
                target.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        AbstractArrow arrow = (AbstractArrow) event.getEntity();
        HitResult hitResult = event.getRayTraceResult();
        Entity shooter = arrow.getOwner();

        if (hitResult instanceof EntityHitResult) {
            LivingEntity target = (LivingEntity) ((EntityHitResult) hitResult).getEntity();
            if (shooter instanceof Player) {
                Player player = (Player) shooter;
                ItemStack bowStack = player.getItemInHand(InteractionHand.MAIN_HAND);

                if (bowStack.getItem() instanceof BowItem || bowStack.getItem() instanceof CrossbowItem && EnchantUtils.hasEnch(player, ModEnchantments.DISARM)) {
                    player.addItem(target.getMainHandItem().copy());
                    target.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }
    }
}
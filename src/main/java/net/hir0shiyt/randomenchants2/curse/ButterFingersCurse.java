package net.hir0shiyt.randomenchants2.curse;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.enchantment.ModEnchantments;
import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class ButterFingersCurse extends Enchantment {
    public ButterFingersCurse(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 25;
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.butterFingersConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.butterFingersConfig.get() == ModConfig.Restriction.TREASURE;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public void  doPostAttack(LivingEntity user, Entity target, int level) {
        if (!(user instanceof Player)) return;
        Player player = (Player) user;
        if (Math.random()>.50) return;
        player.drop(player.getItemBySlot(EquipmentSlot.MAINHAND), true);
        player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        ItemEntity itemStack = new ItemEntity(player.getCommandSenderWorld(), player.getX(), player.getY(), player.getZ(), player.getMainHandItem());
        player.getCommandSenderWorld().addFreshEntity(itemStack);
    }

    @SubscribeEvent
    public static void diggingEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (EnchantUtils.hasEnch(player.getMainHandItem(), ModEnchantments.BUTTER_FINGERS_CURSE.get())) {
            if (event.getResult() == Event.Result.DEFAULT) {
                if (Math.random() > .50) return;
                player.drop(player.getMainHandItem(), true);
                player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                ItemEntity itemStack = new ItemEntity(player.getCommandSenderWorld(), player.getX(), player.getY(), player.getZ(), player.getMainHandItem());
                player.getCommandSenderWorld().addFreshEntity(itemStack);
            }
        }
    }
}
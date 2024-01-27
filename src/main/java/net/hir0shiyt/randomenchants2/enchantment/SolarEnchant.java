package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class SolarEnchant extends Enchantment {

    private static final int REPAIR_COOLDOWN = 20;

    public SolarEnchant(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 5 + (level - 1) * 10;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.solarEnchantConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.solarEnchantConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.solarEnchantConfig.get() == ModConfig.Restriction.ENABLED;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.solarEnchantConfig.get() == ModConfig.Restriction.TREASURE;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Randomness) &&
                !(enchantment instanceof MendingEnchantment) &&
                !(enchantment instanceof Eternal) &&
                !(enchantment instanceof Torches) &&
                super.checkCompatibility(enchantment);
    }

    @SubscribeEvent
    public static void applySolarEnchant(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.getCommandSenderWorld().isClientSide())
            return;

        Level world = event.player.getCommandSenderWorld();
        long time = world.getDayTime();
        boolean isDaytime = time >= 0 && time < 12000;

        ItemStack mainHandStack = event.player.getMainHandItem();
        int mainHandLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SOLAR_ENCHANT.get(), mainHandStack);

        if (mainHandStack.isDamaged() && mainHandLevel > 0) {
            BlockPos playerPos = event.player.blockPosition();
            int skyLight = world.getBrightness(LightLayer.SKY, playerPos);
            int blockLight = world.getBrightness(LightLayer.BLOCK, playerPos);

            if (isDaytime || (skyLight >= 8 && blockLight >= 8)) {
                if (event.player.tickCount % REPAIR_COOLDOWN == 0) {
                    int repairAmount = mainHandLevel * 2;
                    mainHandStack.setDamageValue(Math.max(0, mainHandStack.getDamageValue() - repairAmount));
                }
            }
        }
    }
}
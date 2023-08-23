package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class SolarEnchant extends Enchantment {

    @ObjectHolder(RandomEnchants2.MOD_ID + ":solar_enchant")
    public static final Enchantment SOLAR_ENCHANT = null;
    private static final int REPAIR_COOLDOWN = 20;

    public SolarEnchant(Rarity veryRare, EnchantmentCategory breakable, EquipmentSlot mainhand, EquipmentSlot offhand) {
        super(Rarity.VERY_RARE, RandomEnchants2.PICKAXE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
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
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof Randomness) &&
                !(enchantment instanceof MendingEnchantment) &&
                !(enchantment instanceof Eternal) &&
                super.checkCompatibility(enchantment);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.solarEnchantConfig.isEnabled.get() && ModConfig.solarEnchantConfig.canApplyAtEnchantingTable.get();
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack stack) {
        return ModConfig.solarEnchantConfig.isEnabled.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.solarEnchantConfig.isEnabled.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.solarEnchantConfig.isEnabled.get() && ModConfig.solarEnchantConfig.isTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.solarEnchantConfig.isEnabled.get() && ModConfig.solarEnchantConfig.isTradeable.get();
    }

    @SubscribeEvent
    public static void applySolarEnchant(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level.isClientSide())
            return;

        Level world = event.player.level;
        long time = world.getDayTime();
        boolean isDaytime = time >= 0 && time < 12000;

        // Check if the item is in the main hand and the enchantment is enabled
        ItemStack mainHandStack = event.player.getMainHandItem();
        int mainHandLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SOLAR_ENCHANT.get(), mainHandStack);

        if (mainHandStack.isDamaged() && mainHandLevel > 0) {
            BlockPos playerPos = event.player.blockPosition();
            int skyLight = world.getBrightness(LightLayer.SKY, playerPos); // Skylight
            int blockLight = world.getBrightness(LightLayer.BLOCK, playerPos); // Block light

            if (isDaytime || (skyLight >= 8 && blockLight >= 8)) {
                if (event.player.tickCount % REPAIR_COOLDOWN == 0) {
                    int repairAmount = mainHandLevel * 2;
                    mainHandStack.setDamageValue(Math.max(0, mainHandStack.getDamageValue() - repairAmount));
                }
            }
        }
    }
}
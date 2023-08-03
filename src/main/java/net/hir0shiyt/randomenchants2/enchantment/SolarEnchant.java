package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class SolarEnchant extends Enchantment {

    @ObjectHolder(RandomEnchants2.MOD_ID + ":solar_enchant")
    public static final Enchantment SOLAR_ENCHANT = null;
    private static final int REPAIR_COOLDOWN = 20;

    public SolarEnchant(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
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

    @SubscribeEvent
    public static void applySolarEnchant(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level.isClientSide())
            return;

        Level world = event.player.level;
        long time = world.getDayTime();
        boolean isDaytime = time >= 0 && time < 12000; // Daytime if the time is between 0 and 12000 (exclusive)

        // Check if the item is in the main hand and the enchantment is enabled
        ItemStack mainHandStack = event.player.getMainHandItem();
        int mainHandLevel = EnchantmentHelper.getItemEnchantmentLevel(SOLAR_ENCHANT, mainHandStack);

        if (mainHandStack.isDamaged() && mainHandLevel > 0) {
            BlockPos playerPos = event.player.blockPosition();
            int skyLight = world.getBrightness(LightLayer.SKY, playerPos); // Sky light
            int blockLight = world.getBrightness(LightLayer.BLOCK, playerPos); // Block light

            // If it's day or there's enough light around the player, repair the item
            if (isDaytime || (skyLight >= 8 && blockLight >= 8)) {
                if (event.player.tickCount % REPAIR_COOLDOWN == 0) {
                    int repairAmount = mainHandLevel * 2;
                    mainHandStack.setDamageValue(Math.max(0, mainHandStack.getDamageValue() - repairAmount));
                }
            }
        }
    }
}
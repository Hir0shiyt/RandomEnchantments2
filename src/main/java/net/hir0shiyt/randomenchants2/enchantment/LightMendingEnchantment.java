package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class EnchantmentSolar extends Enchantment {
    @ObjectHolder(RandomEnchants2.MOD_ID + "light_mending_enchant")
    public static final Enchantment LIGHT_MENDING_ENCHANT = null;

    public EnchantmentSolar() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.setRegistryName(RandomEnchants2.MOD_ID, "light_mending_enchant");
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
    public static void applySolar(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START || event.player.level.isClientSide())
            return;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = event.player.getItemBySlot(slot);
            if (!stack.isEmpty() && stack.isDamaged()) {
                int level = EnchantmentHelper.getItemEnchantmentLevel(LIGHT_MENDING_ENCHANT, stack);
                if (level > 0 && event.player.level.getBrightness(LightLayer.BLOCK) >= 15) {
                    int repairAmount = level * 2;
                    stack.setDamageValue(Math.max(0, stack.getDamageValue() - repairAmount));
                }
            }
        }
    }
}
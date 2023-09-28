package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class EtherealEmbrace extends Enchantment {
    public EtherealEmbrace(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }
    private static final Lazy<Random> RANDOM = Lazy.of(Random::new);

    @Override
    public int getMinCost(int level) {
        return 15 + level * 2;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.etherealEmbraceConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.etherealEmbraceConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.etherealEmbraceConfig.get() == ModConfig.Restriction.NORMAL;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.etherealEmbraceConfig.get() == ModConfig.Restriction.ANVIL;
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntityLiving() == null) { return; }
        ItemStack armorStack = event.getEntityLiving().getItemBySlot(EquipmentSlot.CHEST);
        if (EnchantUtils.hasEnch(armorStack, ModEnchantments.ETHEREAL_EMBRACE)) {
            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ETHEREAL_EMBRACE, armorStack);
            double phasingChance = enchantmentLevel * 0.05;
            if (RANDOM.get().nextDouble() <= phasingChance) {
                event.setCanceled(true);
            }
        }
    }
}

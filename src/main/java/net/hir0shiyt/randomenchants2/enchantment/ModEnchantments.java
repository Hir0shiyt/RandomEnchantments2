package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, RandomEnchants2.MOD_ID);

    public static final RegistryObject<Enchantment> SOLAR_ENCHANT =
            ENCHANTMENTS.register("solar_enchant",
                    () -> new SolarEnchant(Enchantment.Rarity.VERY_RARE,
                            EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));

    public static final RegistryObject<Enchantment> OBSIDIAN_BUSTER =
            ENCHANTMENTS.register("obsidian_buster",
                    () -> new ObsidianBuster(Enchantment.Rarity.RARE,
                            EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> RANDOMNESS =
            ENCHANTMENTS.register("randomness",
                    () -> new Randomness(Enchantment.Rarity.VERY_RARE,
                            EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
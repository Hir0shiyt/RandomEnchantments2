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
                            RandomEnchants2.PICKAXE, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> RANDOMNESS =
            ENCHANTMENTS.register("randomness",
                    () -> new Randomness(Enchantment.Rarity.VERY_RARE,
                            EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> MAGNETIC =
            ENCHANTMENTS.register("magnetic",
                    () -> new Magnetic(Enchantment.Rarity.RARE,
                            EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> STONE_LOVER =
            ENCHANTMENTS.register("stone_lover",
                    () -> new StoneLover(Enchantment.Rarity.RARE,
                            RandomEnchants2.PICKAXE, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> ETERNAL =
            ENCHANTMENTS.register("eternal",
                    () -> new Eternal(Enchantment.Rarity.VERY_RARE,
                            EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> DUNGEONEERING =
            ENCHANTMENTS.register("dungeoneering",
                    () -> new Dungeoneering(Enchantment.Rarity.VERY_RARE,
                            RandomEnchants2.PICKAXE, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> GRAPPLING =
            ENCHANTMENTS.register("grappling",
                    () -> new Grappling(Enchantment.Rarity.RARE,
                            EnchantmentCategory.FISHING_ROD, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> SNATCHING =
            ENCHANTMENTS.register("snatching",
                    () -> new Snatching(Enchantment.Rarity.RARE,
                            EnchantmentCategory.FISHING_ROD, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> RESISTANT =
            ENCHANTMENTS.register("resistant",
                    () -> new Resistant(Enchantment.Rarity.RARE,
                            EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, RandomEnchants2.MOD_ID);

    public static RegistryObject<Enchantment> LIGHT_MENDING_ENCHANTMENT =
            ENCHANTMENTS.register("light_mending_enchantment",
                    () -> new LightMendingEnchantment(Enchantment.Rarity.VERY_RARE,
                            EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND,
                            CreativeModeTab.TAB_MISC));


    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}

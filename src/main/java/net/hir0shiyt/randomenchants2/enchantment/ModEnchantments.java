package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.enchantment.Enchantment;


@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, RandomEnchants2.MOD_ID);

    public static final RegistryObject<Enchantment> LIGHT_MENDING_ENCHANTMENT =
            ENCHANTMENTS.register("light_mending_enchant", EnchantmentLightMendingEnchant::new);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}

package net.hir0shiyt.randomenchants2;

import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.enchantment.ModEnchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static net.hir0shiyt.randomenchants2.RandomEnchants2.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
@Mod(value = MOD_ID)
public class RandomEnchants2 {
    public static final String MOD_ID = "randomenchants2";

    public RandomEnchants2() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEnchantments.register(eventBus);

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, ModConfig.COMMON_SPEC, "randomenchants2-server.toml");

    }
}
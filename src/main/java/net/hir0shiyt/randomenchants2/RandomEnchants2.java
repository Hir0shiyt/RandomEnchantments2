package net.hir0shiyt.randomenchants2;

import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.enchantment.ModEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

import static net.hir0shiyt.randomenchants2.RandomEnchants2.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
@Mod(value = MOD_ID)
public class RandomEnchants2 {

    public static final String MOD_ID = "randomenchants2";
    public static final Set<Enchantment> enchants = new HashSet<>();
    public static Logger logger = LogManager.getLogger(MOD_ID);

    public RandomEnchants2() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEnchantments.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, ModConfig.SERVER_SPEC, "randomenchants2-server.toml");
    }

    private void setup(final FMLCommonSetupEvent event) {
        logger.info("Random Enchants 2 is initializing");
        logger.info("Registered " + enchants.size() + " enchantments");
    }
}
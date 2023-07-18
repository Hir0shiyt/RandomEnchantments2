package net.hir0shiyt.randomenchants2;

import com.mojang.logging.LogUtils;
import net.hir0shiyt.randomenchants2.enchantment.ModEnchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(RandomEnchants2.MOD_ID)
public class RandomEnchants2 {
    public static final String MOD_ID = "randomenchants2";

    private static final Logger LOGGER = LogUtils.getLogger();


    public RandomEnchants2() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEnchantments.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
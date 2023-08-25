package net.hir0shiyt.randomenchants2;

import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.enchantment.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static net.hir0shiyt.randomenchants2.RandomEnchants2.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
@Mod(value = MOD_ID)
public class RandomEnchants2 {

    public static final String MOD_ID = "randomenchants2";
    public static final ArrayList<Item> itemList = new ArrayList<>();
    public static final EnchantmentCategory SWORDS_BOWS = EnchantmentCategory.create("weapons", item -> item instanceof SwordItem || item instanceof BowItem);

    public static final EnchantmentCategory PICKAXE = EnchantmentCategory.create("pickaxes", PickaxeItem.class::isInstance);
    public static final EnchantmentCategory SHOOTABLE = EnchantmentCategory.create("shootable", BowItem.class::isInstance);
    public static final EnchantmentCategory SHIELDS = EnchantmentCategory.create("shields", ShieldItem.class::isInstance);
    public static final EnchantmentCategory AXE = EnchantmentCategory.create("axes", AxeItem.class::isInstance);
    public static final EnchantmentCategory TOOLSANDWEAPONS = EnchantmentCategory.create("tools&weapons", item -> item instanceof SwordItem || item instanceof DiggerItem || item instanceof AxeItem);

    public static final Set<Enchantment> enchants = new HashSet<>();

    public RandomEnchants2() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEnchantments.register(eventBus);

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, ModConfig.SERVER_SPEC, "randomenchants2-server.toml");
    }
    @Nonnull
    public static EnchantmentCategory registerEnchantmentCategories(String name, Predicate<Item> condition) {
        return EnchantmentCategory.create(name, condition);
    }
}
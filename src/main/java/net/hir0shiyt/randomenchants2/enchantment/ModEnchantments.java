package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEnchantments {

    public static final EnchantmentCategory SWORDS_BOWS = EnchantmentCategory.create("weapons", item -> item instanceof SwordItem || item instanceof BowItem);
    public static final EnchantmentCategory PICKAXE = EnchantmentCategory.create("pickaxes", PickaxeItem.class::isInstance);
    public static final EnchantmentCategory SHOOTABLE = EnchantmentCategory.create("shootable", item -> item instanceof BowItem || item instanceof CrossbowItem);
    public static final EnchantmentCategory SHIELDS = EnchantmentCategory.create("shields", ShieldItem.class::isInstance);
    public static final EnchantmentCategory AXE = EnchantmentCategory.create("axes", AxeItem.class::isInstance);
    public static final EnchantmentCategory TOOLSANDWEAPONS = EnchantmentCategory.create("tools&weapons", item -> item instanceof SwordItem || item instanceof DiggerItem || item instanceof AxeItem);

    public static final Enchantment SOLAR_ENCHANT = new SolarEnchant(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    public static final Enchantment OBSIDIAN_BUSTER = new ObsidianBuster(Enchantment.Rarity.VERY_RARE, ModEnchantments.PICKAXE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment RANDOMNESS = new Randomness(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment MAGNETIC = new Magnetic(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment STONE_LOVER = new StoneLover(Enchantment.Rarity.RARE, ModEnchantments.PICKAXE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment RESISTANT = new Resistant(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment ETERNAL = new Eternal(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment DUNGEONEERING = new Dungeoneering(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment GRAPPLING = new Grappling(Enchantment.Rarity.RARE, EnchantmentCategory.FISHING_ROD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment SNATCHING = new Snatching(Enchantment.Rarity.RARE, EnchantmentCategory.FISHING_ROD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment TELEPORTATION = new Teleportation(Enchantment.Rarity.VERY_RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment TORCHES = new Torches(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment TRUE_SHOT = new TrueShot(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});


    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        register(event.getRegistry(), "solar_enchant", SOLAR_ENCHANT);
        register(event.getRegistry(), "obsidian_buster", OBSIDIAN_BUSTER);
        register(event.getRegistry(), "randomness", RANDOMNESS);
        register(event.getRegistry(), "magnetic", MAGNETIC);
        register(event.getRegistry(), "stone_lover", STONE_LOVER);
        register(event.getRegistry(), "resistant", RESISTANT);
        register(event.getRegistry(), "eternal", ETERNAL);
        register(event.getRegistry(), "dungeoneering", DUNGEONEERING);
        register(event.getRegistry(), "grappling", GRAPPLING);
        register(event.getRegistry(), "snatching", SNATCHING);
        register(event.getRegistry(), "teleportation", TELEPORTATION);
        register(event.getRegistry(), "torches", TORCHES);
        register(event.getRegistry(), "true_shot", TRUE_SHOT);
    }

    private static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, String name, T object) {
        object.setRegistryName(RandomEnchants2.getLocation(name));
        registry.register(object);
    }
}
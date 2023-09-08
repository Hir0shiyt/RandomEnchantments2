package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import static net.hir0shiyt.randomenchants2.RandomEnchants2.enchants;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEnchantments {

    public static final EnchantmentCategory SWORDS_BOWS = EnchantmentCategory.create("weapons", item -> item instanceof SwordItem || item instanceof BowItem || item instanceof CrossbowItem);
    public static final EnchantmentCategory PICKAXE = EnchantmentCategory.create("pickaxes", PickaxeItem.class::isInstance);
    public static final EnchantmentCategory SHOOTABLE = EnchantmentCategory.create("shootable", item -> item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem);
    public static final EnchantmentCategory SHIELDS = EnchantmentCategory.create("shields", ShieldItem.class::isInstance);
    public static final EnchantmentCategory AXE = EnchantmentCategory.create("axes", AxeItem.class::isInstance);
    public static final EnchantmentCategory TOOLSANDWEAPONS = EnchantmentCategory.create("tools&weapons", item -> item instanceof SwordItem || item instanceof AxeItem || item instanceof ShovelItem || item instanceof HoeItem || item instanceof BowItem || item instanceof CrossbowItem);

    public static final Enchantment SOLAR_ENCHANT = new SolarEnchant(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    public static final Enchantment OBSIDIAN_BUSTER = new ObsidianBuster(Enchantment.Rarity.VERY_RARE, ModEnchantments.PICKAXE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment RANDOMNESS = new Randomness(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment MAGNETIC = new Magnetic(Enchantment.Rarity.RARE, ModEnchantments.TOOLSANDWEAPONS, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment STONE_LOVER = new StoneLover(Enchantment.Rarity.RARE, ModEnchantments.PICKAXE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment RESISTANT = new Resistant(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment ETERNAL = new Eternal(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment DUNGEONEERING = new Dungeoneering(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment GRAPPLING = new Grappling(Enchantment.Rarity.RARE, EnchantmentCategory.FISHING_ROD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment SNATCHING = new Snatching(Enchantment.Rarity.RARE, EnchantmentCategory.FISHING_ROD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment TELEPORTATION = new Teleportation(Enchantment.Rarity.VERY_RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment TORCHES = new Torches(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment TRUE_SHOT = new TrueShot(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment EQUAL_MINE = new EqualMine(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment ASSIMILATION = new Assimilation(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment TRANSPOSITION = new Transposition(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment RICOCHET = new Ricochet(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment EXPLODING = new Exploding(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment BACK_TO_THE_CHAMBER = new BackToTheChamber(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment QUICK_DRAW = new QuickDraw(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment PHASING = new Phasing(Enchantment.Rarity.RARE, ModEnchantments.SHOOTABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment DISCORD = new Discord(Enchantment.Rarity.RARE, ModEnchantments.SWORDS_BOWS, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment SWIFT = new Swift(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

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
        register(event.getRegistry(), "equal_mine", EQUAL_MINE);
        register(event.getRegistry(), "assimilation", ASSIMILATION);
        register(event.getRegistry(), "transposition", TRANSPOSITION);
        register(event.getRegistry(), "ricochet", RICOCHET);
        register(event.getRegistry(), "exploding", EXPLODING);
        register(event.getRegistry(), "back_to_the_chamber", BACK_TO_THE_CHAMBER);
        register(event.getRegistry(), "quick_draw", QUICK_DRAW);
        register(event.getRegistry(), "phasing", PHASING);
        register(event.getRegistry(), "discord", DISCORD);
        register(event.getRegistry(), "swift", SWIFT);

        IForgeRegistry<Enchantment> r = event.getRegistry();

        for (Enchantment enchantment : enchants)
            r.register(enchantment);
        RandomEnchants2.logger.info("Registered" + enchants.size() + " enchantments!");

        if (false) {
            //For future enchantments
        }
    }

    private static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, String name, T object) {
        object.setRegistryName(RandomEnchants2.getLocation(name));
        registry.register(object);
    }
}
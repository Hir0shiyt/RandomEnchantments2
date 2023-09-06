package net.hir0shiyt.randomenchants2.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class ModConfig {
    public static ForgeConfigSpec SERVER_SPEC = null;
    public static final EnchantmentConfigEntry solarEnchantConfig;
    public static final EnchantmentConfigEntry obsidianBusterConfig;
    public static final EnchantmentConfigEntry randomnessConfig;
    public static final EnchantmentConfigEntry magneticConfig;
    public static final EnchantmentConfigEntry stoneLoverConfig;
    public static final EnchantmentConfigEntry eternalConfig;
    public static final EnchantmentConfigEntry dungeoneeringConfig;
    public static final EnchantmentConfigEntry grapplingConfig;
    public static final EnchantmentConfigEntry snatchingConfig;
    public static final EnchantmentConfigEntry resistantConfig;
    public static final EnchantmentConfigEntry teleportationConfig;
    public static final EnchantmentConfigEntry torchesConfig;
    public static final EnchantmentConfigEntry trueShotConfig;
    public static final EnchantmentConfigEntry equalMineConfig;
    public static final EnchantmentConfigEntry assimilationConfig;
    public static final EnchantmentConfigEntry transpositionConfig;
    public static final EnchantmentConfigEntry ricochetConfig;
    public static final EnchantmentConfigEntry explodingConfig;
    public static final EnchantmentConfigEntry backToTheChamberConfig;
    public static final EnchantmentConfigEntry quickDrawConfig;

    static {
        Builder builder = new ForgeConfigSpec.Builder();
        solarEnchantConfig = new EnchantmentConfigEntry(builder, "Solar Enchantment", true, true,  false);
        obsidianBusterConfig = new EnchantmentConfigEntry(builder, "Obsidian Buster Enchantment", true, false,  false);
        randomnessConfig = new EnchantmentConfigEntry(builder, "Randomness Enchantment", true, true,  false);
        magneticConfig = new EnchantmentConfigEntry(builder, "Magnetic Enchantment", true, false,  false);
        stoneLoverConfig = new EnchantmentConfigEntry(builder,"Stone Lover Enchantment",true, false,  false);
        eternalConfig = new EnchantmentConfigEntry(builder,"Eternal Enchantment",true, true,  false);
        dungeoneeringConfig = new EnchantmentConfigEntry(builder,"Dungeoneering Enchantment",true, true,  false);
        grapplingConfig = new EnchantmentConfigEntry(builder,"Grappling Enchantment", true, true,  false);
        snatchingConfig = new EnchantmentConfigEntry(builder, "Snatching Enchantment", true,true,false);
        resistantConfig = new EnchantmentConfigEntry(builder, "Resistant Enchantment", true,true,false);
        teleportationConfig = new EnchantmentConfigEntry(builder,"Teleportation Enchantment", true, false,  false);
        torchesConfig = new EnchantmentConfigEntry(builder,"Torches Enchantment", true, false, false);
        trueShotConfig = new EnchantmentConfigEntry(builder, "True Shot Enchantment",true,false,false);
        equalMineConfig = new EnchantmentConfigEntry(builder, "Equal Mine Enchantment", true, true,  false);
        assimilationConfig = new EnchantmentConfigEntry(builder, "Assimilation Enchantment", true, true,  false);
        transpositionConfig = new EnchantmentConfigEntry(builder, "Transposition Enchantment", true, false, false);
        ricochetConfig = new EnchantmentConfigEntry(builder, "Ricochet Enchantment", true, false, false);
        explodingConfig = new EnchantmentConfigEntry(builder, "Exploding Enchantment", true, false, false);
        backToTheChamberConfig = new EnchantmentConfigEntry(builder, "Back To The Chamber Enchantment", true, false, false);
        quickDrawConfig = new EnchantmentConfigEntry(builder, "Quick Draw Enchantment", true, false ,false);

        SERVER_SPEC = builder.build();
    }

    public static class EnchantmentConfigEntry {
        public final BooleanValue isEnabled;
        public final BooleanValue isTreasureOnly;
        public final BooleanValue isTradeable;

        public EnchantmentConfigEntry(Builder builder, String enchantmentName, boolean defaultValue, boolean defaultTreasureValue, boolean defaultIsTradeableValue) {
            builder.push(enchantmentName);
            isEnabled = builder.comment("Enable or disable the " + enchantmentName)
                    .define("Enabled", defaultValue);
            isTreasureOnly = builder.comment(enchantmentName + " is Only Treasure")
                            .define("TreasureOnly",defaultTreasureValue);
            isTradeable = builder.comment(enchantmentName + " is tradeable")
                            .define("isTradeable", defaultIsTradeableValue);
            builder.pop();
        }
    }
}
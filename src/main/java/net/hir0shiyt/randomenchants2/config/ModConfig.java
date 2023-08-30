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

    static {
        Builder builder = new ForgeConfigSpec.Builder();
        solarEnchantConfig = new EnchantmentConfigEntry(builder, "Solar Enchantment", true, true, false, false);
        obsidianBusterConfig = new EnchantmentConfigEntry(builder, "Obsidian Buster Enchantment", true, false, true, true);
        randomnessConfig = new EnchantmentConfigEntry(builder, "Randomness Enchantment", true, true, false, false);
        magneticConfig = new EnchantmentConfigEntry(builder, "Magnetic Enchantment", true, false, true, true);
        stoneLoverConfig = new EnchantmentConfigEntry(builder,"Stone Lover Enchantment",true, false, true, true);
        eternalConfig = new EnchantmentConfigEntry(builder,"Eternal Enchantment",true, true, false, false);
        dungeoneeringConfig = new EnchantmentConfigEntry(builder,"Dungeoneering Enchantment",true, true, false, false);
        grapplingConfig = new EnchantmentConfigEntry(builder,"Grappling Enchantment", true, true, false, false);
        snatchingConfig = new EnchantmentConfigEntry(builder, "Snatching Enchantment", true,true,false,false);
        resistantConfig = new EnchantmentConfigEntry(builder, "Resistant Enchantment", true,true,false,false);
        teleportationConfig = new EnchantmentConfigEntry(builder,"Teleportation Enchantment", true, false, true, false);


        SERVER_SPEC = builder.build();
    }

    public static class EnchantmentConfigEntry {
        public final BooleanValue isEnabled;
        public final BooleanValue isTreasureOnly;
        public final BooleanValue canApplyAtEnchantingTable;
        public final BooleanValue isTradeable;

        public EnchantmentConfigEntry(Builder builder, String enchantmentName, boolean defaultValue, boolean defaultTreasureValue, boolean defaultApplyAtEnchantingTableValue, boolean defaultIsTradeableValue) {
            builder.push(enchantmentName);
            isEnabled = builder.comment("Enable or disable the " + enchantmentName)
                    .define("Enabled", defaultValue);
            isTreasureOnly = builder.comment(enchantmentName + " is Only Treasure")
                            .define("TreasureOnly",defaultTreasureValue);
            canApplyAtEnchantingTable = builder.comment(enchantmentName + " can be applied in an enchanting table")
                            .define("canApplyAtEnchantingTable",defaultApplyAtEnchantingTableValue);
            isTradeable = builder.comment(enchantmentName + " is tradeable")
                            .define("isTradeable", defaultIsTradeableValue);
            builder.pop();
        }
    }
}
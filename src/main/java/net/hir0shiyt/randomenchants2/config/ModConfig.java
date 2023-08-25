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
    public static final EnchantmentConfigEntry snitchingConfig;

    static {
        Builder builder = new ForgeConfigSpec.Builder();
        solarEnchantConfig = new EnchantmentConfigEntry(builder, "solar_enchant", true, true, false, false);
        obsidianBusterConfig = new EnchantmentConfigEntry(builder, "obsidian_buster", true, false, true, true);
        randomnessConfig = new EnchantmentConfigEntry(builder, "randomness", true, true, false, false);
        magneticConfig = new EnchantmentConfigEntry(builder, "magnetic", true, false, true, true);
        stoneLoverConfig = new EnchantmentConfigEntry(builder,"stone_lover",true, false, true, true);
        eternalConfig = new EnchantmentConfigEntry(builder,"eternal",true, true, false, false);
        dungeoneeringConfig = new EnchantmentConfigEntry(builder,"dungeoneering",true, true, false, false);
        grapplingConfig = new EnchantmentConfigEntry(builder,"grappling", true, true, false, false);
        snitchingConfig = new EnchantmentConfigEntry(builder, "snitching", true,true,false,false);


        SERVER_SPEC = builder.build();
    }

    public static class EnchantmentConfigEntry {
        public final BooleanValue isEnabled;
        public final BooleanValue isTreasureOnly;
        public final BooleanValue canApplyAtEnchantingTable;
        public final BooleanValue isTradeable;

        public EnchantmentConfigEntry(Builder builder, String enchantmentName, boolean defaultValue, boolean defaultTreasureValue, boolean defaultApplyAtEnchantingTableValue, boolean defaultIsTradeableValue) {
            builder.push(enchantmentName);
            isEnabled = builder.comment("Enable or disable the " + enchantmentName + " Enchantment")
                    .define("Enabled", defaultValue);
            isTreasureOnly = builder.comment(enchantmentName + " is Only Treasure Enchantment")
                            .define("TreasureOnly",defaultTreasureValue);
            canApplyAtEnchantingTable = builder.comment(enchantmentName + " can be applied in an enchanting table")
                            .define("canApplyAtEnchantingTable",defaultApplyAtEnchantingTableValue);
            isTradeable = builder.comment(enchantmentName + " is tradeable")
                            .define("isTradeable", defaultIsTradeableValue);
            builder.pop();
        }
    }
}
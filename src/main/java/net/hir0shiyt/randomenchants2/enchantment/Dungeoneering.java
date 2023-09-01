package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber (modid = RandomEnchants2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Dungeoneering extends Enchantment {
    public Dungeoneering(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 15;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof SwordItem && stack.getItem() instanceof AxeItem) {
            return ModConfig.dungeoneeringConfig.isEnabled.get() && ModConfig.dungeoneeringConfig.canApplyAtEnchantingTable.get();
        } else {
            return false;
        }
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.dungeoneeringConfig.isEnabled.get() && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.dungeoneeringConfig.isEnabled.get() && ModConfig.dungeoneeringConfig.isTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return ModConfig.dungeoneeringConfig.isEnabled.get() && ModConfig.dungeoneeringConfig.isTradeable.get();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void addDungeonDrops(LivingDropsEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            ItemStack heldItem = player.getMainHandItem();
            LivingEntity victim = event.getEntityLiving();
            ServerLevel serverWorld = (ServerLevel) player.level;

            if (heldItem.getItem() instanceof SwordItem && !player.level.isClientSide()) {
                int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.DUNGEONEERING, heldItem);

                if (enchantmentLevel > 0) {
                    LootContext.Builder lootBuilder = new LootContext.Builder(serverWorld)
                            .withRandom(serverWorld.random)
                            .withParameter(LootContextParams.THIS_ENTITY, player)
                            .withParameter(LootContextParams.ORIGIN, victim.position())
                            .withParameter(LootContextParams.DAMAGE_SOURCE, event.getSource());

                    LootTable lootTable = serverWorld.getServer().getLootTables().get(new ResourceLocation("minecraft", "chests/simple_dungeon"));
                    List<ItemStack> generatedLoot = lootTable.getRandomItems(lootBuilder.create(LootContextParamSets.ENTITY));

                    int numberOfDrops = enchantmentLevel; // Number of items to drop based on enchantment level

                    for (int i = 0; i < numberOfDrops; i++) {
                        double chanceForDrop = 0.10; // The chance for this specific drop to appear

                        if (serverWorld.random.nextDouble() <= chanceForDrop) {
                            ItemStack stack = generatedLoot.get(serverWorld.random.nextInt(generatedLoot.size()));
                            event.getDrops().add(new ItemEntity(serverWorld, victim.getX(), victim.getY(), victim.getZ(), stack));
                        }
                    }
                }
            }
        }
    }
}

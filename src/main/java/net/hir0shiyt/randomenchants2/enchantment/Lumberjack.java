package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Lumberjack extends Enchantment {
    public Lumberjack(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 15;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.lumberjackConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.lumberjackConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.lumberjackConfig.get() == ModConfig.Restriction.ENABLED;
    }

    @Override
    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.lumberjackConfig.get() == ModConfig.Restriction.TREASURE;
    }

    @SubscribeEvent
    public static void onWoodBreak(BlockEvent.BreakEvent e) {
        Player p = e.getPlayer();
        if (!EnchantUtils.hasEnch(p, ModEnchantments.LUMBERJACK)) return;
        ItemStack stack = p.getMainHandItem();
        BlockState state = e.getState();
        Block block = state.getBlock();
        BlockPos pos = e.getPos();
        int maxLogsToBreak = 64;
        int maxLogsDetected = 64;
        if (isLog(block)) {
            List<BlockPos> logsToBreak = new ArrayList<>();
            int logsFound = findConnectedLogs(logsToBreak, e.getPlayer().getLevel(), pos, maxLogsToBreak);
            int logsToBreakCount = Math.min(logsFound, maxLogsDetected);
            int damageAmount = logsToBreakCount / 2;
            stack.hurtAndBreak(damageAmount, p, player -> {});
            for (BlockPos logPos : logsToBreak) {
                e.getPlayer().getLevel().destroyBlock(logPos, true);
            }
        }
    }

    private static boolean isLog(Block block) {
        ResourceLocation registryName = block.getRegistryName();
        if (registryName != null) {
            String namespace = registryName.getNamespace();
            return namespace.equals("minecraft") && registryName.getPath().contains("log");
        }
        return false;
    }

    private static int findConnectedLogs(List<BlockPos> logsToBreak, Level world, BlockPos start, int maxLogsDetected) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        int logsFound = 0;

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty() && logsFound < maxLogsDetected) {
            BlockPos currentPos = queue.poll();

            if (!isLog(world.getBlockState(currentPos).getBlock())) continue;

            logsToBreak.add(currentPos);
            logsFound++;

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.relative(direction);
                if (!visited.contains(neighborPos)) {
                    queue.add(neighborPos);
                    visited.add(neighborPos);
                }
            }
        }

        return logsFound;
    }

}
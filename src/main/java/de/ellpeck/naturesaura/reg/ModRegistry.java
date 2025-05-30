package de.ellpeck.naturesaura.reg;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.container.ItemAuraContainer;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.blocks.*;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAuraBloom;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityEnderCrate;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityImpl;
import de.ellpeck.naturesaura.blocks.tiles.ModBlockEntities;
import de.ellpeck.naturesaura.compat.Compat;
import de.ellpeck.naturesaura.compat.patchouli.PatchouliCompat;
import de.ellpeck.naturesaura.entities.*;
import de.ellpeck.naturesaura.gen.LevelGenAncientTree;
import de.ellpeck.naturesaura.gen.LevelGenAuraBloom;
import de.ellpeck.naturesaura.gen.LevelGenNetherWartMushroom;
import de.ellpeck.naturesaura.gen.ModFeatures;
import de.ellpeck.naturesaura.gui.ContainerEnderCrate;
import de.ellpeck.naturesaura.gui.GuiEnderCrate;
import de.ellpeck.naturesaura.gui.ModContainers;
import de.ellpeck.naturesaura.items.*;
import de.ellpeck.naturesaura.items.tools.*;
import de.ellpeck.naturesaura.potion.ModPotions;
import de.ellpeck.naturesaura.potion.PotionBreathless;
import de.ellpeck.naturesaura.recipes.ModRecipes;
import de.ellpeck.naturesaura.renderers.PlayerLayerTrinkets;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class ModRegistry {

    // we use a list so that the creative tab maintains addition order
    public static final List<IModItem> ALL_ITEMS = new ArrayList<>();

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(Registries.BLOCK, h -> {
            Block temp;
            ModRegistry.registerAll(h,
                new BlockAncientLog("ancient_log"),
                new BlockAncientLog("ancient_bark"),
                temp = new BlockImpl("ancient_planks", Block.Properties.of().sound(SoundType.WOOD).strength(2F)),
                new BlockStairsNA("ancient_stairs", "ancient_planks", temp.defaultBlockState(), Block.Properties.ofFullCopy(temp)),
                new Slab("ancient_slab", "ancient_planks", Block.Properties.ofFullCopy(temp)),
                new BlockAncientLeaves(),
                new BlockAncientSapling(),
                new BlockNatureAltar(),
                new BlockDecayedLeaves(),
                new BlockGoldenLeaves(),
                new BlockGoldPowder(),
                new BlockWoodStand(),
                temp = new BlockImpl("infused_stone", Block.Properties.of().sound(SoundType.STONE).strength(1.75F)),
                new BlockStairsNA("infused_stairs", "infused_stone", temp.defaultBlockState(), Block.Properties.ofFullCopy(temp)),
                new Slab("infused_slab", "infused_stone", Block.Properties.ofFullCopy(temp)),
                temp = new BlockImpl("infused_brick", Block.Properties.of().sound(SoundType.STONE).strength(1.5F)),
                new BlockStairsNA("infused_brick_stairs", "infused_brick", temp.defaultBlockState(), Block.Properties.ofFullCopy(temp)),
                new Slab("infused_brick_slab", "infused_brick", Block.Properties.ofFullCopy(temp)),
                new BlockFurnaceHeater(),
                new BlockPotionGenerator(),
                new BlockAuraDetector(),
                new BlockImpl("conversion_catalyst", Block.Properties.of().sound(SoundType.STONE).strength(2.5F)),
                new BlockImpl("crushing_catalyst", Block.Properties.of().sound(SoundType.STONE).strength(2.5F)),
                new BlockFlowerGenerator(),
                new BlockPlacer(),
                new BlockHopperUpgrade(),
                new BlockFieldCreator(),
                new BlockOakGenerator(),
                new BlockImpl("infused_iron_block", Block.Properties.of().sound(SoundType.METAL).strength(3F)),
                new BlockOfferingTable(),
                new BlockPickupStopper(),
                new BlockSpawnLamp(),
                new BlockAnimalGenerator(),
                new BlockEndFlower(),
                new BlockGratedChute(),
                new BlockAnimalSpawner(),
                new BlockAutoCrafter(),
                new BlockImpl("gold_brick", Block.Properties.ofFullCopy(Blocks.STONE_BRICKS)),
                new BlockImpl("gold_nether_brick", Block.Properties.ofFullCopy(Blocks.NETHER_BRICKS)),
                new BlockMossGenerator(),
                new BlockTimeChanger(),
                new BlockGeneratorLimitRemover(),
                new BlockEnderCrate(),
                new BlockPowderPlacer(),
                new BlockFireworkGenerator(),
                new BlockProjectileGenerator(),
                new BlockDimensionRail("overworld", Level.OVERWORLD, Level.NETHER, Level.END),
                new BlockDimensionRail("nether", Level.NETHER, Level.OVERWORLD),
                new BlockDimensionRail("end", Level.END, Level.OVERWORLD),
                new BlockBlastFurnaceBooster(),
                new BlockImpl("nether_wart_mushroom", Block.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK)),
                new BlockAnimalContainer(),
                new BlockSnowCreator(),
                new BlockItemDistributor(),
                temp = new BlockAuraBloom("aura_bloom", Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.FARMLAND),
                ModRegistry.createFlowerPot(temp),
                temp = new BlockAuraBloom("aura_cactus", Blocks.SAND, Blocks.RED_SAND),
                ModRegistry.createFlowerPot(temp),
                temp = new BlockAuraBloom("warped_aura_mushroom", Blocks.WARPED_NYLIUM),
                ModRegistry.createFlowerPot(temp),
                temp = new BlockAuraBloom("crimson_aura_mushroom", Blocks.CRIMSON_NYLIUM),
                ModRegistry.createFlowerPot(temp),
                temp = new BlockAuraBloom("aura_mushroom", Blocks.MYCELIUM),
                ModRegistry.createFlowerPot(temp),
                new BlockImpl("tainted_gold_block", Block.Properties.of().sound(SoundType.METAL).strength(3F)),
                new BlockNetherGrass(),
                new BlockLight(),
                new BlockChorusGenerator(),
                new BlockAuraTimer(),
                new BlockSlimeSplitGenerator(),
                new BlockSpring(),
                new BlockWeatherChanger(),
                new BlockRFConverter(),
                new BlockChunkLoader(),
                new BlockLowerLimiter(),
                new BlockImpl("sky_ingot_block", Block.Properties.of().sound(SoundType.METAL).strength(4F)),
                new BlockImpl("depth_ingot_block", Block.Properties.of().sound(SoundType.METAL).strength(6F))
            );
            Helper.populateObjectHolders(ModBlocks.class, event.getRegistry(), false);
        });

        event.register(Registries.ITEM, h -> {
            for (var block : ModRegistry.ALL_ITEMS) {
                if (block instanceof Block && !(block instanceof INoItemBlock)) {
                    var item = new BlockItem((Block) block, new Item.Properties());
                    h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, block.getBaseName()), item);
                }
            }

            Item temp;
            ModRegistry.registerAll(h,
                new ItemPickaxe("infused_iron_pickaxe", ModItemTier.INFUSED, 1, -2.8F),
                new ItemAxe("infused_iron_axe", ModItemTier.INFUSED, 6.0F, -3.1F),
                new ItemShovel("infused_iron_shovel", ModItemTier.INFUSED, 1.5F, -3.0F),
                new ItemHoe("infused_iron_hoe", ModItemTier.INFUSED, -1),
                new ItemSword("infused_iron_sword", ModItemTier.INFUSED, 3, -2.4F),
                new ItemArmor("infused_iron_helmet", ModArmorMaterial.INFUSED, ArmorItem.Type.HELMET),
                new ItemArmor("infused_iron_chest", ModArmorMaterial.INFUSED, ArmorItem.Type.CHESTPLATE),
                new ItemArmor("infused_iron_pants", ModArmorMaterial.INFUSED, ArmorItem.Type.LEGGINGS),
                new ItemArmor("infused_iron_shoes", ModArmorMaterial.INFUSED, ArmorItem.Type.BOOTS),
                new ItemEye("eye"),
                new ItemEye("eye_improved"),
                new ItemGoldFiber(),
                new ItemImpl("gold_leaf"),
                new ItemImpl("infused_iron"),
                new ItemImpl("ancient_stick"),
                new ItemColorChanger(),
                new ItemAuraCache("aura_cache"),
                new ItemAuraCache("aura_trove"),
                new ItemShockwaveCreator(),
                new ItemMultiblockMaker(),
                temp = new ItemImpl("bottle_two_the_rebottling"),
                new ItemAuraBottle(temp),
                new ItemImpl("farming_stencil"),
                new ItemImpl("sky_ingot"),
                new ItemGlowing("calling_spirit"),
                new ItemEffectPowder(),
                new ItemBirthSpirit(),
                new ItemMoverMinecart(),
                new ItemRangeVisualizer(),
                new ItemImpl("clock_hand"),
                new ItemImpl("token_joy"),
                new ItemImpl("token_fear"),
                new ItemImpl("token_anger"),
                new ItemImpl("token_sorrow"),
                new ItemImpl("token_euphoria"),
                new ItemImpl("token_terror"),
                new ItemImpl("token_rage"),
                new ItemImpl("token_grief"),
                new ItemEnderAccess(),
                new ItemCaveFinder(),
                new ItemCrimsonMeal(),
                new ItemDeathRing(),
                new ItemImpl("tainted_gold"),
                new ItemLootFinder(),
                new ItemLightStaff(),
                new ItemPickaxe("sky_pickaxe", ModItemTier.SKY, 1, -2.8F),
                new ItemAxe("sky_axe", ModItemTier.SKY, 5.0F, -3.0F),
                new ItemShovel("sky_shovel", ModItemTier.SKY, 1.5F, -3.0F),
                new ItemHoe("sky_hoe", ModItemTier.SKY, -1),
                new ItemSword("sky_sword", ModItemTier.SKY, 3, -2.4F),
                new ItemArmor("sky_helmet", ModArmorMaterial.SKY, ArmorItem.Type.HELMET),
                new ItemArmor("sky_chest", ModArmorMaterial.SKY, ArmorItem.Type.CHESTPLATE),
                new ItemArmor("sky_pants", ModArmorMaterial.SKY, ArmorItem.Type.LEGGINGS),
                new ItemArmor("sky_shoes", ModArmorMaterial.SKY, ArmorItem.Type.BOOTS),
                new ItemStructureFinder("fortress_finder", BuiltinStructures.FORTRESS, 0xba2800),
                new ItemStructureFinder("end_city_finder", BuiltinStructures.END_CITY, 0xca5cd6),
                new ItemStructureFinder("outpost_finder", BuiltinStructures.PILLAGER_OUTPOST, 0xab9f98),
                new ItemBreakPrevention(),
                new ItemPetReviver(),
                new ItemNetheriteFinder(),
                new ItemImpl("vacuum_bottle"),
                new ItemImpl("depth_ingot"),
                new ItemPickaxe("depth_pickaxe", ModItemTier.DEPTH, 1, -2.8F),
                new ItemAxe("depth_axe", ModItemTier.DEPTH, 5, -3),
                new ItemShovel("depth_shovel", ModItemTier.DEPTH, 1.5F, -3),
                new ItemHoe("depth_hoe", ModItemTier.DEPTH, -1),
                new ItemSword("depth_sword", ModItemTier.DEPTH, 3, -2.4F),
                new ItemArmor("depth_helmet", ModArmorMaterial.DEPTH, ArmorItem.Type.HELMET),
                new ItemArmor("depth_chest", ModArmorMaterial.DEPTH, ArmorItem.Type.CHESTPLATE),
                new ItemArmor("depth_pants", ModArmorMaterial.DEPTH, ArmorItem.Type.LEGGINGS),
                new ItemArmor("depth_shoes", ModArmorMaterial.DEPTH, ArmorItem.Type.BOOTS)
            );
            Helper.populateObjectHolders(ModItems.class, event.getRegistry(), false);
        });

        event.register(Registries.BLOCK_ENTITY_TYPE, h -> {
            // add tile entities that support multiple blocks
            ModRegistry.ALL_ITEMS.add(new ModTileType<>(BlockEntityAuraBloom::new, "aura_bloom", ModRegistry.ALL_ITEMS.stream().filter(i -> i instanceof BlockAuraBloom).toArray(IModItem[]::new)));

            for (var item : ModRegistry.ALL_ITEMS) {
                if (item instanceof ModTileType<?> type)
                    h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, type.getBaseName()), type.type);
            }
            Helper.populateObjectHolders(ModBlockEntities.class, event.getRegistry(), false);
        });

        event.register(Registries.MOB_EFFECT, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "breathless"), new PotionBreathless());
            Helper.populateObjectHolders(ModPotions.class, event.getRegistry(), true);
        });

        event.register(Registries.MENU, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "ender_crate"), IMenuTypeExtension.create((windowId, inv, data) -> {
                var tile = inv.player.level().getBlockEntity(data.readBlockPos());
                if (tile instanceof BlockEntityEnderCrate) {
                    var handler = tile.getLevel().getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, null);
                    return new ContainerEnderCrate(ModContainers.ENDER_CRATE, windowId, inv.player, handler);
                }
                return null;
            }));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "ender_access"), IMenuTypeExtension.create((windowId, inv, data) -> {
                IItemHandler handler = ILevelData.getOverworldData(inv.player.level()).getEnderStorage(data.readUtf());
                return new ContainerEnderCrate(ModContainers.ENDER_ACCESS, windowId, inv.player, handler);
            }));
            Helper.populateObjectHolders(ModContainers.class, event.getRegistry(), false);
        });

/*        event.register(Registries.ENCHANTMENT, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "aura_mending"), Enchantment.enchantment(
                Enchantment.definition(
                    HolderSet.direct(BuiltInRegistries.ITEM.holders().filter(i -> new ItemStack(i).getCapability(NaturesAuraAPI.AURA_RECHARGE_CAPABILITY) == null).toList()),
                    // same values as unbreaking, except for the max level
                    5, 1, Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(55, 8), 2, EquipmentSlotGroup.ANY)
            ).build(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "aura_mending")));
            Helper.populateObjectHolders(ModEnchantments.class, event.getRegistry(), true);
        });*/

        event.register(Registries.ENTITY_TYPE, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "mover_cart"), EntityType.Builder
                .of(EntityMoverMinecart::new, MobCategory.MISC)
                .sized(1, 1).setShouldReceiveVelocityUpdates(true)
                .setTrackingRange(64).setUpdateInterval(3).fireImmune().build(NaturesAura.MOD_ID + ":mover_minecart"));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "effect_inhibitor"), EntityType.Builder
                .of(EntityEffectInhibitor::new, MobCategory.MISC)
                .sized(1, 1).setShouldReceiveVelocityUpdates(true)
                .setTrackingRange(64).setUpdateInterval(20).fireImmune().build(NaturesAura.MOD_ID + ":effect_inhibitor"));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "light_projectile"), EntityType.Builder
                .<EntityLightProjectile>of(EntityLightProjectile::new, MobCategory.MISC)
                .sized(0.5F, 0.5F).setShouldReceiveVelocityUpdates(true)
                .setTrackingRange(64).setUpdateInterval(3).fireImmune().build(NaturesAura.MOD_ID + ":light_projectile"));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "structure_finder"), EntityType.Builder
                .of(EntityStructureFinder::new, MobCategory.MISC)
                .sized(0.5F, 0.5F).setShouldReceiveVelocityUpdates(true)
                .setTrackingRange(64).setUpdateInterval(2).fireImmune().build(NaturesAura.MOD_ID + ":structure_finder"));
            Helper.populateObjectHolders(ModEntities.class, event.getRegistry(), false);
        });

        event.register(Registries.FEATURE, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "aura_bloom"), new LevelGenAuraBloom(ModBlocks.AURA_BLOOM, 60, false));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "aura_cactus"), new LevelGenAuraBloom(ModBlocks.AURA_CACTUS, 60, false));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "warped_aura_mushroom"), new LevelGenAuraBloom(ModBlocks.WARPED_AURA_MUSHROOM, 10, true));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "crimson_aura_mushroom"), new LevelGenAuraBloom(ModBlocks.CRIMSON_AURA_MUSHROOM, 10, true));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "aura_mushroom"), new LevelGenAuraBloom(ModBlocks.AURA_MUSHROOM, 20, false));
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "ancient_tree"), new LevelGenAncientTree());
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "nether_wart_mushroom"), new LevelGenNetherWartMushroom());
            Helper.populateObjectHolders(ModFeatures.class, event.getRegistry(), false);
        });

        event.register(Registries.RECIPE_TYPE, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "altar"), ModRecipes.ALTAR_TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "animal_spawner"), ModRecipes.ANIMAL_SPAWNER_TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "offering"), ModRecipes.OFFERING_TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "tree_ritual"), ModRecipes.TREE_RITUAL_TYPE);
        });

        event.register(Registries.RECIPE_SERIALIZER, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "altar"), ModRecipes.ALTAR_SERIALIZER);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "animal_spawner"), ModRecipes.ANIMAL_SPAWNER_SERIALIZER);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "offering"), ModRecipes.OFFERING_SERIALIZER);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "tree_ritual"), ModRecipes.TREE_RITUAL_SERIALIZER);
        });

        event.register(Registries.CREATIVE_MODE_TAB, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "tab"), CreativeModeTab.builder()
                .title(Component.translatable("item_group." + NaturesAura.MOD_ID + ".tab"))
                .icon(() -> new ItemStack(ModItems.GOLD_LEAF))
                .displayItems((params, output) -> {
                    output.accept(PatchouliAPI.get().getBookStack(PatchouliCompat.BOOK));
                    ModRegistry.ALL_ITEMS.forEach(i -> {
                        if (i instanceof ICustomCreativeTab c) {
                            output.acceptAll(c.getCreativeTabItems());
                        } else if (i instanceof ItemLike l) {
                            if (l.asItem() != Items.AIR)
                                output.accept(l);
                        }
                    });
                })
                .build()
            );
        });

        event.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "aura_chunk"), NaturesAuraAPI.AURA_CHUNK_ATTACHMENT);
        });

        event.register(Registries.DATA_COMPONENT_TYPE, h -> {
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "multiblock_maker_data"), ItemMultiblockMaker.Data.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "aura_data"), ItemAuraContainer.Data.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "dropped_item_data"), BlockEntityImpl.DroppedItemData.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "ender_crate_data"), BlockEnderCrate.Data.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "aura_bottle_data"), ItemAuraBottle.Data.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "break_prevention_data"), ItemBreakPrevention.Data.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "color_changer_data"), ItemColorChanger.Data.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "effect_powder_data"), ItemEffectPowder.Data.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "shockwave_creator_data"), ItemShockwaveCreator.Data.TYPE);
            h.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, "disableable_tool_data"), Helper.DisableableToolData.TYPE);
        });
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(FluidHandler.BLOCK, ModBlockEntities.SPRING, (e, c) -> e.tank);
        event.registerBlockEntity(EnergyStorage.BLOCK, ModBlockEntities.RF_CONVERTER, (e, c) -> e.storage);
        event.registerBlockEntity(NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, ModBlockEntities.NATURE_ALTAR, (e, c) -> e.container);
        event.registerBlockEntity(NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, ModBlockEntities.ANCIENT_LEAVES, (e, c) -> e.container);
        event.registerBlockEntity(NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, ModBlockEntities.END_FLOWER, (e, c) -> e.container);
        event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.BLAST_FURNACE_BOOSTER, (e, c) -> e.getItemHandler());
        event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.OFFERING_TABLE, (e, c) -> e.items);
        event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.GRATED_CHUTE, (e, c) -> e.items);
        event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.NATURE_ALTAR, (e, c) -> e.items);
        event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.WOOD_STAND, (e, c) -> e.items);
        event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.ENDER_CRATE, (e, c) -> e.canOpen() ? e.wrappedEnderStorage : null);
        event.registerBlockEntity(ItemHandler.BLOCK, ModBlockEntities.AURA_TIMER, (e, c) -> e.itemHandler);

        event.registerItem(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY, (s, c) -> new ItemAuraContainer(s, null, 400000), ModItems.AURA_CACHE);
        event.registerItem(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY, (s, c) -> new ItemAuraContainer(s, null, 1200000), ModItems.AURA_TROVE);
        for (var item : ModRegistry.ALL_ITEMS) {
            if (item instanceof ItemArmor) {
                event.registerItem(NaturesAuraAPI.AURA_RECHARGE_CAPABILITY, Helper.makeRechargeProvider(false), (Item) item);
            } else if (item instanceof ItemAxe || item instanceof ItemPickaxe || item instanceof ItemShovel || item instanceof ItemHoe || item instanceof ItemSword) {
                event.registerItem(NaturesAuraAPI.AURA_RECHARGE_CAPABILITY, Helper.makeRechargeProvider(true), (Item) item);
            }
        }

        Compat.addCapabilities(event);
    }

    public static Block createFlowerPot(Block block) {
        var props = Block.Properties.of().strength(0F);
        Block potted = new BlockFlowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> block, props);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BuiltInRegistries.BLOCK.getKey(block), () -> potted);
        return potted;
    }

    @SafeVarargs
    private static <T> void registerAll(RegisterEvent.RegisterHelper<T> helper, T... items) {
        for (var item : items)
            helper.register(ResourceLocation.fromNamespaceAndPath(NaturesAura.MOD_ID, ((IModItem) item).getBaseName()), item);
    }

    @EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static final class Client {

        @SubscribeEvent
        public static void registerMenuScreens(RegisterMenuScreensEvent event) {
            event.register(ModContainers.ENDER_CRATE, GuiEnderCrate::new);
            event.register(ModContainers.ENDER_ACCESS, GuiEnderCrate::new);
        }

        @SubscribeEvent
        public static void registerRenderLayers(EntityRenderersEvent.AddLayers event) {
            for (var render : new PlayerRenderer[]{event.getSkin(PlayerSkin.Model.WIDE), event.getSkin(PlayerSkin.Model.SLIM)})
                render.addLayer(new PlayerLayerTrinkets(render));
        }

    }

}

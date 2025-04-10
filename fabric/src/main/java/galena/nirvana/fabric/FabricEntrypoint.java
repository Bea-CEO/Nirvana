package galena.nirvana.fabric;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import galena.nirvana.NirvanaCommon;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaBanners;
import galena.nirvana.index.NirvanaBlocks;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class FabricEntrypoint implements ModInitializer {

    public static final FabricNirvanaRegistrate REGISTRATE = new FabricNirvanaRegistrate(NirvanaConstants.MOD_ID);

    private static final ResourceKey<PlacedFeature> WILD_HEMP_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(NirvanaConstants.MOD_ID, "patch_wild_hemp"));

    private static final ProviderType<RegistrateTagsProvider.IntrinsicImpl<BannerPattern>> BANNER_PATTERN_TAGS = ProviderType.register("tags/banner_pattern", type -> (p, e) ->
            new RegistrateTagsProvider.IntrinsicImpl<>(p, type, "blocks", e.output(), Registries.BANNER_PATTERN, e.registriesLookup(), it -> BuiltInRegistries.BANNER_PATTERN.getResourceKey(it).orElseThrow())
    );

    @Override
    public void onInitialize() {
        NirvanaCommon.init();
        REGISTRATE.register();

        LootTableEvents.MODIFY.register((resources, manager, id, table, source) -> {
            if (!source.isBuiltin()) return;
            if (BuiltInLootTables.SNIFFER_DIGGING.equals(id)) {
                table.modifyPools(it -> {
                    it.add(LootItem.lootTableItem(NirvanaBlocks.BLISS_BLOOM));
                });
            } else if (BuiltInLootTables.IGLOO_CHEST.equals(id)) {
                table.withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(NirvanaItems.EMPTY_PIPE))
                        .when(LootItemRandomChanceCondition.randomChance(0.1F))
                );
            } else if (BuiltInLootTables.WOODLAND_MANSION.equals(id)) {
                table.withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(NirvanaItems.EMPTY_PIPE))
                        .when(LootItemRandomChanceCondition.randomChance(0.33F))
                );
            }
        });

        BiomeModifications.addFeature(BiomeSelectors.tag(NirvanaTags.GENERATES_WILD_HEMP), GenerationStep.Decoration.VEGETAL_DECORATION, WILD_HEMP_FEATURE);

        REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, provider ->
                provider.addTag(NirvanaTags.CREEPER_LIKE).add(EntityType.CREEPER)
        );

        REGISTRATE.addDataGenerator(BANNER_PATTERN_TAGS, provider -> {
            provider.addTag(NirvanaTags.PEACE_BANNER_PATTERN).add(NirvanaBanners.PEACE.get());
        });
    }

}

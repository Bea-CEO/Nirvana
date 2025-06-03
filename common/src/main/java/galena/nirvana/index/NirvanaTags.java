package galena.nirvana.index;

import static galena.nirvana.NirvanaConstants.MOD_ID;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

public class NirvanaTags {

    public static final TagKey<Block> SUMMER_CROPS = TagKey.create(Registries.BLOCK, new ResourceLocation("sereneseasons", "summer_crops"));

    public static final TagKey<Item> NAUSEATING = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "nauseating"));

    public static final TagKey<Item> ATTACHED_TO_HEAD = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "attached_to_head"));

    public static final TagKey<Biome> GENERATES_WILD_HEMP = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "has_feature/wild_hemp"));

    public static final TagKey<Item> SHEARS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "shears"));

    public static final TagKey<Item> SMOKING_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "smoking_item"));

    public static final TagKey<EntityType<?>> CREEPER_LIKE = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "creeper_like"));

    public static final TagKey<Block> SMOKING_CRATES = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "smoking_crates"));

    public static final TagKey<BannerPattern> PEACE_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "peace_banner_patterns"));

    public static final TagKey<Item> BURLAP = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "burlap"));

}

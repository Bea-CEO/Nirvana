package galena.nirvana.world.item;

import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaSounds;
import galena.nirvana.platform.Services;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PotionBongItem extends SmokingItem {

    public static final String PATTERN_TRANSLATION_KEY = NirvanaConstants.MOD_ID + ".pattern.potion";

    private static MobEffectInstance modify(MobEffectInstance instance) {
        return new MobEffectInstance(
                instance.getEffect(),
                instance.getDuration() / Services.CONFIG.common().getBongHits(),
                instance.getAmplifier(),
                instance.isAmbient(),
                instance.isVisible(),
                instance.showIcon(),
                null,
                instance.getFactorData()
        );
    }

    public PotionBongItem(Properties properties) {
        super(properties);
    }

    @Override
    Stream<MobEffectInstance> getEffects(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity) {
        return PotionUtils.getMobEffects(stack).stream().map(PotionBongItem::modify);
    }

    @Override
    double getRadius(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity) {
        return Services.CONFIG.common().bongRadius();
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!Services.CONFIG.common().generateBongTranslations()) {
            return super.getName(stack);
        }

        var language = Language.getInstance();
        if (language.has(PATTERN_TRANSLATION_KEY)) try {
            var potion = PotionUtils.getPotion(stack);
            var pattern = Pattern.compile(language.getOrDefault(PATTERN_TRANSLATION_KEY));
            var potionTranslation = language.getOrDefault(potion.getName(Items.POTION.getDescriptionId() + ".effect."));
            var matcher = pattern.matcher(potionTranslation);
            if (matcher.find()) {
                var translation = matcher.group(1);
                return Component.translatable(getDescriptionId(), translation);
            }
        } catch (PatternSyntaxException | IllegalStateException | IndexOutOfBoundsException ex) {
            NirvanaConstants.LOGGER.debug("Unable to translation potion bong automatically", ex);
        }

        return super.getName(stack);
    }

    public String getDescriptionId(ItemStack stack) {
        return PotionUtils.getPotion(stack).getName(getDescriptionId() + ".effect.");
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }

    @Override
    protected @Nullable SoundEvent getUseSound() {
        return NirvanaSounds.BONG.get();
    }

}

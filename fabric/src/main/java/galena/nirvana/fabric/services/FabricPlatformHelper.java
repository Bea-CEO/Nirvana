package galena.nirvana.fabric.services;

import com.tterrag.registrate.builders.EntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import galena.nirvana.fabric.FabricEntrypoint;
import galena.nirvana.platform.registrate.EntityPropertiesBuilder;
import galena.nirvana.platform.registrate.NirvanaRegistrate;
import galena.nirvana.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public NirvanaRegistrate<?> getRegistrate() {
        return FabricEntrypoint.REGISTRATE;
    }

    private static <E extends Entity> NonNullConsumer<FabricEntityTypeBuilder<E>> mapFactory(NonNullConsumer<EntityPropertiesBuilder> factory) {
        return builder -> {
            factory.accept(new EntityPropertiesBuilder() {
                @Override
                public EntityPropertiesBuilder sized(float width, float height) {
                    builder.dimensions(EntityDimensions.fixed(width, height));
                    return this;
                }

                @Override
                public EntityPropertiesBuilder clientTrackingRange(int range) {
                    builder.trackRangeChunks(range);
                    return this;
                }

                @Override
                public EntityPropertiesBuilder updateInterval(int interval) {
                    builder.trackedUpdateRate(interval);
                    return this;
                }

                @Override
                public EntityPropertiesBuilder fireImmune() {
                    builder.fireImmune();
                    return this;
                }
            });
        };
    }

    @Override
    public <E extends Entity, P> NonNullFunction<EntityBuilder<E, P>, EntityBuilder<E, P>> entityProperties(NonNullConsumer<EntityPropertiesBuilder> factory) {
        return entry -> entry.properties(mapFactory(factory));
    }

}

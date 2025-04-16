package galena.nirvana.forge.client;

import galena.nirvana.NirvanaClient;
import galena.nirvana.client.JointModels;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Function;

public class ForgeClientEntrypoint {

    public static void init() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(ForgeClientEntrypoint::registerModels);
        modBus.addListener(ForgeClientEntrypoint::registerParticles);
        modBus.addListener(ForgeClientEntrypoint::registerLayers);
    }

    private static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(JointModels.HAND_MODEL);
        event.register(JointModels.FLAT_MODEL);
    }

    private static void registerParticles(RegisterParticleProvidersEvent event) {
        NirvanaClient.registerParticles(new NirvanaParticles.ParticleRegister() {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> options, Function<SpriteSet, ParticleProvider<T>> factory) {
                event.registerSpriteSet(options, factory::apply);
            }
        });
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
    }

}

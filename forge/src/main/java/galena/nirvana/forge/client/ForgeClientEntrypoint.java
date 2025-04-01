package galena.nirvana.forge.client;

import galena.nirvana.client.JointModels;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import galena.nirvana.world.particle.SmokeRingParticle;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        event.registerSpriteSet(NirvanaParticles.SMOKE_RING.get(), SmokeRingParticle.Provider::new);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
    }

}

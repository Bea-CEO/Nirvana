package galena.nirvana.fabric.client;

import galena.nirvana.client.JointModels;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import galena.nirvana.world.particle.SmokeRingParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BuiltinItemRendererRegistry.INSTANCE.register(NirvanaItems.JOINT.get(), new JointRenderer());
        ModelLoadingPlugin.register(context -> context.addModels(JointModels.FLAT_MODEL, JointModels.HAND_MODEL));

        ParticleFactoryRegistry.getInstance().register(NirvanaParticles.SMOKE_RING.get(), SmokeRingParticle.Provider::new);

        EntityModelLayerRegistry.registerModelLayer(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
    }

}

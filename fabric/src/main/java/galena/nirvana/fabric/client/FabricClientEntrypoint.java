package galena.nirvana.fabric.client;

import galena.nirvana.NirvanaClient;
import galena.nirvana.client.JointModels;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Function;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BuiltinItemRendererRegistry.INSTANCE.register(NirvanaItems.JOINT.get(), new JointRenderer());
        ModelLoadingPlugin.register(context -> context.addModels(JointModels.FLAT_MODEL, JointModels.HAND_MODEL));

        NirvanaClient.registerParticles(new NirvanaParticles.ParticleRegister() {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> options, Function<SpriteSet, ParticleProvider<T>> factory) {
                ParticleFactoryRegistry.getInstance().register(options, factory::apply);
            }
        });

        EntityModelLayerRegistry.registerModelLayer(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
    }

}

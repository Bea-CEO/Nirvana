package galena.nirvana.world.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CustomTntRenderer extends TntRenderer {

    private final BlockRenderDispatcher blockRenderer;
    private final BlockState block;

    private CustomTntRenderer(EntityRendererProvider.Context context, Block block) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
        this.block = block.defaultBlockState();
    }

    public static <T extends PrimedTnt> NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>> of(NonNullSupplier<? extends Block> block) {
        return context -> new CustomTntRenderer(context, block.get());
    }

    @Override
    public void render(PrimedTnt primedTnt, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5F, 0.0F);
        int fuse = primedTnt.getFuse();
        if (fuse - g + 1.0F < 10.0F) {
            float h = 1.0F - ((float) fuse - g + 1.0F) / 10.0F;
            h = Mth.clamp(h, 0.0F, 1.0F);
            h *= h;
            h *= h;
            float k = 1.0F + h * 0.3F;
            poseStack.scale(k, k, k);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5F, -0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(blockRenderer, block, poseStack, multiBufferSource, i, fuse / 5 % 2 == 0);
        poseStack.popPose();
    }

}

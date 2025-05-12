package galena.nirvana.client;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.platform.Services;
import galena.nirvana.platform.services.IClientPlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.List;

import static net.minecraft.world.item.ItemDisplayContext.HEAD;
import static net.minecraft.world.item.ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
import static net.minecraft.world.item.ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;

public class CustomItemModel {

    private static final IClientPlatformHelper SERVICE = Services.load(IClientPlatformHelper.class);

    public static final CustomItemModel JOINT = SERVICE.registerCustomModel(NirvanaItems.JOINT, List.of(THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, HEAD));
    public static final CustomItemModel DEERSTALKER = SERVICE.registerCustomModel(NirvanaItems.DEERSTALKER, List.of(HEAD));

    private final ModelResourceLocation equippedModel;
    private final ModelResourceLocation flatModel;

    private final Collection<ItemDisplayContext> contexts;

    public CustomItemModel(String name, Collection<ItemDisplayContext> contexts) {
        this.contexts = contexts;
        this.equippedModel = new ModelResourceLocation(NirvanaConstants.MOD_ID, name + "_equipped", "inventory");
        this.flatModel = new ModelResourceLocation(NirvanaConstants.MOD_ID, name + "_flat", "inventory");
    }

    public Collection<? extends ResourceLocation> models() {
        return List.of(flatModel, equippedModel);
    }

    @FunctionalInterface
    public interface Renderer {
        void render(ItemRenderer renderer, BakedModel model, VertexConsumer vertexConsumer);
    }

    private ModelResourceLocation getModel(ItemDisplayContext mode) {
        if (contexts.contains(mode)) return equippedModel;
        return flatModel;
    }

    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack pose, MultiBufferSource vertexConsumers, Renderer r) {
        pose.pushPose();

        Lighting.setupForFlatItems();

        var texture = getModel(mode);
        var renderer = Minecraft.getInstance().getItemRenderer();
        var model = renderer.getItemModelShaper().getModelManager().getModel(texture);

        pose.translate(0.5, 0.5, 0.5);
        model.getTransforms().getTransform(mode).apply(false, pose);
        pose.translate(-0.5, -0.5, -0.5);

        var renderType = ItemBlockRenderTypes.getRenderType(stack, false);
        var vertex = ItemRenderer.getFoilBufferDirect(vertexConsumers, renderType, true, stack.hasFoil());
        r.render(renderer, model, vertex);

        pose.popPose();
    }

    public static void register() {
        // Load this class
    }

}

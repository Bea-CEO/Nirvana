package galena.nirvana.fabric.client;

import com.mojang.blaze3d.vertex.PoseStack;
import galena.nirvana.client.CustomItemModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CustomModelRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private final CustomItemModel model;

    public CustomModelRenderer(CustomItemModel model) {
        this.model = model;
    }

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack pose, MultiBufferSource vertexConsumers, int light, int overlay) {
        model.render(stack, mode, pose, vertexConsumers, ((renderer, model, vertexConsumer) ->
                renderer.renderModelLists(model, stack, light, overlay, pose, vertexConsumer))
        );
    }

}

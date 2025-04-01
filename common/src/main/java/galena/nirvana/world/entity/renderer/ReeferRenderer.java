package galena.nirvana.world.entity.renderer;

import galena.nirvana.NirvanaConstants;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

public class ReeferRenderer extends CreeperRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(NirvanaConstants.MOD_ID, "textures/entity/reefer.png");
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(NirvanaConstants.MOD_ID, "reefer"), "main");

    public ReeferRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CreeperModel<>(context.bakeLayer(LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(Creeper creeper) {
        return TEXTURE;
    }

    public static LayerDefinition createLayers() {
        var deformation = CubeDeformation.NONE;

        var mesh = new MeshDefinition();
        PartDefinition partDefinition = mesh.getRoot();
        partDefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation),
                PartPose.offset(0.0F, 6.0F, 0.0F)
        );
        partDefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(0.0F, 6.0F, 0.0F)
        );

        var legs = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation);
        partDefinition.addOrReplaceChild("right_hind_leg", legs, PartPose.offset(-2.0F, 18.0F, 4.0F));
        partDefinition.addOrReplaceChild("left_hind_leg", legs, PartPose.offset(2.0F, 18.0F, 4.0F));
        partDefinition.addOrReplaceChild("right_front_leg", legs, PartPose.offset(-2.0F, 18.0F, -4.0F));
        partDefinition.addOrReplaceChild("left_front_leg", legs, PartPose.offset(2.0F, 18.0F, -4.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }
}

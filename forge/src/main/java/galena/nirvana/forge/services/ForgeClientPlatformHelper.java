package galena.nirvana.forge.services;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.client.CustomItemModel;
import galena.nirvana.platform.services.IClientPlatformHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ForgeClientPlatformHelper implements IClientPlatformHelper {

    @Override
    public CustomItemModel registerCustomModel(NonNullSupplier<? extends Item> item, CustomItemModel model) {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((ModelEvent.RegisterAdditional event) ->
            model.models().forEach(event::register)
        );
        return model;
    }

}

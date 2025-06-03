package galena.nirvana.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import galena.nirvana.index.NirvanaItems;
import java.util.UUID;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DeerStalkerItem extends Item implements ArmorLike {

    private static final UUID ATTRIBUTE_UUID = UUID.fromString("fc19f9da-04c4-41e4-89df-350590564ee4");

    private final Multimap<Attribute, AttributeModifier> modifiers;

    public DeerStalkerItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);

        var builder = ImmutableMultimap.<Attribute, AttributeModifier>builder();
        builder.put(Attributes.ARMOR, new AttributeModifier(ATTRIBUTE_UUID, "Armor modifier", ArmorMaterials.LEATHER.getDefenseForType(ArmorItem.Type.HELMET), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ATTRIBUTE_UUID, "Armor toughness", ArmorMaterials.LEATHER.getToughness(), AttributeModifier.Operation.ADDITION));
        this.modifiers = builder.build();
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack with) {
        return NirvanaItems.HEMP_CLOTH.isIn(with);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return swapWithEquipmentSlot(this, level, player, hand);
    }

    @Override
    public int getEnchantmentValue() {
        return ArmorMaterials.LEATHER.getEnchantmentValue();
    }

    @Override
    public SoundEvent getEquipSound() {
        return ArmorMaterials.LEATHER.getEquipSound();
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot != getEquipmentSlot()) return super.getDefaultAttributeModifiers(slot);
        return modifiers;
    }

}

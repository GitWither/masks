package com.daniel.masks.item;

import com.daniel.masks.Masks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class MaskItem extends ArmorItem {
    public MaskItem(ArmorMaterial material) {
        super(material, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT).maxCount(1).group(Masks.MASK_GROUP));
    }
}

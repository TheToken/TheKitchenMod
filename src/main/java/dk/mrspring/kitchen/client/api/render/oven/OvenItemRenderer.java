package dk.mrspring.kitchen.client.api.render.oven;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public abstract class OvenItemRenderer
{
    public abstract void render(IClientOven oven);
}

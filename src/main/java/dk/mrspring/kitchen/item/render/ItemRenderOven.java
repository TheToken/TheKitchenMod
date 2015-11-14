package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelOven;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
public class ItemRenderOven implements IItemRenderer
{
    ModelOven model = new ModelOven();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/oven.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
            case INVENTORY:
            case ENTITY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case BLOCK_3D:
                return type != ItemRenderType.ENTITY;
            case INVENTORY_BLOCK:
                return type == ItemRenderType.INVENTORY;
            case ENTITY_BOBBING:
            case ENTITY_ROTATION:
                return type == ItemRenderType.ENTITY;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType renderType, ItemStack item, Object... data)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        switch (renderType)
        {
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(25, 0F, 0F, 1F);
                GL11.glTranslatef(.5F, .8F, -.15F);
                GL11.glRotatef(40, 0, 1, 0);

                float scale = 0.8F;

                GL11.glScalef(0.6F, scale, scale);

                GL11.glTranslatef(.5F, -.2F, .5F);
                break;
            case EQUIPPED:
                GL11.glRotatef(180, 0, 1, 0);
                GL11.glRotatef(25F, 0F, 0F, -1F);
                GL11.glRotatef(5, 0, -1, 0);
                GL11.glTranslatef(-0.875F, 1.2F, -0.05F);

                scale = 1.2F;

                GL11.glScalef(scale, scale, scale);
                break;
            case INVENTORY:
                GL11.glRotatef(180, 0, 1, 0);
                scale = 1F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.0F, 1F, 0F);
                break;
            case ENTITY:
                scale = 0.5F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.3F, 0F);
                GL11.glTranslatef(0F, 0F, -0.1F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, 0, 0, 0F);
        GL11.glPopMatrix();
    }
}

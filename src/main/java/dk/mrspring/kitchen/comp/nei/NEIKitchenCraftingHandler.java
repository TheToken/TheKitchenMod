package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.recipe.INEIRecipeHelper;
import dk.mrspring.kitchen.recipe.IRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.List;

/**
 * Created by Konrad on 12-04-2015.
 */
public abstract class NEIKitchenCraftingHandler extends FurnaceRecipeHandler
{
    protected abstract ItemStack getBlockDisplayStack();

    protected abstract String getName();

    protected abstract String getID();

    protected abstract List<IRecipe> getRecipes();

    protected void loadAllRecipes()
    {
        java.util.List<IRecipe> recipes = getRecipes();
        for (IRecipe iRecipe : recipes)
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                ItemStack input = recipe.getExpectedInput();
                ItemStack output = recipe.getExpectedOutput(input);
                arecipes.add(new RecipePair(input, output));
            }
    }

    protected void loadRecipeFor(ItemStack output)
    {
        java.util.List<IRecipe> recipes = getRecipes();
        for (IRecipe iRecipe : recipes)
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                if (!recipe.doesExpectedOutputMatch(output))
                    continue;
                ItemStack input = recipe.getExpectedInput(output);
                arecipes.add(new RecipePair(input, output));
            }
    }

    protected void loadRecipesFrom(ItemStack input)
    {
        java.util.List<IRecipe> recipes = getRecipes();
        for (IRecipe iRecipe : recipes)
        {
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                if (!recipe.doesExpectedInputMatch(input))
                    continue;
                ItemStack output = recipe.getExpectedOutput(input);
                arecipes.add(new RecipePair(input, output));
            }
        }
    }

    protected boolean drawMouse()
    {
        return true;
    }

    protected boolean translateName()
    {
        return true;
    }

    @Override
    public String getGuiTexture()
    {
        return ModInfo.toTexture("textures/gui/nei/base.png");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(getID()))
            this.loadAllRecipes();
        else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if (inputId.equals(getID()))
            this.loadCraftingRecipes(inputId, ingredients);
        else super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        this.loadRecipeFor(result);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        this.loadRecipesFrom(ingredient);
    }

    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
        if (drawMouse())
        {
            int drawX = 176;
            int mouseButton = Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode();
            if (mouseButton == -100)
                drawX += 16;
            else if (mouseButton == -99)
                drawX += 32;
            else if (mouseButton == -98)
                drawX += 48;
            drawTexturedModalRect(51, 42 - 18, drawX, 31, 16, 16);
        }
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), getID()));
    }

    @Override
    public String getRecipeName()
    {
        if (translateName())
            return StatCollector.translateToLocal(getName());
        return getName();
    }

    private void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x), (double) (y + height), 1, (double) ((float) (u) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), 1, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y), 1, (double) ((float) (u + width) * f), (double) ((float) (v) * f1));
        tessellator.addVertexWithUV((double) (x), (double) (y), 1, (double) ((float) (u) * f), (double) ((float) (v) * f1));
        tessellator.draw();
    }

    public class RecipePair extends SmeltingPair
    {
        public RecipePair(ItemStack input, ItemStack output)
        {
            super(input, output);
        }

        @Override
        public PositionedStack getOtherStack()
        {
            return new PositionedStack(getBlockDisplayStack(), 51, 42, false);
        }
    }
}
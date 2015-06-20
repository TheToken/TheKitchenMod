package dk.mrspring.kitchen.comp.furniture;

import com.mrcrayfish.furniture.api.IRecipeRegistry;
import com.mrcrayfish.furniture.api.RecipeVariables;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.recipe.INEIRecipeHelper;
import dk.mrspring.kitchen.recipe.IRecipe;
import dk.mrspring.kitchen.recipe.KnifeRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 08-12-2014 for TheKitchenMod.
 */
public class CrayfishFurnitureRegister
{
    public static void registerRecipes(IRecipeRegistry registryComm)
    {
        ModLogger.print(ModLogger.INFO, "Loading Crayfish Furniture Mod compatibility..."); // TODO: Use new KnifeRecipes registry

        for (IRecipe recipe : KnifeRecipes.instance().getRecipes())
        {
            if (recipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipeHelper = (INEIRecipeHelper) recipe;
                RecipeVariables variables = new RecipeVariables();
                ItemStack input = recipeHelper.getExpectedInput();
                variables.addValue("input", input);
                variables.addValue("output", recipeHelper.getExpectedOutput(input));
                registryComm.registerRecipe("choppingboard", variables);
            }
        }

        /*RecipeVariables variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(Items.porkchop));
        variables.addValue("output", new ItemStack(KitchenItems.raw_bacon));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(KitchenItems.tomato));
        variables.addValue("output", new ItemStack(KitchenItems.tomato_slice));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(KitchenItems.lettuce));
        variables.addValue("output", new ItemStack(KitchenItems.lettuce_leaf));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(Items.potato));
        variables.addValue("output", new ItemStack(KitchenItems.potato_slice));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(Items.carrot));
        variables.addValue("output", new ItemStack(KitchenItems.carrot_slice));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(Items.beef));
        variables.addValue("output", new ItemStack(KitchenItems.raw_roast_beef));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(Items.chicken));
        variables.addValue("output", new ItemStack(KitchenItems.raw_chicken_fillet));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(KitchenItems.cheese));
        variables.addValue("output", new ItemStack(KitchenItems.cheese_slice));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(Items.apple));
        variables.addValue("output", new ItemStack(KitchenItems.cut_apple));
        registryComm.registerRecipe("choppingboard", variables);

        variables = new RecipeVariables();
        variables.addValue("input", new ItemStack(KitchenItems.strawberry));
        variables.addValue("output", new ItemStack(KitchenItems.cut_strawberry));
        registryComm.registerRecipe("choppingboard", variables);*/


        RecipeVariables variables = new RecipeVariables();
        variables.addValue("name", "Strawberry Smoothie");
        variables.addValue("heal", 6);
        variables.addValue("ingredients", new ItemStack[]{new ItemStack(KitchenItems.cut_strawberry, 8), new ItemStack(Items.sugar, 4), new ItemStack(Blocks.ice, 1)});
        variables.addValue("colour", new int[]{255, 0, 0});
        registryComm.registerRecipe("blender", variables);
    }
}

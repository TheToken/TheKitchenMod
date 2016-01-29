package dk.mrspring.kitchen.api_impl.common.pan;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.item.ItemJamJar;
import dk.mrspring.kitchen.recipe.FryingPanJamRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

/**
 * Created by Konrad on 19-06-2015.
 */
public class JamIngredient implements IIngredient
{
    public static final String JAM_RECIPE_INPUT = "JamRecipeInput";
    public static final String JAM_RECIPE_OUTPUT = "JamRecipeOutput";

    @Override
    public String getName()
    {
        return "jam_recipe";
    }

    @Override
    public String getDisplayName(IFryingPan pan)
    {
        NBTTagCompound compound = pan.getSpecialInfo();
        String jam = compound.getString(JAM_RECIPE_OUTPUT);
        return StatCollector.translateToLocal("jam." + jam.toLowerCase() + ".name");
    }

    @Override
    public boolean isForItem(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        return FryingPanJamRecipes.instance().hasOutput(clicked);
    }

    @Override
    public boolean canAdd(IFryingPan pan, ItemStack adding, EntityPlayer player)
    {
        return true;
    }

    @Override
    public void onAdded(IFryingPan pan, ItemStack added, EntityPlayer player)
    {
        pan.playSound(ModInfo.toTexture("sizzle"), 1F, 1F, false);
        String jamOutput = FryingPanJamRecipes.instance().getJamOutputFor(added);
        NBTTagCompound compound = pan.getSpecialInfo();
        NBTTagCompound inputCompound = new NBTTagCompound();
        added.writeToNBT(inputCompound);
        compound.setTag(JAM_RECIPE_INPUT, inputCompound);
        compound.setString(JAM_RECIPE_OUTPUT, jamOutput);
        added.stackSize--;
    }

    @Override
    public boolean onRightClicked(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        if (pan.isFinished() && clicked != null && clicked.getItem() == KitchenItems.jam_jar)
        {
            NBTTagCompound compound = pan.getSpecialInfo();
            NBTTagCompound inputCompound = compound.getCompoundTag(JAM_RECIPE_INPUT);
            ItemStack input = ItemStack.loadItemStackFromNBT(inputCompound);
            String jamOutput = FryingPanJamRecipes.instance().getJamOutputFor(input);
            if (jamOutput == null)
                jamOutput = compound.getString(JAM_RECIPE_OUTPUT);
            clicked.stackSize--;
            ItemStack output = ItemJamJar.getJamJarStack(jamOutput, 6);
            pan.spawnItemInWorld(output);
            pan.replaceIngredient(null);
            return true;
        }
        return false;
    }

    @Override
    public int getCookTime(IFryingPan pan)
    {
        return 150;
    }

    @Override
    public boolean canBeRemoved(IFryingPan pan, EntityPlayer player)
    {
        return false;
    }

    @Override
    public ItemStack onRemoved(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        return null;
    }

    @Override
    public boolean readyToCook(IFryingPan pan)
    {
        return true;
    }
}
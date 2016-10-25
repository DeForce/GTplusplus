package gtPlusPlus.core.util.recipe.shapeless;

import gtPlusPlus.core.util.Utils;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;

public class ShapelessUtils {


	public static boolean addShapelessRecipe(ItemStack output, Object ... params)
	{
		ArrayList<ItemStack> arraylist = new ArrayList<ItemStack>();
		Object[] aobject = params;
		int i = params.length;

		for (int j = 0; j < i; ++j)
		{
			Object object1 = aobject[j];

			if (object1 instanceof ItemStack)
			{
				arraylist.add(((ItemStack)object1).copy());
			}
			else if (object1 instanceof Item)
			{
				arraylist.add(new ItemStack((Item)object1));
			}
			else
			{
				if ((object1 == null))
				{
					Utils.LOG_INFO(("Invalid shapeless input, ignoring!"));					
				}
				else if (!(object1 instanceof Block) && (object1 != null))
				{
					Utils.LOG_INFO(("Invalid shapeless recipe!"));
					return false;
				}
				else {
					arraylist.add(new ItemStack((Block)object1));
				}
			}
		}
		CraftingManager.getInstance().getRecipeList().add(new ShapelessRecipes(output, arraylist));
		//CraftingManager.getInstance().addShapelessRecipe(output, arraylist);
		return true;
	}


}
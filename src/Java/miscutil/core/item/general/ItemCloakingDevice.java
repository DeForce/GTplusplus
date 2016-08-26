package miscutil.core.item.general;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IElectricItemManager;

import java.util.List;

import miscutil.core.creative.AddToCreativeTab;
import miscutil.core.lib.CORE;
import miscutil.core.util.item.UtilsItems;
import miscutil.core.util.math.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemCloakingDevice extends Item implements IElectricItem, IElectricItemManager, IBauble{

	private final String unlocalizedName = "personalCloakingDevice";
	private final ItemStack thisStack;
	private final static int maxValueEU = 10000*20*500;
	protected double chargeEU = 0;

	public ItemCloakingDevice(double charge){
		this.chargeEU = charge;
		this.setCreativeTab(AddToCreativeTab.tabMachines);
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxStackSize(1);
		this.setTextureName(CORE.MODID + ":" + "personalCloakingDevice");
		this.thisStack = UtilsItems.getSimpleStack(this);
		this.charge(thisStack, charge, 3, true, false);
		if (charge == 10000*20*500){
			this.setDamage(thisStack, 13);	
		}
		GameRegistry.registerItem(this, unlocalizedName+"-"+charge);
	}

	@Override
	public void onUpdate(ItemStack itemStack, World worldObj, Entity player, int p_77663_4_, boolean p_77663_5_) {
		if (worldObj.isRemote) {
			return;
		}

		if (player instanceof EntityPlayer){
			for (ItemStack is : ((EntityPlayer) player).inventory.mainInventory) {
				if (is == itemStack) {
					continue;
				}
				if (is != null) {
					if (is.getItem() instanceof IElectricItem) {
						IElectricItem electricItem = (IElectricItem) is.getItem();
						chargeEU = ElectricItem.manager.getCharge(is);
					}

				}
			}
		}


		super.onUpdate(itemStack, worldObj, player, p_77663_4_, p_77663_5_);
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public Item getChargedItem(ItemStack itemStack) {
		ItemStack x = itemStack.copy();
		x.setItemDamage(maxValueEU);
		return x.getItem();
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack) {
		ItemStack x = itemStack.copy();
		x.setItemDamage(0);
		return x.getItem();
	}

	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return maxValueEU;
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return 5;
	}

	@Override
	public double getTransferLimit(ItemStack itemStack) {
		return 8196;
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_) {

		return (EnumChatFormatting.BLUE+"Personal Cloaking Device"+EnumChatFormatting.GRAY);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		//return 1.0D - getEnergyStored(stack) / this.capacity;
		return  1.0D - (double)getCharge(stack) / (double)getMaxCharge(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}
	
	public int secondsLeft(ItemStack stack){
		double r = 0;
		r = getCharge(stack)/(10000*20);
		return (int) MathUtils.decimalRounding(r);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer aPlayer, List list, boolean bool) {				
		list.add("");				
		list.add(EnumChatFormatting.GREEN+"Worn as a Belt within Baubles."+EnumChatFormatting.GRAY);	
		list.add(EnumChatFormatting.GREEN+"Drains 10,000Eu/t to provide invisibility."+EnumChatFormatting.GRAY);			
		list.add("");			
		list.add(EnumChatFormatting.GOLD+"IC2/EU Information"+EnumChatFormatting.GRAY);	
		list.add(EnumChatFormatting.GRAY+"Tier: ["+EnumChatFormatting.YELLOW+getTier(thisStack)+EnumChatFormatting.GRAY+"] Input Limit: ["+EnumChatFormatting.YELLOW+getTransferLimit(thisStack)+EnumChatFormatting.GRAY +"Eu/t]");
		list.add(EnumChatFormatting.GRAY+"Current Power: ["+EnumChatFormatting.YELLOW+(long) getCharge(stack)+EnumChatFormatting.GRAY+"Eu] ["+EnumChatFormatting.YELLOW+MathUtils.findPercentage(getCharge(stack), getMaxCharge(stack))+EnumChatFormatting.GRAY +"%]");
		list.add(EnumChatFormatting.GRAY+"Time Remaining: ["+EnumChatFormatting.YELLOW+secondsLeft(stack)+ EnumChatFormatting.GRAY +" seconds]");
		super.addInformation(stack, aPlayer, list, bool);
	}

	/*@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		ItemStack newItem = itemStack.copy();
		newItem.stackSize = 1;
		extractEnergy(newItem, 150000, false);
		return newItem;
	}*/

	/*@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}*/

	/*@Override
	public int getBurnTime(ItemStack fuel) {
		if ((fuel == null) || (fuel.getItem() != this)) {
			return 0;
		}
		return extractEnergy(fuel, 150000, true) / 50 / 100;
	}*/

	@Override
	public double charge(ItemStack stack, double amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {		 

		if (!simulate)
		{
			ElectricItem.manager.charge(stack, amount, tier, true, simulate);

		}
		return ElectricItem.manager.charge(stack, amount, tier, true, simulate);
	}

	@Override
	public double discharge(ItemStack stack, double amount, int tier,
			boolean ignoreTransferLimit, boolean externally, boolean simulate) {		
		if (!simulate)
		{
			ElectricItem.manager.discharge(stack, amount, tier, ignoreTransferLimit, externally, simulate);
		}

		return ElectricItem.manager.discharge(stack, amount, tier, ignoreTransferLimit, externally, simulate);
	}

	@Override
	public double getCharge(ItemStack stack) {
		return ElectricItem.manager.getCharge(stack);
	}

	@Override
	public boolean canUse(ItemStack stack, double amount) {
		return ElectricItem.manager.canUse(stack, amount);
	}

	@Override
	public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
		return ElectricItem.manager.use(stack, amount, entity);
	}

	@Override
	public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
		ElectricItem.manager.chargeFromArmor(stack, entity);
	}

	@Override
	public String getToolTip(ItemStack stack) {
		return ElectricItem.manager.getToolTip(stack);
	}

	@Override
	public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.BELT;
	}

	@Override //TODO
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {

	}

	@Override //TODO
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {

	}

	@Override //TODO
	public void onWornTick(ItemStack arg0, EntityLivingBase arg1) {
		//Utils.LOG_INFO("Trying to Tick Belt. 1");
		if (!arg1.worldObj.isRemote){
			if (getCharge(arg0) >= 10000){
				arg1.addPotionEffect(new PotionEffect(Potion.invisibility.id, 10, 2));
				discharge(arg0, 10000, 5, true, true, false);
			}
			else {
				if (arg1.isPotionActive((Potion.invisibility))){
					arg1.removePotionEffect(Potion.invisibility.id);
				}
			}
		}
	}

}

package gtPlusPlus.core.material;

import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.item.UtilsItems;
import gtPlusPlus.core.util.math.MathUtils;
import net.minecraft.item.ItemStack;

public class Material {

	final String unlocalizedName;
	final String localizedName;

	private MaterialStack[] materialInput = new MaterialStack[4];

	final short[] RGBA;

	final boolean usesBlastFurnace;

	final int meltingPointK;
	final int boilingPointK;
	final int meltingPointC;
	final int boilingPointC;
	final long vProtons;
	final long vNeutrons;
	final long vMass;

	public Material(String materialName, short[] rgba, int meltingPoint, int boilingPoint, long protons, long neutrons, boolean blastFurnace, MaterialStack[] inputs){
		
		this.unlocalizedName = Utils.sanitizeString(materialName);
		this.localizedName = materialName;
		this.RGBA = rgba;
		this.meltingPointC = meltingPoint;
		if (boilingPoint == 0){
			boilingPoint = meltingPoint*4;
		}
		this.boilingPointC = boilingPoint;
		this.meltingPointK = (int) MathUtils.celsiusToKelvin(meltingPointC);
		this.boilingPointK = (int) MathUtils.celsiusToKelvin(boilingPointC);
		this.vProtons = protons;
		this.vNeutrons = neutrons;
		this.vMass = getMass();
		this.usesBlastFurnace = blastFurnace;

		if (inputs == null){
			this.materialInput = null;			
		}
		else {
			if (inputs.length != 0){
				for (int i=0; i < inputs.length; i++){
					if (inputs[i] != null){
						this.materialInput[i] = inputs[i];
					}
				}
			}
		}
		Utils.LOG_INFO("Creating a Material instance for "+materialName);
		Utils.LOG_INFO("Protons: "+vProtons);
		Utils.LOG_INFO("Neutrons: "+vNeutrons);
		Utils.LOG_INFO("Mass: "+vMass+"/units");
		Utils.LOG_INFO("Melting Point: "+meltingPointC+"C.");
		Utils.LOG_INFO("Boiling Point: "+boilingPointC+"C.");
	}

	public String getLocalizedName(){
		return localizedName;
	}

	public String getUnlocalizedName(){
		return unlocalizedName;
	}

	public short[] getRGBA(){
		return RGBA;
	}

	public long getProtons() {
		return vProtons;
	}

	public long getNeutrons() {
		return vNeutrons;
	}

	public long getMass() {
		return vProtons + vNeutrons;
	}

	public int getMeltingPoint_C() {
		return meltingPointC;
	}

	public int getBoilingPoint_C() {
		return boilingPointC;
	}

	public boolean requiresBlastFurnace(){
		return usesBlastFurnace;
	}

	public ItemStack getDust(int stacksize){
		return UtilsItems.getItemStackOfAmountFromOreDictNoBroken("dust"+unlocalizedName, stacksize);
	}
	
	public ItemStack getSmallDust(int stacksize){
		return UtilsItems.getItemStackOfAmountFromOreDictNoBroken("dustSmall"+unlocalizedName, stacksize);
	}
	
	public ItemStack getTinyDust(int stacksize){
		return UtilsItems.getItemStackOfAmountFromOreDictNoBroken("dustTiny"+unlocalizedName, stacksize);
	}

	public ItemStack[] getValidInputStacks(){
		return UtilsItems.validItemsForOreDict(unlocalizedName);
	}

	public ItemStack[] getMaterialComposites(){
		//Utils.LOG_INFO("Something requested the materials needed for "+localizedName);
		if (materialInput != null && materialInput.length >= 1){
			ItemStack[] temp = new ItemStack[materialInput.length];
			for (int i=0;i<materialInput.length;i++){
				//Utils.LOG_INFO("i:"+i);
				ItemStack testNull = null;
				try {
				testNull = materialInput[i].getDustStack();
				} catch (Throwable r){
					Utils.LOG_INFO("Failed gathering material stack for "+localizedName+".");
					Utils.LOG_INFO("What Failed: Length:"+materialInput.length+" current:"+i);
				}
				try {
				if (testNull != null){
					//Utils.LOG_INFO("not null");
					temp[i] = materialInput[i].getDustStack();
				}
				} catch (Throwable r){
					Utils.LOG_INFO("Failed setting slot "+i+", using "+localizedName);
				}
			}		
			return temp;
		}
		return new ItemStack[]{};
	}

	public int[] getMaterialCompositeStackSizes(){
		if (materialInput != null && materialInput.length >= 1){
			int[] temp = new int[materialInput.length];
			for (int i=0;i<materialInput.length;i++){
				temp[i] = materialInput[i].getDustStack().stackSize;
			}		
			return temp;
		}
		return new int[]{};
	}

}
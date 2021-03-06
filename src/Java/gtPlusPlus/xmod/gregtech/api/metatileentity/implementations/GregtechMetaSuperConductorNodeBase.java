package gtPlusPlus.xmod.gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.GT_Values.V;

import java.util.Collection;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.lossless.GregtechMetaTileEntityLosslessBasicTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class GregtechMetaSuperConductorNodeBase extends GregtechMetaTileEntityLosslessBasicTank {
	public GregtechMetaSuperConductorNodeBase(final int aID, final String aName, final String aNameRegional, final int aTier, final String aDescription, final ITexture... aTextures) {
		super(aID, aName, aNameRegional, aTier, 3, aDescription, aTextures);
	}

	public GregtechMetaSuperConductorNodeBase(final String aName, final int aTier, final String aDescription, final ITexture[][][] aTextures) {
		super(aName, aTier, 3, aDescription, aTextures);
	}

	@Override
	public ITexture[][][] getTextureSet(final ITexture[] aTextures) {
		final ITexture[][][] rTextures = new ITexture[10][17][];
		for (byte i = -1; i < 16; i++) {
			rTextures[0][i + 1] = this.getFront(i);
			rTextures[1][i + 1] = this.getBack(i);
			rTextures[2][i + 1] = this.getBottom(i);
			rTextures[3][i + 1] = this.getTop(i);
			rTextures[4][i + 1] = this.getSides(i);
			rTextures[5][i + 1] = this.getFrontActive(i);
			rTextures[6][i + 1] = this.getBackActive(i);
			rTextures[7][i + 1] = this.getBottomActive(i);
			rTextures[8][i + 1] = this.getTopActive(i);
			rTextures[9][i + 1] = this.getSidesActive(i);
		}
		return rTextures;
	}

	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return this.mTextures[(aActive ? 5 : 0) + (aSide == aFacing ? 0 : aSide == GT_Utility.getOppositeSide(aFacing) ? 1 : aSide == 0 ? 2 : aSide == 1 ? 3 : 4)][aColorIndex + 1];
	}

	@Override
	public String[] getDescription() {
		return new String[]{this.mDescription, "Cooling Efficiency: " + this.getEfficiency() + "%", CORE.GT_Tooltip};
	}

	@Override
	public boolean onRightclick(final IGregTechTileEntity aBaseMetaTileEntity, final EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) {
			return true;
		}
		aBaseMetaTileEntity.openGUI(aPlayer);
		return true;
	}

	public ITexture[] getFront(final byte aColor) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColor + 1]};
	}

	public ITexture[] getBack(final byte aColor) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColor + 1]};
	}

	public ITexture[] getBottom(final byte aColor) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColor + 1]};
	}

	public ITexture[] getTop(final byte aColor) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColor + 1]};
	}

	public ITexture[] getSides(final byte aColor) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColor + 1]};
	}

	public ITexture[] getFrontActive(final byte aColor) {
		return this.getFront(aColor);
	}

	public ITexture[] getBackActive(final byte aColor) {
		return this.getBack(aColor);
	}

	public ITexture[] getBottomActive(final byte aColor) {
		return this.getBottom(aColor);
	}

	public ITexture[] getTopActive(final byte aColor) {
		return this.getTop(aColor);
	}

	public ITexture[] getSidesActive(final byte aColor) {
		return this.getSides(aColor);
	}

	@Override
	public boolean isFacingValid(final byte aSide) {
		return aSide > 1;
	}

	@Override
	public boolean isSimpleMachine() {
		return false;
	}

	@Override
	public boolean isValidSlot(final int aIndex) {
		return aIndex < 2;
	}

	@Override
	public boolean isEnetInput() {
		return true;
	}

	@Override
	public boolean isEnetOutput() {
		return true;
	}

	@Override
	public boolean isOutputFacing(final byte aSide) {
		return true;
	}

	@Override
	public boolean isAccessAllowed(final EntityPlayer aPlayer) {
		return true;
	}

	@Override
	public long getMinimumStoredEU() {
		return V[this.mTier] * 16 * this.mInventory.length;
	}

	@Override
	public long maxEUStore() {
		return V[this.mTier] * 64;
	}

	@Override
	public long maxEUInput() {
		return V[this.mTier];
	}

	@Override
	public long maxEUOutput() {
		return V[this.mTier];
	}

	@Override
	public long maxAmperesIn() {
		return 16;
	}

	@Override
	public long maxAmperesOut() {
		return 16;
	}

	@Override
	public boolean doesFillContainers() {
		return this.getBaseMetaTileEntity().isAllowedToWork();
	}

	@Override
	public boolean doesEmptyContainers() {
		return this.getBaseMetaTileEntity().isAllowedToWork();
	}

	@Override
	public boolean canTankBeFilled() {
		return this.getBaseMetaTileEntity().isAllowedToWork();
	}

	@Override
	public boolean canTankBeEmptied() {
		return this.getBaseMetaTileEntity().isAllowedToWork();
	}

	@Override
	public boolean displaysItemStack() {
		return true;
	}

	@Override
	public boolean displaysStackSize() {
		return false;
	}

	@Override
	public boolean isFluidInputAllowed(final FluidStack aFluid) {
		return this.getFuelValue(aFluid) > 0;
	}

	@Override
	public void onPostTick(final IGregTechTileEntity aBaseMetaTileEntity, final long aTick) {
		if ((this.getEUVar() == this.maxEUStore()) || (this.mFluid == null)){
			aBaseMetaTileEntity.disableWorking();
		}
		else {
			aBaseMetaTileEntity.enableWorking();
		}
		if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isAllowedToWork() && ((aTick % 10) == 0)) {
			if (this.mFluid == null) {
				if (aBaseMetaTileEntity.getUniversalEnergyStored() < (this.maxEUOutput() + this.getMinimumStoredEU())) {
					this.mInventory[this.getStackDisplaySlot()] = null;
				} else {
					if (this.mInventory[this.getStackDisplaySlot()] == null) {
						this.mInventory[this.getStackDisplaySlot()] = new ItemStack(Blocks.fire, 1);
					}
					this.mInventory[this.getStackDisplaySlot()].setStackDisplayName("Generating: " + (aBaseMetaTileEntity.getUniversalEnergyStored() - this.getMinimumStoredEU()) + " EU");
				}
			} else {
				final int tFuelValue = this.getFuelValue(this.mFluid), tConsumed = this.consumedFluidPerOperation(this.mFluid);
				if ((tConsumed > 0) && (this.mFluid.amount > tConsumed)) {
					final long tFluidAmountToUse = Math.min(this.mFluid.amount / tConsumed, (((this.maxEUOutput() * 20) + this.getMinimumStoredEU()) - aBaseMetaTileEntity.getUniversalEnergyStored()));
					if (tFluidAmountToUse > 0 /*&& aBaseMetaTileEntity.increaseStoredEnergyUnits(tFluidAmountToUse * tFuelValue, true)*/) {
						this.mFluid.amount -= tFluidAmountToUse * tConsumed;
					}
				}
			}
			if ((this.mInventory[this.getInputSlot()] != null) && (aBaseMetaTileEntity.getUniversalEnergyStored() < ((this.maxEUOutput() * 20) + this.getMinimumStoredEU())) && (GT_Utility.getFluidForFilledItem(this.mInventory[this.getInputSlot()], true) == null)) {
				final int tFuelValue = this.getFuelValue(this.mInventory[this.getInputSlot()]);
				if (tFuelValue >= 0) {
					final ItemStack tEmptyContainer = this.getEmptyContainer(this.mInventory[this.getInputSlot()]);
					if (aBaseMetaTileEntity.addStackToSlot(this.getOutputSlot(), tEmptyContainer)) {
						//aBaseMetaTileEntity.increaseStoredEnergyUnits(tFuelValue, true);
						aBaseMetaTileEntity.decrStackSize(this.getInputSlot(), 1);
					}
				}
			}
		}

		if (aBaseMetaTileEntity.isServerSide()) {
			aBaseMetaTileEntity.setActive(aBaseMetaTileEntity.isAllowedToWork() && (aBaseMetaTileEntity.getUniversalEnergyStored() >= (this.maxEUOutput() + this.getMinimumStoredEU())));
		}
	}

	public abstract GT_Recipe_Map getRecipes();

	public abstract int getEfficiency();

	public int consumedFluidPerOperation(final FluidStack aLiquid) {
		return 1;
	}

	public int getFuelValue(final FluidStack aLiquid) {
		if ((aLiquid == null) || (this.getRecipes() == null)) {
			return 0;
		}
		FluidStack tLiquid;
		final Collection<GT_Recipe> tRecipeList = this.getRecipes().mRecipeList;
		if (tRecipeList != null) {
			for (final GT_Recipe tFuel : tRecipeList) {
				if ((tLiquid = GT_Utility.getFluidForFilledItem(tFuel.getRepresentativeInput(0), true)) != null) {
					if (aLiquid.isFluidEqual(tLiquid)) {
						return 0;
					}
				}
			}
		}
		//return (int) (((long) tFuel.mSpecialValue * getEfficiency() * consumedFluidPerOperation(tLiquid)) / 100);
		return 0;
	}

	public int getFuelValue(final ItemStack aStack) {
		if (GT_Utility.isStackInvalid(aStack) || (this.getRecipes() == null)) {
			return 0;
		}
		final GT_Recipe tFuel = this.getRecipes().findRecipe(this.getBaseMetaTileEntity(), false, Long.MAX_VALUE, null, aStack);
		if (tFuel != null) {
			return 0;
		}
		//return (int) ((tFuel.mSpecialValue * 1000L * getEfficiency()) / 100);
		return 0;
	}

	public ItemStack getEmptyContainer(final ItemStack aStack) {
		if (GT_Utility.isStackInvalid(aStack) || (this.getRecipes() == null)) {
			return null;
		}
		final GT_Recipe tFuel = this.getRecipes().findRecipe(this.getBaseMetaTileEntity(), false, Long.MAX_VALUE, null, aStack);
		if (tFuel != null) {
			return GT_Utility.copy(tFuel.getOutput(0));
		}
		return GT_Utility.getContainerItem(aStack, true);
	}

	@Override
	public boolean allowPutStack(final IGregTechTileEntity aBaseMetaTileEntity, final int aIndex, final byte aSide, final ItemStack aStack) {
		return super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack) && ((this.getFuelValue(aStack) > 0) || (this.getFuelValue(GT_Utility.getFluidForFilledItem(aStack, true)) > 0));
	}

	@Override
	public int getCapacity() {
		return 16000;
	}

	@Override
	public int getTankPressure() {
		return -100;
	}


}

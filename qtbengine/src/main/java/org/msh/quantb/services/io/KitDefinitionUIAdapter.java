package org.msh.quantb.services.io;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.msh.quantb.model.forecast.ForecastingBatch;
import org.msh.quantb.model.forecast.ForecastingOrder;
import org.msh.quantb.model.forecast.KitDefinition;
import org.msh.quantb.model.mvp.ModelFactory;

/**
 * Medicine kit for ForecastingRegimenUIAdapter
 * @author alexk
 *
 */
public class KitDefinitionUIAdapter extends AbstractUIAdapter {

	private KitDefinition kitDefinition = null;

	public  KitDefinitionUIAdapter(KitDefinition _kitDefinition) {
		if(_kitDefinition != null) {
			this.kitDefinition = _kitDefinition;
		}else {
			this.kitDefinition = ModelFactory.of("").createKit();
		}
	}

	public KitDefinition getKitDefinition() {
		return kitDefinition;
	}

	public void setKitDefinition(KitDefinition kitDefinition) {
		this.kitDefinition = kitDefinition;
	}

	/**
	 * Unique name of the kit intends to the regimen in the forecasting
	 * @return
	 */
	public String getKitName() {
		if(this.kitDefinition.getKitName()==null) {
			this.kitDefinition.setKitName("");
		}
		return kitDefinition.getKitName();
	}

	/**
	 * Unique name of the kit intends to the regimen in the forecasting
	 * @return
	 */
	public void setKitName(String value) {
		String oldValue = getKitName();
		kitDefinition.setKitName(value);
		firePropertyChange("kitName", oldValue, value);
	}

	/**
	 * Batches in the stock sorted by expire date 
	 * @return
	 */
	public List<ForecastingBatchUIAdapter> getInventory() {
		List<ForecastingBatchUIAdapter> ret = new ArrayList<ForecastingBatchUIAdapter>();
		for(ForecastingBatch kit : getKitDefinition().getInventory()) {
			ret.add(new ForecastingBatchUIAdapter(kit));
		}
		Collections.sort(ret);
		return ret;
	}

	/**
	 * Get orders related to this kit
	 * @return
	 */
	public List<ForecastingOrderUIAdapter> getOrders() {
		List<ForecastingOrderUIAdapter> ret = new ArrayList<ForecastingOrderUIAdapter>();
		if(getKitDefinition() != null && getKitDefinition().getOrders() != null) {
			for(ForecastingOrder ford : getKitDefinition().getOrders()) {
				ret.add(new ForecastingOrderUIAdapter(ford));
			}
		}
		Collections.sort(ret);
		return ret;
	}

	/**
	 * Sorted by expire date batches for kits available
	 * @return
	 */
	public List<ForecastingBatchUIAdapter> getAllBatches() {
		List<ForecastingBatchUIAdapter> ret = new ArrayList<ForecastingBatchUIAdapter>();
		if(getKitDefinition().getInventory() != null) {
			for(ForecastingBatch kit : getKitDefinition().getInventory()) {
				ret.add(new ForecastingBatchUIAdapter(kit));
			}
		}
		if(getKitDefinition().getOrders() != null) {
			for(ForecastingOrder ford : getKitDefinition().getOrders()) {
				ret.add(new ForecastingBatchUIAdapter(ford.getBatch()));
			}
		}
		Collections.sort(ret);
		return ret;
	}


	/**
	 * The regimen in the forecasting is not for kit
	 * @return
	 */
	public boolean isNotKit() {
		return getKitName().length()==0;
	}

}

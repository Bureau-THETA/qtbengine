package org.msh.quantb.services.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jdesktop.observablecollections.ObservableCollections;
import org.msh.quantb.model.gen.Regimen;
import org.msh.quantb.model.gen.RegimenTypesEnum;
import org.msh.quantb.model.regimen.Regimens;

/**
 * Adapter for regimens list
 * @author alexey
 *
 */
public class RegimensDicUIAdapter extends AbstractUIAdapter {
	private Regimens regimens;
	private List<RegimenUIAdapter> regimensDic = new ArrayList<RegimenUIAdapter>();
	//filter to select regimens
	private RegimenTypesEnum filter = RegimenTypesEnum.MULTI_DRUG;
	private String filterStr = "";
	/**
	 * Only valid constructor
	 * @param regimensDic
	 */
	public RegimensDicUIAdapter(Regimens _regimens){		
		assert(_regimens != null);
		this.regimens = _regimens;
		List<RegimenUIAdapter> rl = new ArrayList<RegimenUIAdapter>();
		for (Regimen reg : this.regimens.getRegimen()){
			if(reg.getType() == null){
				reg.setType(RegimenTypesEnum.MULTI_DRUG);
			}
			RegimenUIAdapter ad = new RegimenUIAdapter(reg);
			rl.add(ad);
		}
		regimensDic = ObservableCollections.observableList(rl);
	}

	/**
	 * Get object
	 * @return
	 */
	public Regimens getRegimensObj(){
		return regimens;
	}
	/**
	 * Get FILTERED regimens
	 * @return the regimens
	 */
	public List<RegimenUIAdapter> getRegimens() {
		List<RegimenUIAdapter> rl = new ArrayList<RegimenUIAdapter>();
		for(RegimenUIAdapter rui : regimensDic){
			if(rui.getType() == filter){
				if(getFilterStr().length()>0) {
					if(rui.getNameWithForDisplay().toUpperCase().contains(getFilterStr().toUpperCase())){
						rl.add(rui);
					}
				}else {
					rl.add(rui);
				}
			}
		}
		Collections.sort(rl, new Comparator<RegimenUIAdapter>(){

			@Override
			public int compare(RegimenUIAdapter arg0, RegimenUIAdapter arg1) {
				int res = arg0.getName().compareTo(arg1.getName());
				if (res == 0){
					return 1;
				}else{
					return res;
				}
			}
			
		});
		return rl;
	}
	
	/**
	 * Regimes filter as string
	 * @return
	 */
	public String getFilterStr() {
		return filterStr;
	}
	
	/**
	 * Regimes filter as string
	 * getRegimens() is sensitive to it
	 * @param filterStr
	 */
	public void setFilterStr(String filterStr) {
		String oldValue=getFilterStr();
		this.filterStr = filterStr;
		firePropertyChange("regimens", oldValue, getRegimens());
		firePropertyChange("filterStr", oldValue, getRegimens());
	}

	/**
	 * set new filter and change selected regimens list
	 * @param _filter
	 */
	public void setFilter(RegimenTypesEnum _filter) {
		List<RegimenUIAdapter> oldValue = getRegimens();
		this.filter = _filter;
		firePropertyChange("regimens", oldValue, getRegimens());
		firePropertyChange("filter", oldValue, getRegimens());

	}
	/**
	 * Get regimens, but not rebuild them
	 * @return
	 */
	public List<RegimenUIAdapter> getSavedRegimens() {
		return regimensDic;
	}
	/**
	 * Get current filter
	 * @return
	 */
	public RegimenTypesEnum getFilter() {
		return this.filter;
	}
	/**
	 * Select all regimens
	 */
	public void selectAll() {
		for(RegimenUIAdapter rui : this.regimensDic){
			rui.setChecked(true);
		}
		
	}
	/**
	 * Get all regimens, filter will not be applied
	 * @return
	 */
	public List<RegimenUIAdapter> getAllRegimens() {
		return regimensDic;
	}
	/**
	 * add regimen if one not exist in dictionary
	 * @param fcRegUi
	 * @return true if regimen has been added
	 */
	public boolean addIfNotExist(RegimenUIAdapter fcRegUi) {
		if(!getAllRegimens().contains(fcRegUi)){
			getRegimensObj().getRegimen().add(fcRegUi.getRegimen());
			return true;
		}else{
			return false;
		}
		
	}


	
	

}

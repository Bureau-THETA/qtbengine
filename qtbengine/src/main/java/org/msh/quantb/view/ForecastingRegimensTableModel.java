package org.msh.quantb.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import org.msh.quantb.model.gen.RegimenTypesEnum;
import org.msh.quantb.services.io.ForecastUIAdapter;
import org.msh.quantb.services.io.ForecastingRegimenUIAdapter;
import org.msh.quantb.services.io.MonthUIAdapter;
import org.msh.quantb.services.mvp.Messages;


/**
 * Table model for forecasting regimens cases on treatment.
 * 
 * @author user
 * 
 */
public class ForecastingRegimensTableModel extends AbstractTableModel implements HasRegimenData {

	private static final long serialVersionUID = -2111904609994478109L;
	private List<ForecastingRegimenUIAdapter> data;
	private Integer columnCount;
	private ForecastingRegimenUIAdapter maxRegimen;
	private ForecastUIAdapter forecast;
	private HasCalculationDetails mainTabPane;

	/**
	 * Constructor
	 * 
	 * @param _forecast current forecast
	 * @param forecastingDocumentPAnel - panel
	 */
	public ForecastingRegimensTableModel(ForecastUIAdapter _forecast, HasCalculationDetails forecastingDocumentPanel) {
		this.data = _forecast.getRegimes();
		this.forecast = _forecast;
		this.mainTabPane=forecastingDocumentPanel;
	}
	

	public List<ForecastingRegimenUIAdapter> getData() {
		return data;
	}



	@Override
	public int getRowCount() {
		return data != null ? data.size() : 0;
	}

	@Override
	public int getColumnCount() {
		if (columnCount==null){
			int maxLength = 0;
			if (data != null) {
				for (ForecastingRegimenUIAdapter frui : data) {
					if (frui != null && frui.getCasesOnTreatment().size() > maxLength) {
						maxLength = frui.getCasesOnTreatment().size();
						maxRegimen = frui;
					}
				}
			}
			columnCount = new Integer(maxLength+2);
		}
		return columnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//if (data == null || rowIndex < 0 || rowIndex >= data.size() || columnIndex < 0 || (!data.isEmpty() && columnIndex > data.iterator().next().getCasesOnTreatment().size())) return null;
		int durationInMonths = 0;
		//nothing to do
		if (data == null || rowIndex < 0 || rowIndex >= data.size() || columnIndex < 0 || columnIndex > columnCount){
			return null;
		}
		//the checkbox
		if (columnIndex==0){
			return !data.get(rowIndex).isExcludeCasesOnTreatment();
		}
		//regimen (medicine) name
		if (columnIndex == 1) {
			String ret = String.format("%4d",rowIndex+1)+". "  + data.get(rowIndex).getRegimen().getNameWithForDisplay();
			//return createNameWithForDisplayHTML(ret);
			return ret;
		}
		//may be on empty leftmost cells, not all regimes have equal lengths
		if (!data.isEmpty() && columnIndex>0){
			durationInMonths = data.get(rowIndex).getCasesOnTreatment().size();			
			if (columnIndex-2 <columnCount-2 - durationInMonths) {
				return null;
			}
		}		
		//real quantities
		return data.get(rowIndex).getCasesOnTreatment().get(columnIndex + durationInMonths - columnCount).getIQuantity();
	}
	
/**
 * Try correct word wrap for regimen name to display in a label
 * @param orig
 * @param width
 * @return
 */
	public static String createNameWithForDisplayHTML(String orig, int width) {
				int STRING_WIDE = width;
				int pos= orig.indexOf(":")+1;
				String disp="";
				if(pos<width) {
					disp ="<b>"+orig.substring(0, pos)+"</b>";
				}else {
					disp="<b>"+orig.substring(0, width-5)+"<br>"+orig.substring(width-5,pos) +"</b>";
				}
				String rest = orig.substring(pos, orig.length());
				while(rest.length()>0) {
					if(rest.length()>STRING_WIDE) {
						String tmp=rest.substring(0,STRING_WIDE);
						int tail=tmp.lastIndexOf(")")+1;
						if(tail>0) {
							tmp=tmp.substring(0,tail);
						}else {
							tmp=tmp.substring(0,STRING_WIDE);
						}
						disp=disp+"<br>"+tmp;
						rest=rest.substring(tmp.length(),rest.length());
					}else {
						disp=disp+"<br>"+rest;
						rest="";
					}
				}
				String text = String.format("<div style=\"word-wrap:break-word;\">%s</div>", 
						 disp);
				return text;
	}
	/**
	 * Make a beauty line number from an integer
	 * @param i
	 * @return
	 */
	private String makeNumber(int i) {
		String ret = "   "+i;
		int endIndex=ret.length();
		int beginIndex=endIndex-3;
		return ret.substring(beginIndex, endIndex);
	}


	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		//if (data == null || rowIndex < 0 || rowIndex >= data.size() || columnIndex <= 0 || (!data.isEmpty() && columnIndex > data.iterator().next().getCasesOnTreatment().size())) return;
		int durationInMonths = 0;
		//nothing to set
		if (data == null || rowIndex < 0 || rowIndex >= data.size() || columnIndex < 0  || columnIndex > columnCount){
			return;
		}
		// set include - exclude flag
		if(columnIndex==0){
			data.get(rowIndex).setExcludeCasesOnTreatment(!(Boolean) aValue);
			this.fireTableRowsUpdated(rowIndex, rowIndex);
			this.mainTabPane.setVisibleCalculationDetailsTabs(false);
		}
		//try to set quantities
		if (!data.isEmpty() && columnIndex>1){
			durationInMonths = data.get(rowIndex).getCasesOnTreatment().size();	
			//do not set any, on leftmost empty cells (if exists)
			if (columnIndex-2 <columnCount-2 - durationInMonths){
				return;
			}
			//really set
			try {
				int value = Integer.valueOf((String) aValue);
				data.get(rowIndex).getCasesOnTreatment().get(columnIndex + durationInMonths - columnCount).setIQuantity(new Integer(value));
				this.mainTabPane.setVisibleCalculationDetailsTabs(false);
			} catch (NumberFormatException ex) {}
		}
	}

	@Override
	public String getColumnName(int column) {
		if (data == null || data.isEmpty() || column < 0 || column > columnCount) { return null; }
		//it's include - exclude flag
		if(column==0){
			return Messages.getString("ForecastingDocumentWindow.tbParameters.SubTab.NewCases.disable");
		}
		String result = null;
		if (column > 1) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy", Locale.of(Messages.getLanguage(), Messages.getCountry()));
			Calendar cal = GregorianCalendar.getInstance();
			MonthUIAdapter adapter = maxRegimen.getCasesOnTreatment().get(column - 2).getMonth();
			int year = adapter.getYear();
			int month = adapter.getMonth();
			int date = 1;
			cal.set(year, month, date);
			result = dateFormat.format(cal.getTime());
		} else {
			if (this.forecast.getRegimensType() == RegimenTypesEnum.MULTI_DRUG){
				result = Messages.getString("Regimen.clmn.Regimen");
			}else{
				result = Messages.getString("Regimen.clmn.medicines");
			}
		}
		return result;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {	
		if (columnIndex == 0){
			return true; //include - exclude flag
		}
		if (columnIndex == 1) {
			return false; // name of medicine (regime)
		}
		if (data!=null && !data.isEmpty() && rowIndex<data.size() && columnIndex!=0){
			int durationInMonths = data.get(rowIndex).getCasesOnTreatment().size();			
			if (columnIndex-2 <columnCount-2 - durationInMonths){
				return false; //leftmost empty cells (if exist)
			}else{
				return true; // quantities
			}
		}else{
			return false; //something impossible
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0){
			return Boolean.class;
		}else{
			return Object.class;
		}
	}
	/**
	 * Is this row editable
	 * @param row
	 * @return
	 */
	public boolean isRowEditable(int row) {
		return !data.get(row).isExcludeCasesOnTreatment();
	}
	/**
	 * Check possibility to edit all rows
	 * @param rows array with rows numbers
	 * @return
	 */
	public boolean isRowsEditable(int[] rows) {
		boolean ret = true;
		for(int i=0; i<rows.length; i++){
			ret = isRowEditable(rows[i]) & ret;
		}
		return ret;
	}
}

package org.msh.quantb.services.io;

import org.msh.quantb.model.forecast.KitDefinition;
import org.msh.quantb.model.gen.Classifier2018;
import org.msh.quantb.model.gen.ClassifierARV;
import org.msh.quantb.model.gen.ClassifierTypesEnum;
import org.msh.quantb.model.gen.FormARV;
import org.msh.quantb.model.gen.Medicine;
import org.msh.quantb.model.gen.MedicineFormEnum;
import org.msh.quantb.model.gen.MedicineTypesEnum;
import org.msh.quantb.model.mvp.ModelFactory;
import org.msh.quantb.services.mvp.Messages;

/**
 * Medicine object adapted for UI operation Fires property change events
 * 
 * @author alexey
 * 
 */
@SuppressWarnings("rawtypes")
public class MedicineUIAdapter extends AbstractUIAdapter implements Comparable {
	private Medicine medicine = new Medicine();

	/**
	 * only valid constructor
	 * 
	 * @param med
	 */
	public MedicineUIAdapter(Medicine med) {
		medicine = med;
	}

	/**
	 * @return the medicine
	 */
	public Medicine getMedicine() {
		return medicine;
	}

	public String getName() {
		return medicine.getName();
	}

	public void setName(String name) {
		String old = getName();
		medicine.setName(name.trim());
		firePropertyChange("name", old, name);
	}

	/**
	 * Get medicine name for display 
	 * @return medicine name with strength/Unit and dosage form
	 */
	public String getNameForDisplay(){
		return medicine.getName() + "  " + medicine.getStrength() + "  " + getFormDosage();
	}

	/**
	 * Get medicine form backward compatible with the previous version
	 * @return
	 */
	public String getFormDosage() {
		String formDosage="";
		if (this.getForm() == null){
			formDosage = getDosage();
		}else{
			if(this.getForm()!= MedicineFormEnum.NA){
				if (this.getForm() == MedicineFormEnum.OTHER){
					formDosage = getDosage();
					if(formDosage.trim().length()==0){
						formDosage = Messages.getString("Medicine.forms."+this.getForm().toString());
					}
				}else{
					formDosage = Messages.getString("Medicine.forms." + this.getForm().toString());
				}
			}
		}
		return formDosage;
	}
	/**
	 * Mainly we do not need "Not Applicable" for form dosage, but for list of medicines
	 * @return
	 */
	public String getFormDosageNotEmpty(){
		String ret = getFormDosage();
		if(ret.trim().length()==0){
			ret = Messages.getString("Medicine.forms."+this.getForm().toString());
		}
		return ret;
	}

	/**
	 * Get medicine name for display with abbreviate name
	 * @return medicine name with strength/Unit and dosage form
	 */
	public String getNameForDisplayWithAbbrev(){
		return medicine.getAbbrevName() + "  " + medicine.getName() + "  " + medicine.getStrength() + "  " + getFormDosage();
	}

	/**
	 * @return
	 * @see org.msh.quantb.model.gen.Medicine#getAbbrevName()
	 */
	public String getAbbrevName() {
		return medicine.getAbbrevName();
	}


	/**
	 * @param name
	 * @see org.msh.quantb.model.gen.Medicine#setAbbrevName(java.lang.String)
	 */
	public void setAbbrevName(String name) {
		String old = getAbbrevName();
		medicine.setAbbrevName(name);
		firePropertyChange("abbrevName", old, name);
	}

	/**
	 * @return
	 * @see org.msh.quantb.model.gen.Medicine#getStrength()
	 */
	public String getStrength() {
		return medicine.getStrength();
	}

	/**
	 * @param value
	 * @see org.msh.quantb.model.gen.Medicine#setStrength(String)
	 */
	public void setStrength(String value) {
		String old = getStrength();
		medicine.setStrength(value);
		firePropertyChange("strength", old, getStrength());	
	}

	/**
	 * @param _form
	 * @see org.msh.quantb.model.gen.Medicine#setForm(MedicineFormEnum)
	 */
	public void setForm(MedicineFormEnum _form){
		MedicineFormEnum oldValue = getForm();
		String oldFormDosage = getFormDosage();
		medicine.setForm(_form);
		firePropertyChange("form", oldValue, getForm());
		firePropertyChange("formDosage", oldFormDosage, getFormDosage());

	}


	/**
	 * @return
	 * @see org.msh.quantb.model.gen.Medicine#getForm()
	 */
	public MedicineFormEnum getForm() {
		if (medicine.getForm() != null){
			return medicine.getForm();
		}else{
			return MedicineFormEnum.NA;
		}
	}
	
	/**
	 * @param _form
	 * @see org.msh.quantb.model.gen.Medicine#setForm(MedicineFormEnum)
	 */
	public void setFormARV(FormARV _form){
		FormARV oldValue = getFormARV();
		String oldFormDosage = getFormDosage();
		medicine.setFormARV(_form);
		firePropertyChange("form", oldValue, getFormARV());
		firePropertyChange("formDosage", oldFormDosage, getFormDosage());

	}


	/**
	 * @return
	 * @see org.msh.quantb.model.gen.Medicine#getForm()
	 */
	public FormARV getFormARV() {
		if (medicine.getFormARV() != null){
			return medicine.getFormARV();
		}else{
			return FormARV.UNKNOWN;
		}
	}
	
	public String getFormARVString() {
		return Messages.getString("Medicine.formsARV."+getFormARV());
	}

	/**
	 * @return
	 * @see org.msh.quantb.model.gen.Medicine#getDosage()
	 */
	public String getDosage() {
		return medicine.getDosage();
	}

	/**
	 * @param value
	 * @see org.msh.quantb.model.gen.Medicine#setDosage(String)
	 */
	public void setDosage(String value) {
		String old = getDosage();
		String oldFormDosage = getFormDosage();
		medicine.setDosage(value);
		firePropertyChange("dosage", old, getDosage());
		firePropertyChange("formDosage", oldFormDosage, getFormDosage());
	}	
	/**
	 * Injectable must be on the top of the list, so injectable always less
	 */
	@Override
	public int compareTo(Object o) {
		if (o == null){
			return -1;
		}
		if (o instanceof MedicineUIAdapter){
			MedicineUIAdapter another = (MedicineUIAdapter) o;
			if (this.isInjectable() && !another.isInjectable()){
				return -1;
			}
			if (!this.isInjectable() && another.isInjectable()){
				return 1;
			}
			if (getAbbrevName() == null)
				return -1;
			if (another.getAbbrevName() == null)
				return 1;
			if (this.equals(another)){
				return 0;
			}
			int sort = getAbbrevName().compareTo(another.getAbbrevName());
			if (sort == 0){
				return -1;
			}else{
				return sort;
			}
		}else{
			return -1;
		}
	}

	/**
	 * is this medicine injectable?
	 * @return
	 */
	private boolean isInjectable() {
		if (this.getForm() == null){
			return false;
		}
		if (this.getForm() == MedicineFormEnum.POWDER_FOR_INJECTION || 
				this.getForm() == MedicineFormEnum.SOLUTION_FOR_INJECTION){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MedicineUIAdapter other = (MedicineUIAdapter) obj;
		if (this.getAbbrevName() == null) {
			if (other.getAbbrevName() != null) {
				return false;
			}
		} else if (!this.getAbbrevName().equals(other.getAbbrevName())) {
			return false;
		}
		if (this.getForm() == null) {
			if (other.getForm() != null) {
				return false;
			}
		} else if (!this.getForm().equals(other.getForm())) {
			return false;
		}
		
		if (this.getFormARV() == null) {
			if (other.getFormARV() != null) {
				return false;
			}
		} else if (!this.getFormARV().equals(other.getFormARV())) {
			return false;
		}

		if (this.getDosage() == null) {
			if (other.getDosage() != null) {
				return false;
			}
		} else if (!this.getDosage().equals(other.getDosage())) {
			return false;
		}

		if (this.getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!this.getName().equals(other.getName())) {
			return false;
		}
		if (this.getStrength() == null) {
			if (other.getStrength() != null) {
				return false;
			}
		} else if (!this.getStrength().equals(other.getStrength())) {
			return false;
		}

		/*		if(!this.getTypeAsString().equalsIgnoreCase(other.getTypeAsString())){
			return false;
		}

		if(!this.getClassifierAsString().equalsIgnoreCase(other.getClassifierAsString())){
			return false;
		}*/

		/*		if (this.getType() == MedicineTypesEnum.UNKNOWN) {
			if (other.getType() != MedicineTypesEnum.UNKNOWN) {
				this.setType(other.getType());
				return true;
			}
		} else if (!this.getType().equals(other.getType())) {
			if(other.getType()!= MedicineTypesEnum.UNKNOWN){
				return false;
			}else{
				other.setType(this.getType());
				return true;
			}
		}		*/
		return true;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAbbrevName() == null) ? 0 : getAbbrevName().hashCode()) + (getStrength()==null?0:getStrength().hashCode()) + (getForm()==null ? 0:getForm().hashCode());
		return result;
	}

	/**
	 * @param medicine
	 *            the medicine to set
	 */
	public void setMedicine(Medicine medicine) {
		Medicine old = getMedicine();
		this.medicine = medicine;
		firePropertyChange("medicine", old, getMedicine());
	}

	/**
	 * Get clone of current object
	 * @param model model factort
	 * @return clone
	 */
	public MedicineUIAdapter createClone(ModelFactory model){
		Medicine med = model.createMedicine();
		med.setAbbrevName(getAbbrevName());
		med.setForm(getForm());
		med.setDosage(getDosage());
		med.setName(getName());
		med.setStrength(getStrength());	
		med.setType(getType());
		med.setClassifier(getClassifier());
		med.setType2018(getType2018());
		med.setTypeARV(getTypeARV());
		med.setFormARV(getFormARV());
		return new MedicineUIAdapter(med );
	}

	@Override
	public String toString() {
		return getAbbrevName() + " " + getName();
	}

	/**
	 * @return
	 * @see org.msh.quantb.model.gen.Medicine#getType()
	 */
	public MedicineTypesEnum getType() {
		MedicineTypesEnum ret = medicine.getType();
		if (ret == null){
			ret = MedicineTypesEnum.UNKNOWN;
		}
		return ret;
	}

	/**
	 * @param value
	 * @see org.msh.quantb.model.gen.Medicine#setType(org.msh.quantb.model.gen.MedicineTypesEnum)
	 */
	public void setType(MedicineTypesEnum value) {
		MedicineTypesEnum oldValue = getType();
		medicine.setType(value);
		firePropertyChange("type", oldValue, getType());
	}
	/**
	 * New classification from 2018
	 * @return
	 */
	public Classifier2018 getType2018(){
		Classifier2018 ret = medicine.getType2018();
		if (ret == null){
			ret = Classifier2018.UNKNOWN;
		}
		return ret;
	}
	/**
	 * New classification from 2018
	 * @return
	 */
	public void setTypeARV(ClassifierARV typeARV){
		ClassifierARV oldValue = getTypeARV();
		medicine.setTypeARV(typeARV);
		firePropertyChange("typeARV", oldValue, getTypeARV());
	}
	
	/**
	 * New classification ARV
	 * @return
	 */
	public ClassifierARV getTypeARV(){
		ClassifierARV ret = medicine.getTypeARV();
		if (ret == null){
			ret = ClassifierARV.UNKNOWN;
		}
		return ret;
	}
	
	public String getTypeARVString(){
		return Messages.getString("Medicine.ARV."+getTypeARV());
	}
	
	/**
	 * New classification from 2018
	 * @return
	 */
	public void setType2018(Classifier2018 type2018){
		Classifier2018 oldValue = getType2018();
		medicine.setType2018(type2018);
		firePropertyChange("type2018", oldValue, getType2018());
	}
	
	
	/**
	 * Get only abbreviated name of the medicine, without dosage, spaces, etc
	 * @return
	 */
	public String getOnlyAbbrevName() {
		//String resultString = getAbbrevName().replaceAll("[^\\p{L}]", "");
		String resultString = getAbbrevName();
		return resultString;
	}
	/**
	 * Get medicine type as string
	 * @return
	 */
	public String getTypeAsString() {
		MedicineTypesEnum tmp = getType();
		if(tmp != null){
			return Messages.getString("Medicine.types."+tmp.toString().trim());
		}else{
			return "-";
		}
	}

	public ClassifierTypesEnum getClassifier(){
		ClassifierTypesEnum ret = getMedicine().getClassifier();
		if(ret == null){
			ret = ClassifierTypesEnum.UNKNOWN;
		}
		return ret;
	}

	public void setClassifier(ClassifierTypesEnum value){
		ClassifierTypesEnum oldValue = getClassifier();
		getMedicine().setClassifier(value);
		firePropertyChange("classifier", oldValue, getClassifier());

	}

	public String getClassifierAsString(){
		Classifier2018 tmp = getType2018();
		if(tmp != Classifier2018.UNKNOWN){
			return Messages.getString("Medicine.types2018."+getType2018().toString().trim());
		}else{
			return "-";
		}
	}

	/**
	 * ��� ��������� ������ equals ����������� ������ ���� �����
	 * @param obj
	 * @return
	 */
	public String createQuanTbKey() {
		String key = this.getAbbrevName() + this.getForm() + this.getDosage() + this.getName() + this.getStrength();
		return key;
	}
	/**
	 * Create medicine UI adapter based on Medicine kit
	 * @param kitDefinition
	 * @return
	 */
	public static MedicineUIAdapter of(KitDefinition kitDefinition) {
			org.msh.quantb.model.gen.Medicine fMed = ModelFactory.of("").createMedicine();
			fMed.setAbbrevName(kitDefinition.getKitName());
			fMed.setClassifier(ClassifierTypesEnum.UNKNOWN);
			fMed.setDosage("1");
			fMed.setForm(MedicineFormEnum.OTHER);
			fMed.setFormARV(FormARV.UNKNOWN);
			fMed.setName(kitDefinition.getKitName());
			fMed.setStrength("1");
			fMed.setType(MedicineTypesEnum.UNKNOWN);
			fMed.setType2018(Classifier2018.UNKNOWN);
			fMed.setTypeARV(ClassifierARV.UNKNOWN);
			return new MedicineUIAdapter(fMed);
		}

}

import java.util.EventObject;

public class FormEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	private String name;
	private String occupation;
	private String ageCategory;
	private String employmentCategory;
	private String taxId;
	private boolean usCitizen;
	private String gender;
	
	public FormEvent(Object source, String name, String occupation,
			String ageCat, String employmentCategory, String taxId,
			boolean usCitizen, String gender) {
		super(source);

		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCat;
		this.employmentCategory = employmentCategory;
		this.taxId = taxId;
		this.usCitizen = usCitizen;
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public String getTaxId() {
		return taxId;
	}

	public boolean IsUsCitizen() {
		return usCitizen;
	}

	public String getEmploymentCategory() {
		return employmentCategory;
	}

	public String getName() {
		return name;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getAgeCategory() {
		return ageCategory.toString();
	}

}

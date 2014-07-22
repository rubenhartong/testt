package domein;

public class MailFormat {
	int id;
	String naam, format, element1, element2, element3;
	
	public MailFormat(int id, String naam, String format, String element1, String element2,
			String element3) {
		super();
		this.id = id;
		this.format = format;
		this.naam = naam;
		this.element1 = element1;
		this.element2 = element2;
		this.element3 = element3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getNaam(){
		return naam;
	}
	public void setNaam(String naam){
		this.naam = naam;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getElement1() {
		return element1;
	}

	public void setElement1(String element1) {
		this.element1 = element1;
	}

	public String getElement2() {
		return element2;
	}

	public void setElement2(String element2) {
		this.element2 = element2;
	}

	public String getElement3() {
		return element3;
	}

	public void setElement3(String element3) {
		this.element3 = element3;
	}
	
	
}

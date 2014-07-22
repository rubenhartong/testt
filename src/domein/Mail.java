package domein;



public class Mail {
	private int id;
	private String eventID;
	private String naam, link;
	private String image_header, image_subheader, image;
	private String content1, content2, header1, header2;
	private String afzender; 
	private String ontvanger;
	private String onderwerp;
	private String aanhef;


	
	public Mail(int id, String eventID,String naam, String link, String image_header,
			String image_subheader, String image, String content1,
			String content2, String header1, String header2, String afzender,
			String ontvanger, String onderwerp, String aanhef) {
		super();
		this.id = id;
		this.eventID = eventID;
		this.naam = naam;
		this.link = link;
		this.image_header = image_header;
		this.image_subheader = image_subheader;
		this.image = image;
		this.content1 = content1;
		this.content2 = content2;
		this.header1 = header1;
		this.header2 = header2;
		this.afzender = afzender;
		this.ontvanger = ontvanger;
		this.onderwerp = onderwerp;
		this.aanhef = aanhef;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEventID() {
		return eventID;
	}


	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	
	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getImage_header() {
		return image_header;
	}


	public void setImage_header(String image_header) {
		this.image_header = image_header;
	}


	public String getImage_subheader() {
		return image_subheader;
	}


	public void setImage_subheader(String image_subheader) {
		this.image_subheader = image_subheader;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getContent1() {
		return content1;
	}


	public void setContent1(String content1) {
		this.content1 = content1;
	}


	public String getContent2() {
		return content2;
	}


	public void setContent2(String content2) {
		this.content2 = content2;
	}


	public String getHeader1() {
		return header1;
	}


	public void setHeader1(String header1) {
		this.header1 = header1;
	}


	public String getHeader2() {
		return header2;
	}


	public void setHeader2(String header2) {
		this.header2 = header2;
	}


	public String getAfzender() {
		return afzender;
	}


	public void setAfzender(String afzender) {
		this.afzender = afzender;
	}


	public String getOntvanger() {
		return ontvanger;
	}


	public void setOntvanger(String ontvanger) {
		this.ontvanger = ontvanger;
	}


	public String getOnderwerp() {
		return onderwerp;
	}


	public void setOnderwerp(String onderwerp) {
		this.onderwerp = onderwerp;
	}


	public String getAanhef() {
		return aanhef;
	}


	public void setAanhef(String aanhef) {
		this.aanhef = aanhef;
	}





	
	
	
	
	
}



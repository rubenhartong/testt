package domein;

public class Programma {

    private String vanTijd, totTijd, programmatxt;
    private int programmaID;

    public Programma(int pId, String vTijd, String tTijd, String pText) {
		programmaID = pId;
		vanTijd = vTijd;
		programmatxt = pText;
	}
 
	public int getProgrammaID() {
	return programmaID;
    }

    public String getVanTijd() {
	return vanTijd;
    }

    public String getTotTijd() {
	return totTijd;
    }

    public String getProgrammatxt() {
	return programmatxt;
    }

    public void setProgrammaID(int programmaID) {
	this.programmaID = programmaID;
    }

    public void setVanTijd(String vanTijd) {
	this.vanTijd = vanTijd;
    }

    public void setTotTijd(String totTijd) {
	this.totTijd = totTijd;
    }

    public void setProgrammatxt(String programmatxt) {
	this.programmatxt = programmatxt;
    }

}

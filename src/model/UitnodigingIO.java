package model;

public class UitnodigingIO extends DbAbstract {

	public void genodigdenToevoegen(String id, String eventID) {
		
		System.out.println("updaten................................................................");
		String query2 = "INSERT INTO status (`contact`, `eventID`, `key`) VALUES('" + id + "','" + eventID + "', SHA2('"+id+ eventID+"', 256))";

		try {
			super.addUpdateRecord(query2);
		} catch (Exception ex) {
			System.out.println(ex + "het toevoegen van een genodigde is mislukt");
		}
		super.closeConnection();
	}
	
	public void genodigdenVerwijderen(String id, String eventID) {
		String query = "DELETE FROM status WHERE contact='"+ id +"' AND eventID='"+eventID+"' ";
		try {
			super.addUpdateRecord(query);

			super.closeConnection();

		} catch (Exception ex) {
			System.out.println(ex + "het verwijderen van de genodigde is mislukt");
		}
	}

}

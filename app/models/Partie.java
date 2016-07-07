package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
public class Partie extends Model {
	@Id
	public Long pId;
	@Required
	public String homeTeam;
	@Required
	public String guestTeam;
	@Required
	public Date playDate;
	@Required
	public String stadium;

	public static Finder<Long, Partie> find = new Finder<Long, Partie>(
			Partie.class);

	public static void create(Partie partie) {
		partie.save();
	}

	public static List<Partie> read() {
		return find.all();
	}

	public static void update(Partie updatedPartie) {
		updatedPartie.update();
	}

	public static void delete(Long pId) {
		find.ref(pId).delete();
	}

}
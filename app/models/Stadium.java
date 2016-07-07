package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
public class Stadium extends Model {
	@Id
	public Long sId;
	@Required
	public String name;
	@Required
	public String location;
	@Required
	public int seats;
	@Required
	public int entries;
	public String sponsor;
	public static Finder<Long, Stadium> find = new Finder<Long, Stadium>(
			Stadium.class);

	public static void create(Stadium partie) {
		partie.save();
	}

	public static List<Stadium> read() {
		return find.all();
	}

	public static void update(Stadium updatedPartie) {
		updatedPartie.update();
	}

	public static void delete(Long sId) {
		find.ref(sId).delete();
	}
}

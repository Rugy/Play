package controllers;

import javax.inject.Inject;

import models.Partie;
import models.Stadium;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class StadiumsCtrl extends Controller {

	@Inject
	private FormFactory formFactory;

	public Result createStadium() {
		return ok(views.html.stadiumsForm.render("Create",
				formFactory.form(Stadium.class), Partie.read()));
	}

	public Result readStadiums() {
		return ok(views.html.stadiums.render(Stadium.read()));
	}

	public Result updateStadium(Long sID) {
		Stadium stadium = Stadium.find.byId(sID);
		Form<Stadium> filledForm = formFactory.form(Stadium.class)
				.fill(stadium);
		return ok(views.html.stadiumsForm.render("Update", filledForm,
				Partie.read()));
	}

	public Result deleteStadium(Long sId) {
		String deletedStadium = "";
		for (Stadium stadium : Stadium.read()) {
			if (stadium.sId.equals(sId)) {
				deletedStadium = stadium.name;
			}
		}

		for (Partie partie : Partie.read()) {
			if (partie.stadium.equals(deletedStadium)) {
				Partie.delete(partie.pId);
			}
		}
		Stadium.delete(sId);
		return redirect(routes.IndexCtrl.getIndex());
	}

	public Result storeStadium() {
		Form<Stadium> stadiumForm = formFactory.form(Stadium.class);
		Form<Stadium> filledForm = stadiumForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return ok(views.html.stadiumsForm.render("Correct", filledForm,
					Partie.read()));
		} else {
			Stadium stadium = filledForm.get();
			if (stadium.sId == null)
				Stadium.create(stadium);
			else
				Stadium.update(stadium);
			return ok(views.html.stadiums.render(Stadium.read()));
		}
	}

}

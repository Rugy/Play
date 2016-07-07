package controllers;

import javax.inject.Inject;

import models.Partie;
import models.Stadium;

import org.apache.commons.lang3.time.DateUtils;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class PartienCtrl extends Controller {

	@Inject
	private FormFactory formFactory;

	public Result createPartie() {
		return ok(views.html.partienForm.render("Create",
				formFactory.form(Partie.class), Stadium.read()));
	}

	public Result readPartien() {
		return ok(views.html.partien.render(Partie.read()));
	}

	public Result updatePartie(Long pId) {
		Partie partie = Partie.find.byId(pId);
		Form<Partie> filledForm = formFactory.form(Partie.class).fill(partie);
		return ok(views.html.partienForm.render("Update", filledForm,
				Stadium.read()));
	}

	public Result deletePartie(Long pId) {
		Partie.delete(pId);
		return redirect(routes.IndexCtrl.getIndex());
	}

	public Result storePartie() {
		Form<Partie> partieForm = formFactory.form(Partie.class);
		Form<Partie> filledForm = partieForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return ok(views.html.partienForm.render(
					"Incorrect entry, try again: ", filledForm, Stadium.read()));
		} else {
			for (Partie partie : Partie.read()) {
				if (DateUtils.isSameDay(partie.playDate,
						filledForm.get().playDate)
						&& partie.stadium.equals(filledForm.get().stadium)) {
					return ok(views.html.partienForm.render(
							"Field occupied, try again: ", filledForm,
							Stadium.read()));
				}
			}
			Partie partie = filledForm.get();
			if (partie.pId == null) {
				Partie.create(partie);
			} else {
				Partie.update(partie);
			}
			return ok(views.html.partien.render(Partie.read()));
		}
	}
}
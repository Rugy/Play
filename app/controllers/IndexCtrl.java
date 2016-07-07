package controllers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.Partie;
import models.Stadium;
import play.mvc.Controller;
import play.mvc.Result;

public class IndexCtrl extends Controller {

	public Result getIndex() {
		List<Partie> partien = Partie.read();
		String dateDifference = "no games";

		if (partien.size() >= 2) {
			Collections.sort(partien,
					(p1, p2) -> p1.playDate.compareTo(p2.playDate));
		}
		if (partien.size() >= 1) {
			Long timeDifference = partien.get(0).playDate.getTime()
					- new Date().getTime();
			dateDifference = timeDifference / (24 * 60 * 60 * 1000) + " days, ";
			dateDifference += timeDifference / (60 * 60 * 1000) % 24
					+ " hours, ";
			dateDifference += timeDifference / (60 * 1000) % 60 + " minutes";
		}
		return ok(views.html.index.render(partien, Stadium.read(),
				dateDifference));
	}
}

package services;

import java.time.Clock;
import java.time.Instant;
import java.util.GregorianCalendar;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Partie;
import models.Stadium;
import play.Logger;
import play.inject.ApplicationLifecycle;

/**
 * This class demonstrates how to run code when the application starts and
 * stops. It starts a timer when the application starts. When the application
 * stops it prints out how long the application was running for.
 *
 * This class is registered for Guice dependency injection in the {@link Module}
 * class. We want the class to start when the application starts, so it is
 * registered as an "eager singleton". See the code in the {@link Module} class
 * to see how this happens.
 *
 * This class needs to run code when the server stops. It uses the application's
 * {@link ApplicationLifecycle} to register a stop hook.
 */
@Singleton
public class ApplicationTimer {

	private final Clock clock;
	private final ApplicationLifecycle appLifecycle;
	private final Instant start;

	@Inject
	public ApplicationTimer(Clock clock, ApplicationLifecycle appLifecycle) {
		this.clock = clock;
		this.appLifecycle = appLifecycle;
		// This code is called when the application starts.
		start = clock.instant();
		Logger.info("ApplicationTimer demo: Starting application at " + start);

		// Stadien:
		Stadium allianzArena = new Stadium();
		allianzArena.name = "Allianz Arena";
		allianzArena.location = "München";
		allianzArena.seats = 10000;
		allianzArena.entries = 50;
		allianzArena.sponsor = "Versicherung";
		Stadium.create(allianzArena);

		Stadium wembleyArena = new Stadium();
		wembleyArena.name = "Wembley Arena";
		wembleyArena.location = "London";
		wembleyArena.seats = 15000;
		wembleyArena.entries = 80;
		Stadium.create(wembleyArena);

		Stadium thirdArena = new Stadium();
		thirdArena.name = "Third Arena";
		thirdArena.location = "Hello World";
		thirdArena.seats = 25000;
		thirdArena.entries = 150;
		Stadium.create(thirdArena);

		// Matches
		Partie germanyEngland = new Partie();
		germanyEngland.homeTeam = "Germany";
		germanyEngland.guestTeam = "England";
		germanyEngland.playDate = new GregorianCalendar(2016, 07, 20).getTime();
		germanyEngland.stadium = allianzArena.name;
		Partie.create(germanyEngland);

		Partie germanyFrance = new Partie();
		germanyFrance.homeTeam = "Germany";
		germanyFrance.guestTeam = "France";
		germanyFrance.playDate = new GregorianCalendar(2016, 07, 25).getTime();
		germanyFrance.stadium = allianzArena.name;
		Partie.create(germanyFrance);

		Partie englandFrance = new Partie();
		englandFrance.homeTeam = "England";
		englandFrance.guestTeam = "France";
		englandFrance.playDate = new GregorianCalendar(2016, 07, 23).getTime();
		englandFrance.stadium = wembleyArena.name;
		Partie.create(englandFrance);

		// When the application starts, register a stop hook with the
		// ApplicationLifecycle object. The code inside the stop hook will
		// be run when the application stops.
		appLifecycle.addStopHook(() -> {
			Instant stop = clock.instant();
			Long runningTime = stop.getEpochSecond() - start.getEpochSecond();
			Logger.info("ApplicationTimer demo: Stopping application at "
					+ clock.instant() + " after " + runningTime + "s.");
			for (Stadium stadium : Stadium.read()) {
				Stadium.delete(stadium.sId);
				Logger.info("Deleting stadium: " + stadium.sId + ", name: "
						+ stadium.name);
			}
			for (Partie partie : Partie.read()) {
				Partie.delete(partie.pId);
				Logger.info("Deleting partie: " + partie.pId);
			}
			return CompletableFuture.completedFuture(null);
		});
	}
}

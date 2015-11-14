package kiblerdude.fpw.card;

import java.util.List;

import org.apache.log4j.Logger;

import io.dropwizard.lifecycle.Managed;

public class CardService implements Managed {

	private static final Logger LOG = Logger.getLogger(CardService.class);

	private final CardDAO cards;
	private final MatchDAO matches;
	
	public CardService(CardDAO cards, MatchDAO matches) {
		this.cards = cards;
		this.matches = matches;
	}
	
	@Override
	public void start() throws Exception {
		LOG.info("Start");
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stop");
	}
	
	public List<Card> getCards() {
		return cards.getCards(0, 5);
	}
	
	public List<Match> getMatches(String cardId) {
		return matches.getMatches(cardId);
	}
}

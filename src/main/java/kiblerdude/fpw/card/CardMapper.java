package kiblerdude.fpw.card;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class CardMapper implements ResultSetMapper<Card> {
	
	private static final Logger LOG = Logger.getLogger(CardMapper.class);
	
	public Card map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		String cardId = r.getString("id");
		String promotion = r.getString("promotion");
		String cardName = r.getString("name");
		String cardDate = r.getString("date");
		
		Card card = new Card(cardId, promotion, cardName, cardDate, null);
		
		LOG.info(card);
		
		return card;
	}
}

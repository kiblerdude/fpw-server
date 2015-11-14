package kiblerdude.fpw.card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.google.common.collect.Lists;

public class MatchMapper implements ResultSetMapper<Match> {
	
	public Match map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		List<String> matchParticipants = Lists.newArrayList();
		
		Long lastMatchId = 1L;
		Long currentMatchId = 1L;
		String lastWinner = null;
		
		// TODO get total matches and then get matches individually :(
		
		// Collect the details for the matches.
		while(r.next()) {
			String winner = r.getString("winner");
			String participant = r.getString("participant");
			currentMatchId = r.getLong("match_id");

			// A single match spans multiple rows from the result set.
			// All the participants need to be collected before the 
			// match is ready to be added to the list of matches.
			if (currentMatchId != lastMatchId) {
				//matches.add(new Match(matchParticipants, "", lastWinner));
				// prepare to gather participants for the next match.
				lastMatchId = currentMatchId;
				matchParticipants.clear();
			}
			
			matchParticipants.add(participant);
			lastWinner = winner;
		}
		return null;
		//return matches;
	}
}

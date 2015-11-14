package kiblerdude.fpw.card;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(MatchMapper.class)
public interface MatchDAO {
	
	@SqlQuery(
	"select p.match_id, p.participant, m.winner " +
	"from card_match_participants p " +
	"left join card_matches m on p.card_id = m.card_id and p.match_id = m.match_id " +
	"where p.card_id= :cardId " +
	"order by match_id")
	List<Match> getMatches(@Bind("cardId") String cardId);
	
}

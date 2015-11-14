package kiblerdude.fpw.card;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(CardMapper.class)
public interface CardDAO {
	
	@SqlQuery(
	"select id, promotion, name, date " +
	"from cards " +
	"order by date desc " +
	"limit :page,:size")
	List<Card> getCards(@Bind("page") int page, @Bind("size") int size);	
}

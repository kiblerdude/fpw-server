package kiblerdude.fpw.card;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

/**
 * APIs for accounts.
 * 
 * @author kiblerj
 *
 */
@Path("/v1/cards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CardResource {

	private static final Logger LOG = Logger.getLogger(CardResource.class);

	private final CardService cardSvc;

	public CardResource(CardService cardSvc) {
		this.cardSvc = cardSvc;
	}

	@GET
	public Response getCards(@QueryParam("page") Integer page, @QueryParam("size") Integer size) {
		try {
			List<Card> response = cardSvc.getCards();
			return Response.ok(response).build();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			if (e.getCause() != null) {
				LOG.error(e.getCause().getMessage());
			}
		}
		return Response.serverError().build();
	}
	
	@GET
	@Path("/{cardId}/matches")
	public Response getCardMatches(@PathParam("cardId") String cardId) {
		try {
			List<Match> matches = cardSvc.getMatches(cardId);
			return Response.ok(matches).build();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			if (e.getCause() != null) {
				LOG.error(e.getCause().getMessage());
			}			
		}
		return Response.serverError().build();
	}
}

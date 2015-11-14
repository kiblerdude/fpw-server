package kiblerdude.fpw.news;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.common.base.Optional;

@Path("/v1/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsResource {
	
	private static final Logger LOG = Logger.getLogger(NewsResource.class);

	private final NewsService news;
	
	public NewsResource(NewsService news) {
		this.news = news;
	}
	
	@GET
	public Response get(
	        @QueryParam("p") Optional<Integer> page,
	        @QueryParam("s") Optional<Integer> size) {
		try {
			int p = page.isPresent() ? page.get() : 1;
			int s = size.isPresent() ? size.get() : 50;
			List<News> stories = news.getNews(p, s);
			BulkNews bulk = new BulkNews(stories);
			return Response.ok(bulk).build();
		} catch (Exception e) {
			LOG.error(e.getMessage());			
		}
		return Response.serverError().build();
	}
}

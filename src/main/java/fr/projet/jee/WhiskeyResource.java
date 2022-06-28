package fr.projet.jee;

import fr.projet.jee.Bean.AuthBean;
import fr.projet.jee.Bean.WhiskeyBean;
import fr.projet.jee.Objets.Whiskey;

import java.util.logging.Level;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("whiskey")
public class WhiskeyResource {
    
	@Inject
    private WhiskeyBean whiskeyBean;
	@Inject
    private AuthBean authBean;

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SampleResource.class.getName());

    @GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getWhiskey() {
        var ls = whiskeyBean.getWhiskeys();
        return Response.ok(ls).build();
	}
    
    @GET
	@Path("/search/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getWhiskeys(@HeaderParam("Authorizations") String bearerAuth, @PathParam("id") Long id) {
        if(bearerAuth == null)
            return Response.status(404).build();

        var token_val = bearerAuth.split(" ")[1];
        var token = authBean.getToken(token_val);
        if(token == null)
            return Response.status(414).build();

        var res = whiskeyBean.getWhiskey(id);
        return Response.ok(res).build();
	}
    
    @GET
	@Path("/search-noauth/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getNoAuthWhiskeys(@PathParam("id") Long id) {
        if(id == null)
            return Response.status(404).build();

        var res = whiskeyBean.getWhiskey(id);
        return Response.ok(res).build();
	}

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add/")
    public Response addWhiskey(Whiskey _whiskey) {
        logger.log(Level.INFO, _whiskey.toString());
        if (_whiskey.getName() == null) {
            return Response.status(403, "Whiskey").build();
        }
        
        return Response.ok(whiskeyBean.add(_whiskey)).build();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateWhiskey(@PathParam("id") Long id, Whiskey _whiskey){
        if (_whiskey.getName() == null || id == null || id <= 0) {
            return Response.status(403).build();
        }
        var res = whiskeyBean.update(id, _whiskey);
        return Response.ok(res).build();
    }
	
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/delete/{id}")
    public Response deleteWhiskey(@PathParam("id") Long id){
        if (id == null || id < 0) {
            return Response.status(403).build();
        }

        var res = whiskeyBean.delete(id);
        return Response.ok(res).build();
    }
    /*cours*/
    /*MGazm@YdV-R7yA!2*/
}
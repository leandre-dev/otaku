package fr.projet.jee;

import fr.projet.jee.Bean.AuthBean;
import fr.projet.jee.Objets.User;

import java.util.logging.Level;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
public class AuthResource {
    
	@Inject
    private AuthBean authBean;

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SampleResource.class.getName());

    @GET
	@Path("/users")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUsers() {
        return Response.ok(authBean.getUsers()).build();
	}

    @GET
	@Path("/users/id/{uid}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUserById(@PathParam("uid") Long uid) {
        return Response.ok(authBean.getUser(uid)).build();
	}

    @GET
	@Path("/user/{uid}/tokens")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUserTokens(@PathParam("uid") Long uid) {
        return Response.ok(authBean.getUserTokens(uid)).build();
	}
    @GET
	@Path("/tokens")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getTokens() {
        return Response.ok(authBean.getTokens()).build();
	}


    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/user/add")
    public Response addUser(User _user) {
        logger.log(Level.SEVERE, _user.toString());
        if (_user.getName() == null) {
            return Response.status(403, "User").build();
        }
        
        var res = authBean.addUser(_user);
        logger.log(Level.SEVERE, res.isValue1() + " -* " + res.isValue2());
        return Response.ok(res).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/user/login")
    public Response login(@HeaderParam("Authorizations") String bearerAuth, User _user) {
        var _token = "";
        if(bearerAuth != null && bearerAuth.contains(" "))
            _token = bearerAuth.split(" ")[1];

        return Response.ok(authBean.login(_user, _token)).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/user/logout")
    public Response logout(@HeaderParam("Authorizations") String bearerAuth) {
        if(bearerAuth == null)
            return Response.status(404).build();

        var token_val = bearerAuth.split(" ")[1];
        var token = authBean.getToken(token_val);
        if(token == null)
            return Response.status(414).build();
            
        return Response.ok(authBean.logout(token_val)).build();
    }
}
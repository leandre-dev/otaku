package fr.projet.jee;

import fr.projet.jee.Bean.AuthBean;
import fr.projet.jee.Bean.BrandBean;
import fr.projet.jee.Objets.Brand;

import java.util.logging.Level;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

//import org.eclipse.microprofile.config.inject.ConfigProperty;

//OK
@Path("brand")
public class BrandResource {
    
	@Inject
    private BrandBean brandBean;
	@Inject
    private AuthBean authBean;

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SampleResource.class.getName());

    @GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBrands(@Context HttpServletRequest req, @PathParam("id") Long id){
        if(req.getHeader(AUTHORIZATION) == null)
            return Response.status(401).build();

        var token_val = req.getHeader(AUTHORIZATION).substring("Bearer".length()).trim();
        if(token_val == null)
            return Response.status(403).build();

        var token = authBean.getToken(token_val);
        if(token == null)
            return Response.status(405).build();

        return Response.ok(brandBean.getBrands()).build();
	}

    @GET
	@Path("/token/{val}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getTokenByName(@PathParam("val") String val){
        return Response.ok(authBean.getToken(val)).build();
	}

    @GET
	@Path("/search/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBrand(@PathParam("id") Long id) {
        return Response.ok(brandBean.getBrand(id)).build();
	}

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add/")
    public Response addBrand(Brand _brand) {
        logger.log(Level.INFO, _brand.toString());
        if (_brand.getName() == null) {
            return Response.status(403, "Brand").build();
        }
        
        return Response.ok(brandBean.add(_brand)).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/multi-add/")
    public Response addBrands(Brand[] _brands) {
        logger.log(Level.INFO, _brands.toString());
        if (_brands == null || _brands[0].getName() == null) {
            return Response.status(403, "Brands").build();
        }

        var res = true;
        for(int i = 0; i < _brands.length; i++)
            res = res && brandBean.add(_brands[i]);
        
        return Response.ok(res).build();
    }

    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateBrand(@PathParam("id") Long id, Brand _brand){
        if (_brand.getName() == null || id == null || id <= 0) {
            return Response.status(404).build();
        }
        var res = brandBean.update(id, _brand);
        return Response.ok(res).build();
    }
	
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/delete/{id}")
    public Response deleteBrand(@PathParam("id") Long id){
        if (id == null || id < 0)
            return Response.status(404).build();

        var res = brandBean.delete(id);
        return Response.ok(res).build();
    }
}

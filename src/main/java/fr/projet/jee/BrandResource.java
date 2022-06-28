package fr.projet.jee;

import fr.projet.jee.Bean.BrandBean;
import fr.projet.jee.Objets.Brand;

import java.util.logging.Level;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import org.eclipse.microprofile.config.inject.ConfigProperty;

//OK
@Path("brand")
public class BrandResource {
    
	@Inject
    private BrandBean brandBean;

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SampleResource.class.getName());

    @GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBrands() {
        return Response.ok(brandBean.getBrands()).build();
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
}
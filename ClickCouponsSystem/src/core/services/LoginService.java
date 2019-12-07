package core.services;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.sys.coupon.login.ClientFacade;
import com.sys.coupon.login.ClientType;
import com.sys.coupon.login.LoginManager;
import com.sys.exceptions.CouponSystemException;

@Path("/login-manager")
public class LoginService {

	private LoginManager loginManager = LoginManager.getInstance();
	// AdminFacade adminFacade;
	private HttpSession session;

	@Context
	HttpServletRequest req;

	@PostConstruct
	private void getSession() {
		this.session = req.getSession(false);
		// adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin",
		// ClientType.Administrator);
	}

	/*
	 * @Path("/login")
	 * 
	 * @GET
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * loginAdmin(@QueryParam("clientFacade") String facade) { AdminFacade
	 * adminFacade = null; try { if (req.getSession(false) == null) { adminFacade =
	 * (AdminFacade) loginManager.login("admin@admin.com", "admin",
	 * ClientType.Administrator); this.session = req.getSession(true);
	 * this.session.setAttribute("adminFacade", adminFacade); return
	 * Response.status(Response.Status.OK).entity("admin facade created").build(); }
	 * else { req.getSession().invalidate(); return
	 * Response.status(Response.Status.OK).entity("The session was invalideted").
	 * build(); } //} } catch (CouponSystemException e) { e.printStackTrace();
	 * return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
	 * entity("no adminFacade").build(); } }
	 */

	@Path("/login")
	@GET
	public Response loginAdmin(@QueryParam("email") String email, @QueryParam("password") String password,
			@QueryParam("clientType") String clientType) throws CouponSystemException {

		ClientFacade clientFacade = null;

		try {
			if (req.getSession(false) == null) {
				System.out.println(ClientType.valueOf(clientType));
				clientFacade = loginManager.login(email, password, ClientType.valueOf(clientType));
				this.session = req.getSession(true);
				this.session.setAttribute("facade", clientFacade);
				return Response.status(Response.Status.OK).entity("Hello " + clientType + ", you are logged in")
						.build();
			} else {
				req.getSession().invalidate();
				return Response.status(Response.Status.OK).entity("The session was invalideted").build();
			}

		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("login failed").build();
		}
	}

}



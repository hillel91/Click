		package core.services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sys.beans.Coupon;
import com.sys.beans.Customer;
import com.sys.categories.Categories;
import com.sys.coupon.login.LoginManager;
import com.sys.exceptions.CouponSystemException;
import com.sys.facade.CustomerFacade;

@Path("/sec/customerService")
public class CustomerWebService {

	LoginManager loginManager = LoginManager.getInstance();
	CustomerFacade customerFacade;
	HttpSession session;

	@Context
	HttpServletRequest req;

	@PostConstruct
	public void getSession() {
		session = req.getSession(false);
	}

	@Path("/purchase-coupon")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response purchaseCoupon(Coupon coupon,@QueryParam("customerId") int customerId) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			customerFacade = (CustomerFacade) session.getAttribute("facade");
			customerFacade.purchaseCoupon(coupon, customerId);
			return Response.status(Response.Status.OK).entity(coupon).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to purchase coupon " + coupon)
				.build();
	}

	@Path("/get-customer-coupons")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerCoupons(@QueryParam("id") int customerId) throws CouponSystemException {
		ArrayList<Coupon> couponsList = null;
		if (session != null) {
			// Get the facade as session attribute
			customerFacade = (CustomerFacade) session.getAttribute("facade");
			couponsList = customerFacade.getCustomerCoupon(customerId);
			return Response.status(Response.Status.OK).entity(couponsList).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(couponsList).build();
	}

	@Path("/get-customer-coupons-category")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerCoupons(@QueryParam("customerId") int customerId, @QueryParam("category") String category) throws CouponSystemException {
		ArrayList<Coupon> couponsList = null;
		Categories catego = Categories.valueOf(category);
		if (session != null) {
			// Get the facade as session attribute
			customerFacade = (CustomerFacade) session.getAttribute("facade");
			couponsList = customerFacade.getCustomerCoupon(customerId, catego);
			return Response.status(Response.Status.OK).entity(couponsList).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(couponsList).build();
	}

	@Path("/get-customer-coupons-maxPrice")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerCoupons(@QueryParam("id") int customerId, @QueryParam("maxPrice") double maxPrice) throws CouponSystemException {
		ArrayList<Coupon> couponsList = null;
		if (session != null) {
			customerFacade = (CustomerFacade) session.getAttribute("facade");
			couponsList = customerFacade.getCustomerCoupon(customerId, maxPrice);
			return Response.status(Response.Status.OK).entity(couponsList).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(couponsList).build();
	}

	@Path("/get-customer-details")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerDetails(@QueryParam("id") int customerId) throws CouponSystemException {
		Customer customer = null;
		if (session != null) {
			// Get the facade as session attribute
			customerFacade = (CustomerFacade) session.getAttribute("facade");
			customer = customerFacade.getCustomerDetails(customerId);
			return Response.status(Response.Status.OK).entity(customer).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(customer).build();
	}

	@Path("/update-customer")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			customerFacade = (CustomerFacade) session.getAttribute("facade");
			customerFacade.updateCustomer(customer);
			return Response.status(Response.Status.OK).entity(customer).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(customer).build();
	}

}

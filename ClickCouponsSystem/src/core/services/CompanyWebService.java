package core.services;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sys.beans.Company;
import com.sys.beans.Coupon;
import com.sys.categories.Categories;
import com.sys.coupon.login.LoginManager;
import com.sys.exceptions.CouponSystemException;
import com.sys.facade.CompanyFacad;

@Path("/sec/companyService")
public class CompanyWebService {

	LoginManager loginManager = LoginManager.getInstance();
	CompanyFacad companyFacade;
	HttpSession session;
	
	@Context
	HttpServletRequest req;

	@PostConstruct
	public void getSession() {
		session = req.getSession(false);
	}
	
	@Path("/create-coupon")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCoupon(Coupon coupon) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			System.out.println(coupon);
			companyFacade.addCoupon(coupon);
			return Response.status(Response.Status.OK).entity(coupon).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add coupon: " + coupon).build();
	}

	@Path("/create-company")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCompany(Company company/*, @QueryParam("login") String login*/) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			companyFacade.addCompany(company);
			return Response.status(Response.Status.OK).entity(company).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(company).build();
	}
	
	@Path("/delete-company")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCompany(Company company) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			companyFacade.deleteCompany(company);
			return Response.status(Response.Status.OK).entity(company).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(company).build();
	}
	
	@Path("/update-coupon")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCoupon(Coupon coupon) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			companyFacade.updateCoupon(coupon);
			return Response.status(Response.Status.OK).entity(coupon).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(coupon).build();
	}
	
	@Path("/delete-coupon")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCoupon(@QueryParam("id") int couponId) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			companyFacade.deleteCoupon(couponId);
			return Response.status(Response.Status.OK).entity(couponId).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(couponId).build();
	}
	
	@Path("/get_coupons")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanyCoupons(@QueryParam("id") int companyId) throws CouponSystemException {
		ArrayList<Coupon> CompanyCoupons = null;
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			CompanyCoupons = companyFacade.getCompanyCoupons(companyId);
			return Response.status(Response.Status.OK).entity(CompanyCoupons).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(CompanyCoupons).build();
	}
	
	@Path("/get_coupons_of_category")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanyCouponsOfCategory(@QueryParam("id") int companyId, @QueryParam("category") String category) throws CouponSystemException {
		Categories c = Categories.valueOf(category);
		ArrayList<Coupon> CompanyCoupons = null;
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			CompanyCoupons = companyFacade.getCompanyCoupons(companyId, c);
			return Response.status(Response.Status.OK).entity(CompanyCoupons).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(CompanyCoupons).build();
	}
	
	@Path("/get_coupons_max_price")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanyCouponsMaxPrice(@QueryParam("id") int companyId, @QueryParam("price") double price) throws CouponSystemException {
		ArrayList<Coupon> CompanyCoupons = null;
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			CompanyCoupons = companyFacade.getCompanyCoupons(companyId, price);
			return Response.status(Response.Status.OK).entity(CompanyCoupons).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(CompanyCoupons).build();
	}
	
	@Path("/get_company_details")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanyDetails(@QueryParam("id") int companyId) throws CouponSystemException {
		Company company = null;
		if (session != null) {
			// Get the facade as session attribute
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			company = companyFacade.getCompanyDetails(companyId);
			return Response.status(Response.Status.OK).entity(company).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(company).build();
	}
	
	@Path("/update_company")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCompany(Company company) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			System.out.println("===>" + session.getAttribute("facade"));
			companyFacade = (CompanyFacad) session.getAttribute("facade");
			companyFacade.updateCompany(company);
			return Response.status(Response.Status.OK).entity(company).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(company).build();
	}
	
}

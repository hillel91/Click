package core.services;

import java.util.ArrayList;
import java.util.List;

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
import com.sys.beans.Customer;
import com.sys.coupon.login.ClientType;
import com.sys.coupon.login.LoginManager;
import com.sys.exceptions.CouponSystemException;
import com.sys.facade.AdminFacade;

@Path("/sec/adminService")
public class adminWebService {

	LoginManager loginManager = LoginManager.getInstance();
	AdminFacade adminFacade;
	HttpSession session;

	@Context
	HttpServletRequest req;

	@PostConstruct
	public void getSession() {
		session = req.getSession(false);
		if(session!=null) {
			this.adminFacade=(AdminFacade) session.getAttribute("adminFacade");
		}
	}

	@Path("/create-company")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCompany(Company company) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			adminFacade.addCompany(company);
			return Response.status(Response.Status.OK).entity(company).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(company).build();
	}

	@Path("/update-company")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCompany(Company company) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			adminFacade.updateCompany(company);
			return Response.status(Response.Status.OK).entity(company).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(company).build();
	}

	@Path("/delete-company")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)  //-- unnescesary to convert to JSON in the body, because "@QueryParam(...)" is sent in the header
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCompany(Company company) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			adminFacade.deleteCompany(company.getId());
			return Response.status(Response.Status.OK).entity(company).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(company).build();
	}

	@Path("/get-All-Companies")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCompanies() throws CouponSystemException {
		
		if (session != null) {
			
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			  ArrayList<Company> companiesList = adminFacade.getAllCompanies();
			return Response.status(Response.Status.OK).entity(companiesList).build();
		}
		//return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to get all the companies").build();
		return Response.status(Response.Status.OK).entity("session is null!").build();
	//return null;
	
	}

	@Path("/get-Company")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)  -- unnescesary to convert to JSON in the body, because "@QueryParam(...)" is sent in the header
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneCompany(@QueryParam("id") int companyId) throws CouponSystemException {
		Company company= null;
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			company = adminFacade.getOneCompany(companyId);
			return Response.status(Response.Status.OK).entity(company).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(company).build();
	}

	@Path("/add-customer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(Customer customer) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			adminFacade.addCustomer(customer);
			return Response.status(Response.Status.OK).entity(customer).build();
		}
//		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed adding customer: " + customer).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(customer).build();
	}
	
	@Path("/update-customer")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			adminFacade.updateCustomer(customer);
			return Response.status(Response.Status.OK).entity(customer).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed updating customer: " + customer).build();
	}
	
	@Path("/delete-customer")
	@DELETE
	//@Consumes(MediaType.APPLICATION_JSON) -- unnescesary to convert to JSON in the body, because "@QueryParam(...)" is sent in the header
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@QueryParam("id") int customerId) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			adminFacade.deleteCustomer(customerId);
			return Response.status(Response.Status.OK).entity("id of custumer deleted: " + customerId).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed delete customer with id: " + customerId).build();
	}
	
	@Path("/get-all-customers")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCustomers() throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			ArrayList<Customer> customerList = adminFacade.getAllCustomers();
			return Response.status(Response.Status.OK).entity(customerList).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("failed getting the customer list").build();
	}
	
	@Path("/get-one-customer")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON) -- unnescesary to convert to JSON in the body, because "@QueryParam(...)" is sent in the header
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneCustomer(@QueryParam("id") int customerId) throws CouponSystemException {
		if (session != null) {
			// Get the facade as session attribute
			adminFacade = (AdminFacade) session.getAttribute("facade");
			Customer customer = adminFacade.getOneCustomer(customerId);
			return Response.status(Response.Status.OK).entity(customer).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("failed getting the customer: " + adminFacade.getOneCustomer(customerId)).build();
	}
	
}

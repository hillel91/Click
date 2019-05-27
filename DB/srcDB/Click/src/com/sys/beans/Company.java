package Click.src.com.sys.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String email;
	private String password;
	private ArrayList<Coupon> coupensList;
	
	public Company() {
	}

	public Company(int id) {
		super();
		this.id = id;
	}

	public Company(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Company(int id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Company(int id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	

	public Company(int id, String name, String email, String password, ArrayList<Coupon> coupensList) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupensList = coupensList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Coupon> getCoupensList() {
		return coupensList;
	}

	public void setCoupensList(ArrayList<Coupon> coupensList) {
		this.coupensList = coupensList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CompaniesJavaBeans [id=" + id + ", name=" + name + 
				", email=" + email + ", password=" + password
				+ ", coupensList=" + coupensList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}

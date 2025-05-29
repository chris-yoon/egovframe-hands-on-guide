package lab;

import org.springframework.stereotype.Repository;

@Repository("customerdao")
public class CustomerAnnotationDAO implements CustomerService {

	public String getCustomerName(String id) {
		return id + " eGovFrame Annotation";
	}

	public String getCustomerGrade(String id) {
		return id + " S Annotation";
	}
}

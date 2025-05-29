package lab;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("customer")
public class CustomerAnnotationServiceImpl implements CustomerService {

	@Resource(name = "customerdao")
	private CustomerAnnotationDAO customerdao;

	public String getCustomerName(String id) {
		return customerdao.getCustomerName(id);
	}

	public String getCustomerGrade(String id) {
		return customerdao.getCustomerGrade(id);
	}
}
package lab;

public class CustomerXMLServiceImpl implements CustomerService {

	private CustomerXMLDAO xmlDAO;

	public void setCustomerXMLDAO(CustomerXMLDAO cxmlDAO) {
		this.xmlDAO = cxmlDAO;
	}

	public String getCustomerName(String id) {
		return xmlDAO.getCustomerName(id);
	}

	public String getCustomerGrade(String id) {
		return xmlDAO.getCustomerGrade(id);
	}
}

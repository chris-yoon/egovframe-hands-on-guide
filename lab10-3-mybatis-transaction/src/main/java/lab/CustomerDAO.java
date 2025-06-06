package lab;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("customerDAO")
public class CustomerDAO extends EgovAbstractMapper {

  public void insertCustomer(CustomerVO vo) {
    insert("customer.insertCustomer", vo);
  }

  public int deleteCustomer(CustomerVO vo) {
    return delete("customer.deleteCustomer", vo);
  }

  public int updateCustomer(CustomerVO vo) {
    return update("customer.updateCustomer", vo);
  }

  public List<CustomerVO> selectCustomerList(CustomerVO vo) {
    return selectList("customer.selectCustomerList", vo);
  }

}

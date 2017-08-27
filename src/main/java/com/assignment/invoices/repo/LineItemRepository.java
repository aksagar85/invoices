package com.assignment.invoices.repo;




import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.assignment.invoices.domain.LineItem;


public interface LineItemRepository extends CrudRepository<LineItem, Integer>{

	List<LineItem> findByInvoiceInvoiceId(Integer invoiceId);

}

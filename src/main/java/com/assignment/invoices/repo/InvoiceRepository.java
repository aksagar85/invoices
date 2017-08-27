package com.assignment.invoices.repo;

import org.springframework.data.repository.CrudRepository;

import com.assignment.invoices.domain.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer>{
	

}

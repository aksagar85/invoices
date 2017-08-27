package com.assignment.invoices.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.invoices.domain.Invoice;
import com.assignment.invoices.repo.InvoiceRepository;

@Service
public class InvoiceService {
	private InvoiceRepository invoiceRepository;
	@Autowired
	public InvoiceService(InvoiceRepository invoiceRepository){
		this.invoiceRepository = invoiceRepository;
	}

	public Invoice createInvoice(Integer invoiceId, String name, String email, Date dueDate)
	{
		System.out.println("Hello Amit6");
		if(!invoiceRepository.exists(invoiceId))
		{
			invoiceRepository.save(new Invoice(name, email, dueDate));
		}
		return null;
	}
	
	public Iterable<Invoice> lookup()
	{
	return invoiceRepository.findAll();
	}
	public long total()
	{
		return invoiceRepository.count();
	}
}

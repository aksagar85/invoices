package com.assignment.invoices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.invoices.domain.Invoice;
import com.assignment.invoices.domain.LineItem;
import com.assignment.invoices.repo.InvoiceRepository;
import com.assignment.invoices.repo.LineItemRepository;

@Service
public class LineItemService {
	private InvoiceRepository invoiceRepository;
	private LineItemRepository lineItemRepository;
	
	@Autowired
	public LineItemService(InvoiceRepository invoiceRepository, LineItemRepository lineItemRepository) {
		this.invoiceRepository = invoiceRepository;
		this.lineItemRepository = lineItemRepository;
	}
	
	public LineItem createLineItem(String description, Integer price, Integer invoiceId)
	{
		System.out.println("Hello Amit2");
		Invoice invoice = invoiceRepository.findOne(invoiceId);
		System.out.println("My Sysout" + invoiceId);
		if(invoice == null)
		{
			throw new RuntimeException("Invoice Does Not Exist" + invoiceId);
		}
		
		return lineItemRepository.save(new LineItem(description, price, invoice));
	}
	
	public Iterable<LineItem> lookup()
	{
		return lineItemRepository.findAll();
	}
	
	public long total()
	{
		return lineItemRepository.count();
	}

}

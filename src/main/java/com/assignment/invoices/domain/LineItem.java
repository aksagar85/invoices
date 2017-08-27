package com.assignment.invoices.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Line Item contains Description and Amount.
 *
 * Created by Amit Kshirsagar
 */

@Entity
public class LineItem implements Serializable{
	
	@Id
    @GeneratedValue
    private Integer id;


    @Column(length = 200)
    private String description;
    
    @Column
    private Integer price;


    @ManyToOne
    @JoinColumn(name = "invoiceId")
    private Invoice invoice;
    
    public LineItem(String description, Integer price, Invoice invoice) {
	    this.description = description;
	    this.price = price;
	    this.invoice = invoice;
    }

	protected LineItem() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	

}

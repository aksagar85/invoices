package com.assignment.invoices.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The Invoice contains name, email and dueDate.
 *
 * Created by Amit Kshirsagar
 */

@Entity
public class Invoice implements Serializable{

	@Id
    @GeneratedValue
    private Integer invoiceId;
	
    @Column(length = 200)
    private String name;
    
    @Column(length = 50)
    private String email;

    @Column
    private Date dueDate;
    
    
    protected Invoice() {
    }

    public Invoice(String name, String email, Date dueDate) {
        this.name = name;
        this.email = email;
        this.dueDate = dueDate;
    }

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
    
}

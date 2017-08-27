package com.assignment.invoices.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object for Line Items.
 *
 * Created by Amit Kshirsagar
 */
public class LineItemDto {

    @Min(0)
    @Max(99999)
    private Integer price;

    @Size(max = 200)
    private String description;

   
    /**
     * Constructor to fully initialize the LineItemDto
     *
     * @param price
     * @param description

     */
    public LineItemDto(String description, Integer price) {
        this.description = description;
        this.price = price;
    }

    protected LineItemDto() {}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    

}

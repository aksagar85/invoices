package com.assignment.invoices.web;

import java.util.AbstractMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import com.assignment.invoices.domain.Invoice;
import com.assignment.invoices.domain.LineItem;
import com.assignment.invoices.repo.InvoiceRepository;
import com.assignment.invoices.repo.LineItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path = "/invoices/{invoiceId}/lineItems")
public class LineItemController {
    LineItemRepository lineItemRepository;
    InvoiceRepository invoiceRepository;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    public LineItemController(LineItemRepository lineItemRepository, InvoiceRepository invoiceRepository)
    {
        this.lineItemRepository = lineItemRepository;
        this.invoiceRepository = invoiceRepository;
    }
    
    protected LineItemController()
    {
        
    }
    
    /**
     * Create a LineItem.
     *
     * @param invoiceId
     * @param description
     * @param price
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createLineItem(@PathVariable(value = "invoiceId") int invoiceId, @RequestBody @Validated LineItemDto lineItemDto) {
        Invoice invoice = verifyInvoice(invoiceId);
        if(invoice == null)
        {
            logger.debug("Invoice " + invoiceId +" does not exist");
            throw new RuntimeException("Invoice Does Not Exist" + invoiceId);
        }
        lineItemRepository.save(new LineItem(lineItemDto.getDescription(), lineItemDto.getPrice(), invoice ));
    }

    /**
     * Lookup a the LineItems for a Invoice.
     *
     * @param invoiceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<LineItemDto> getAllLineItemsForInvoice(@PathVariable(value = "invoiceId") int invoiceId) {
        Invoice invoice = verifyInvoice(invoiceId);
        return lineItemRepository.findByInvoiceInvoiceId(invoiceId).stream().map(lineItem -> toDto(lineItem))
                .collect(Collectors.toList());
    }

    /**
     * Convert the LineItem entity to a LineItemDto
     *
     * @param LineItem
     * @return LineItemDto
     */
    private LineItemDto toDto(LineItem lineItem) {
        return new LineItemDto(lineItem.getDescription(), lineItem.getPrice());
    }
    
    /**
     * Calculate the totalAmount of a Invoice.
     *
     * @param tourId
     * @return Tuple of "average" and the average value.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/total")
    public AbstractMap.SimpleEntry<String, Double> getTotal(@PathVariable(value = "invoiceId") int invoiceId) {
        Invoice invoice = verifyInvoice(invoiceId);
        List<LineItem> lineItems = lineItemRepository.findByInvoiceInvoiceId(invoiceId);
        if(lineItems == null || lineItems.isEmpty())
        {
            logger.info("Total is zero for invoice " + invoiceId );
            double result = 0;
            return new AbstractMap.SimpleEntry<String, Double>("total", result);
        }
        double total = lineItems.stream().mapToInt(LineItem::getPrice).sum();
        double result = total!=0 ? total:null;
        return new AbstractMap.SimpleEntry<String, Double>("total", result);
    }
    
    /**
     * Update description and price of a LineItem
     *
     * @param Id
     * @param lineItemDto
     * @return The modified LineItem DTO.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/{Id}" )
    public LineItemDto updateWithPut(@PathVariable(value = "Id") int Id, @RequestBody @Validated LineItemDto lineItemDto) {
        LineItem lineItem = verifyLineItem(Id);
        lineItem.setDescription(lineItemDto.getDescription());
        lineItem.setPrice(lineItemDto.getPrice());
        return toDto(lineItemRepository.save(lineItem));
    }
    /**
     * Update description or price of a LineItem
     *
     * @param Id
     * @param lineItemDto
     * @return The modified LineItem DTO.
     */
    @RequestMapping(method = RequestMethod.PATCH, path = "/{Id}")
    public LineItemDto updateWithPatch(@PathVariable(value = "Id") int Id, @RequestBody @Validated LineItemDto lineItemDto) {
        LineItem lineItem = verifyLineItem(Id);
        if (lineItemDto.getDescription() != null) {
            lineItem.setDescription(lineItemDto.getDescription());
            logger.info("Description updated for Line Item" + Id );
        }
        if (lineItemDto.getPrice() != null) {
            lineItem.setPrice(lineItemDto.getPrice());
            logger.info("Price updated for Line Item" + Id );
        }
        return toDto(lineItemRepository.save(lineItem));
    }
    
    /**
     * Delete a LineItem of a Invoice 
     *
     * @param Id
     * @param customerId
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/{Id}")
    public void delete(@PathVariable(value = "invoiceId") int invoiceId, @PathVariable(value = "Id") int Id) {
        LineItem lineItem = verifyLineItem(Id);
        lineItemRepository.delete(lineItem);
        logger.info("Line Item " + Id + " deleted");
    }

    
    /**
     * Verify and return the LineItem for a Id 
     * @param Id
     * @return the found LineItem
     * @throws NoSuchElementException if no LineItem found
     */
    private LineItem verifyLineItem(int Id) throws NoSuchElementException {
        LineItem lineItem = lineItemRepository.findOne(Id);
        if (lineItem == null) {
            throw new NoSuchElementException("LineItem for request("
                    + Id);
        }
        return lineItem;
    }
    /**
     * Verify and return the Invoice given a invoiceId.
     *
     * @param invoiceId
     * @return the found Invoice
     * @throws NoSuchElementException if no Invoice found.
     */
    private Invoice verifyInvoice(int invoiceId) throws NoSuchElementException {
        Invoice invoice = invoiceRepository.findOne(invoiceId);
        if (invoice == null) {
            logger.error("invoice " + invoiceId + " not present");
            throw new NoSuchElementException("Invoice does not exist " + invoiceId);
        }
        return invoice;
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

}

package za.co.digitalplatoon.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import za.co.digitalplatoon.entity.Invoice;
import za.co.digitalplatoon.exception.InvoiceNotFoundException;
import za.co.digitalplatoon.repository.InvoiceRepository;

@RestController
public class InvoiceController {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	//Viewing All invoices
	@RequestMapping(value = "/invoices", method = RequestMethod.GET)
	public List<Invoice> viewAllInvoices() {
		
		return invoiceRepository.findAll();
	}
	
	
	//view Invoice by Id
	@RequestMapping(value="/invoices/{id}", method = RequestMethod.GET)
	public Invoice viewInvoice(@PathVariable("id") Long id) {
		
		Optional<Invoice> invoice = invoiceRepository.findById(id);
		
		if(!invoice.isPresent()) {
			
			throw new InvoiceNotFoundException("id -" + id);
		}
		
		return invoice.get();
	}
	
	//Save an Invoice
	@RequestMapping(value="/invoices", method = RequestMethod.POST)
	public ResponseEntity<Void> addInvoice(@RequestBody Invoice invoice, UriComponentsBuilder ucBuilder) {
		

		
		Invoice savedInvoice = invoiceRepository.save(invoice);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/invoice/{id}").buildAndExpand(savedInvoice.getId()).toUri();

		return ResponseEntity.created(location).build();

	
	}
}

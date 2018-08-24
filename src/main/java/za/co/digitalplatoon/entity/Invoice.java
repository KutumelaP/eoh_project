package za.co.digitalplatoon.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="invoice", schema="eoh_digital")
@JsonIdentityInfo(
		generator=ObjectIdGenerators.PropertyGenerator.class,
		property="id")
public class Invoice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	private String client;
	private Long vatRate;
	private Date invoiceDate;
	@OneToMany(mappedBy = "invoice",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<LineItem> lineItems = new ArrayList<>();
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	
	public Long getVatRate() {
		return vatRate;
	}
	public void setVatRate(Long vatRate) {
		this.vatRate = vatRate;
	}
	
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	
	public List<LineItem> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
	
	
	@Transient
	public BigDecimal getSubtotal() {
		
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		BigDecimal result = BigDecimal.ZERO;
		
		//Looping the lineItems Array
		//adding items in an arraylist
		 for (int i = 0; i < lineItems.size(); i++)
		 {
			 list.add((lineItems.get(i).getLineItemTotal()));
			
		 }
			
		 //getting list of items from the list and adding the sum 
		 for (BigDecimal items : list) {
		     result = result.add(items);
		}
				 
		return BigDecimal.valueOf(result.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	@Transient
	public BigDecimal getVat() {
		
		double vat = getSubtotal().doubleValue() * (vatRate.doubleValue()/100);
		
		return BigDecimal.valueOf(vat).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	@Transient
	public BigDecimal getTotal() {
		
		double sum = Math.addExact(getSubtotal().intValue(), getVat().intValue());
		
		return BigDecimal.valueOf(sum).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}

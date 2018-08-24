package za.co.digitalplatoon.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="lineitem", schema="eoh_digital")
@JsonIdentityInfo(
		generator=ObjectIdGenerators.PropertyGenerator.class,
		property="id")
public class LineItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long quantity;
	private String description;
	private BigDecimal unitPrice;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	@Transient
	public BigDecimal getLineItemTotal() {
		
		double total = Math.multiplyExact(quantity, unitPrice.longValue()); 
		
		return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}

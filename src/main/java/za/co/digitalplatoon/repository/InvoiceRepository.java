package za.co.digitalplatoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.digitalplatoon.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}

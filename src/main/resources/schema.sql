CREATE schema EOH_digital;

----
CREATE TABLE eoh_digital.invoice (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    client varchar(255) NOT NULL,
    invoice_date date NOT NULL,
    vat_rate bigint(20) NOT NULL,
    PRIMARY KEY (id)
);
--
INSERT INTO eoh_digital.invoice (client, invoice_date, vat_rate) VALUES ('kutumela piet','2018-07-12',15);
--
CREATE TABLE eoh_digital.lineitem (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    description varchar(255) NOT NULL,
    quantity bigint(20) NOT NULL,
    unit_price decimal(5,2) NOT NULL,
    invoice_id bigint(20),
    PRIMARY KEY (id),
    FOREIGN KEY (invoice_id) REFERENCES eoh_digital.invoice(id)
);
--
INSERT INTO eoh_digital.lineitem (description, quantity, unit_price, invoice_id) VALUES ('bread',4,25.00, 1);
INSERT INTO eoh_digital.lineitem (description, quantity, unit_price, invoice_id) VALUES ('flakes',7,45.80, 1);



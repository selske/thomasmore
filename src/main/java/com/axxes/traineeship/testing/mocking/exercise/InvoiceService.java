package com.axxes.traineeship.testing.mocking.exercise;

import java.util.ArrayList;
import java.util.List;

public class InvoiceService {

    private final OrderDao orderDao;
    private final EmailService emailService;
    private final MailingService mailingService;

    public InvoiceService(OrderDao orderDao, EmailService emailService, MailingService mailingService) {
        this.orderDao = orderDao;
        this.emailService = emailService;
        this.mailingService = mailingService;
    }

    public List<Invoice> generateInvoices() {
        List<Order> orders = orderDao.findAll();

        List<Invoice> invoices = new ArrayList<>();
        for (Order order : orders) {
            Customer customer = order.getCustomer();

            double total = order.getTotal() - order.getTotal() * customer.getDiscount();
            Invoice invoice = new Invoice(customer, total);

            if (customer.getEmail() != null) {
                emailService.sendEmail(customer, invoice);
            } else {
                mailingService.requestMail(customer, invoice);
            }
            invoices.add(invoice);
        }
        return invoices;
    }

}

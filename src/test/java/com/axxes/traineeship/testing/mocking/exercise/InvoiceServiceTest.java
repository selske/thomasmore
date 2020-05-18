package com.axxes.traineeship.testing.mocking.exercise;

import com.axxes.traineeship.testing.mocking.exercise.*;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @Test
    void generateInvoices_noOrders() {
        OrderDao orderDaoMock = mock(OrderDao.class);
        when(orderDaoMock.findAll()).thenReturn(emptyList());

        InvoiceService invoiceService = new InvoiceService(orderDaoMock, null, null);

        List<Invoice> invoices = invoiceService.generateInvoices();

        assertThat(invoices, is(empty()));

        verify(orderDaoMock).findAll();
        verifyNoMoreInteractions(orderDaoMock);
    }

    @Test
    void generateInvoices_emailOrders() {
        OrderDao orderDaoMock = mock(OrderDao.class);
        EmailService emailServiceMock = mock(EmailService.class);

        Customer cust1 = new Customer(0., "Cust1");
        Customer cust2 = new Customer(.15, "Cust1");
        when(orderDaoMock.findAll()).thenReturn(asList(
                new Order(cust1, 100.),
                new Order(cust1, 120.),
                new Order(cust2, 200.)
        ));

        InvoiceService invoiceService = new InvoiceService(orderDaoMock, emailServiceMock, null);

        List<Invoice> invoices = invoiceService.generateInvoices();

        assertThat(invoices, contains(
                invoice(cust1, 100.),
                invoice(cust1, 120.),
                invoice(cust2, 170.)
        ));

        verify(emailServiceMock).sendEmail(cust1, invoices.get(0));
        verify(emailServiceMock).sendEmail(cust1, invoices.get(1));
        verify(emailServiceMock).sendEmail(cust2, invoices.get(2));

        ArgumentCaptor<Invoice> cust1InvoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
        verify(emailServiceMock, times(2)).sendEmail(eq(cust1), cust1InvoiceCaptor.capture());
        ArgumentCaptor<Invoice> cust2InvoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
        verify(emailServiceMock).sendEmail(eq(cust2), cust2InvoiceCaptor.capture());

        assertThat(cust1InvoiceCaptor.getAllValues(), Matchers.hasSize(2));
        assertThat(cust1InvoiceCaptor.getAllValues().get(0).getTotal(), is(100.));
        assertThat(cust1InvoiceCaptor.getAllValues().get(1).getTotal(), is(120.));

        assertThat(cust2InvoiceCaptor.getValue().getTotal(), is(170.));

        verify(orderDaoMock).findAll();
        verifyNoMoreInteractions(orderDaoMock, emailServiceMock);
    }

    @Test
    void generateInvoices_mailOrders() {
        OrderDao orderDaoMock = mock(OrderDao.class);
        MailingService mailingServiceMock = mock(MailingService.class);

        Customer cust1 = new Customer(0., null);
        Customer cust2 = new Customer(.15, null);
        when(orderDaoMock.findAll()).thenReturn(asList(
                new Order(cust1, 100.),
                new Order(cust1, 120.),
                new Order(cust2, 200.)
        ));

        InvoiceService invoiceService = new InvoiceService(orderDaoMock, null, mailingServiceMock);

        List<Invoice> invoices = invoiceService.generateInvoices();

        assertThat(invoices, contains(
                invoice(cust1, 100.),
                invoice(cust1, 120.),
                invoice(cust2, 170.)
        ));

        verify(mailingServiceMock).requestMail(eq(cust1), argThat(invoiceArgumentMatcher(cust1, 100.)));
        verify(mailingServiceMock).requestMail(eq(cust1), argThat(invoiceArgumentMatcher(cust1, 120.)));
        verify(mailingServiceMock).requestMail(eq(cust2), argThat(invoiceArgumentMatcher(cust2, 170.)));

        verify(orderDaoMock).findAll();
        verifyNoMoreInteractions(orderDaoMock, mailingServiceMock);
    }

    private Matcher<Invoice> invoice(Customer customer, double total) {
        return new TypeSafeMatcher<>() {
            @Override
            public boolean matchesSafely(Invoice invoice) {
                return invoice.getCustomer().equals(customer) && total == invoice.getTotal();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("An invoice for customer ").appendValue(customer)
                        .appendText(" for a total of ").appendValue(total);
            }
        };
    }

    private ArgumentMatcher<Invoice> invoiceArgumentMatcher(Customer customer, double total) {
        return invoice -> invoice.getCustomer().equals(customer) && total == invoice.getTotal();
    }

    @Test
    void generateInvoice_mixed() {
        OrderDao orderDaoMock = mock(OrderDao.class);
        MailingService mailingServiceMock = mock(MailingService.class);
        EmailService emailServiceMock = mock(EmailService.class);

        Customer cust1 = new Customer(0., null);
        Customer cust2 = new Customer(.15, "cust2@mail.com");
        when(orderDaoMock.findAll()).thenReturn(asList(
                new Order(cust1, 100.),
                new Order(cust1, 120.),
                new Order(cust2, 200.)
        ));

        InvoiceService invoiceService = new InvoiceService(orderDaoMock, emailServiceMock, mailingServiceMock);

        invoiceService.generateInvoices();

        verify(mailingServiceMock).requestMail(eq(cust1), argThat(invoiceArgumentMatcher(cust1, 100.)));
        verify(mailingServiceMock).requestMail(eq(cust1), argThat(invoiceArgumentMatcher(cust1, 120.)));
        verify(emailServiceMock).sendEmail(eq(cust2), argThat(invoiceArgumentMatcher(cust2, 170.)));

        verify(orderDaoMock).findAll();
        verifyNoMoreInteractions(orderDaoMock, mailingServiceMock);
    }

}

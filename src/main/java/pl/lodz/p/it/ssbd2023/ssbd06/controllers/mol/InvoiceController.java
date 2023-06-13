package pl.lodz.p.it.ssbd2023.ssbd06.controllers.mol;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static pl.lodz.p.it.ssbd2023.ssbd06.service.security.Permission.FACILITY_MANAGER;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd06.controllers.RepeatableTransactionProcessor;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.dto.PaginatedList;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.CreateInvoiceDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.GetPagedInvoicesListDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.InvoicesDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.endpoints.InvoiceEndpoint;
import pl.lodz.p.it.ssbd2023.ssbd06.service.security.etag.PayloadSigner;

@Path("/invoices")
@RequestScoped
public class InvoiceController extends RepeatableTransactionProcessor {

    @Inject
    private InvoiceEndpoint invoiceEndpoint;

    @Inject
    private PayloadSigner payloadSigner;

    @POST
    @Path("/list")
    @RolesAllowed({FACILITY_MANAGER})
    public Response getInvoices(@NotNull @Valid final GetPagedInvoicesListDto dto) {
        PaginatedList<InvoicesDto> invoices = retry(() -> invoiceEndpoint.getInvoicesList(dto), invoiceEndpoint);
        return Response.ok().entity(invoices).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({FACILITY_MANAGER})
    public Response getInvoiceById(@PathParam("id") final long id) {
        InvoicesDto invoice = retry(() -> invoiceEndpoint.getInvoiceById(id), invoiceEndpoint);
        return Response.ok().entity(invoice).header("ETag", payloadSigner.sign(invoice)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({FACILITY_MANAGER})
    public Response updateInvoice(@PathParam("id") final long id, @NotNull @Valid final InvoicesDto dto) {
        retry(() -> invoiceEndpoint.updateInvoice(id, dto), invoiceEndpoint);
        return Response.ok().build();
    }

    @POST
    @RolesAllowed(FACILITY_MANAGER)
    public Response addInvoice(@NotNull @Valid final CreateInvoiceDto dto) {
        invoiceEndpoint.addInvoice(dto);
        return Response.status(CREATED).build();
    }
}

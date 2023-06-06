package pl.lodz.p.it.ssbd2023.ssbd06.controllers.mol;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static pl.lodz.p.it.ssbd2023.ssbd06.service.security.Permission.FACILITY_MANAGER;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd06.controllers.RepeatableTransactionController;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.dto.PaginatedList;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.CreateTariffDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.GetPagedTariffsListDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.TariffsDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.UpdateTariffDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.endpoints.TariffEndpoint;

@Path("/tariffs")
@RequestScoped
public class TariffController extends RepeatableTransactionController {

    @Inject
    private TariffEndpoint tariffEndpoint;

    @POST
    @Path("/list")
    @PermitAll
    public Response getTariffs(@NotNull @Valid final GetPagedTariffsListDto dto) {
        PaginatedList<TariffsDto> tariffs = retry(() -> tariffEndpoint.getTariffsList(dto), tariffEndpoint);
        return Response.ok().entity(tariffs).build();
    }

    @PUT
    @Path("/{id}")
    public void updateTariff(@PathParam("id") final long id, @NotNull @Valid final UpdateTariffDto dto) {
        tariffEndpoint.updateTariff(id, dto);
    }

    @POST
    @RolesAllowed({FACILITY_MANAGER})
    public Response addTariff(@NotNull @Valid final CreateTariffDto dto) {
        tariffEndpoint.addTariff(dto);
        return Response.status(CREATED).build();
    }

}

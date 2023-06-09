package pl.lodz.p.it.ssbd2023.ssbd06.controllers.mol;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;
import static pl.lodz.p.it.ssbd2023.ssbd06.service.security.Permission.FACILITY_MANAGER;
import static pl.lodz.p.it.ssbd2023.ssbd06.service.security.Permission.OWNER;


import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2023.ssbd06.controllers.RepeatableTransactionController;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.dto.PaginatedList;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.CreateMainWaterMeterDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.GetPagedWaterMetersListDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.ReplaceWaterMeterDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.UpdateWaterMeterDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.WaterMeterActiveStatusDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.WaterMeterCheckDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.dto.WaterMetersDto;
import pl.lodz.p.it.ssbd2023.ssbd06.mol.endpoints.WaterMeterEndpoint;

@Log
@Path("/water-meters")
@RequestScoped
public class WaterMeterController extends RepeatableTransactionController {

    @Inject
    private WaterMeterEndpoint waterMeterEndpoint;

    @POST
    @Path("/list")
    @RolesAllowed({FACILITY_MANAGER})
    public Response getWaterMeters(@NotNull @Valid final GetPagedWaterMetersListDto dto) {
        PaginatedList<WaterMetersDto> waterMeters = retry(() -> waterMeterEndpoint.getWaterMetersList(dto), waterMeterEndpoint);
        return Response.ok().entity(waterMeters).build();
    }

    @GET
    @Path("/apartment/{apartmentId}")
    public Response getWaterMatersByApartmentId(@PathParam("apartmentId") final long apartmentId) {
        return Response.ok().entity(waterMeterEndpoint.getWaterMetersByApartmentId(apartmentId)).build();
    }

    @POST
    @Path("/main-water-meter")
    @RolesAllowed(FACILITY_MANAGER)
    public Response createMainWaterMeter(@NotNull @Valid final CreateMainWaterMeterDto dto) {
        retry(() -> waterMeterEndpoint.createMainWaterMeter(dto), waterMeterEndpoint);
        return Response.status(CREATED).build();
    }

    @POST
    @Path("/{id}/water-meter-check")
    @RolesAllowed({FACILITY_MANAGER, OWNER})
    public Response performWaterMeterCheck(@PathParam("id") final long waterMeterId, @NotNull @Valid final WaterMeterCheckDto dto) {
        waterMeterEndpoint.performWaterMeterCheck(dto);
        return Response.status(NO_CONTENT).build();
    }

    @POST
    @Path("/{id}")
    @RolesAllowed(FACILITY_MANAGER)
    public void replaceWaterMeter(@PathParam("id") final long waterMeterId, @NotNull @Valid final ReplaceWaterMeterDto dto) {
        waterMeterEndpoint.replaceWaterMeter(waterMeterId, dto);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed(FACILITY_MANAGER)
    public void updateWaterMeter(@PathParam("id") final long waterMeterId, @NotNull @Valid final UpdateWaterMeterDto dto) {
        waterMeterEndpoint.updateWaterMeter(waterMeterId, dto);
    }

    @RolesAllowed(FACILITY_MANAGER)
    @PUT
    @Path("/{id}/active")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeWaterMeterActiveStatus(@PathParam("id") final long waterMeterId, @NotNull @Valid final WaterMeterActiveStatusDto dto) {
        retry(() -> waterMeterEndpoint.changeWaterMeterActiveStatus(waterMeterId, dto), waterMeterEndpoint);
        return Response.ok().build();
    }
}
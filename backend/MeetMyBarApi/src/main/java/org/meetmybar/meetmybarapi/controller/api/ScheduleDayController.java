package org.meetmybar.meetmybarapi.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.meetmybar.api.controller.ApiUtil;
import org.meetmybar.meetmybarapi.models.dto.ScheduleDay;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-17T14:26:25.502834400+01:00[Europe/Paris]")
@Validated
@Controller
@Tag(name = "ScheduleDay", description = "the ScheduleDay API")
public interface ScheduleDayController {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * DELETE /scheduleday/{scheduledayId} : Your DELETE endpoint
     *
     * @param scheduledayId  (required)
     * @return OK (status code 200)
     */
    @Operation(
            operationId = "deleteScheduledayScheduledayId",
            summary = "Your DELETE endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleDay.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/scheduleday/{scheduledayId}",
            produces = { "application/json" }
    )
    default ResponseEntity<ScheduleDay> deleteScheduledayScheduledayId(
            @Parameter(name = "scheduledayId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("scheduledayId") int scheduledayId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /scheduleday : Your GET endpoint
     *
     * @return OK (status code 200)
     */
    @Operation(
            operationId = "getScheduleday",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScheduleDay.class)))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/scheduleday",
            produces = { "application/json" }
    )
    default ResponseEntity<List<ScheduleDay>> getScheduleday(

    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }, { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /scheduleday/{scheduledayId} : Your GET endpoint
     *
     * @param scheduledayId  (required)
     * @return OK (status code 200)
     */
    @Operation(
            operationId = "getScheduledayScheduledayId",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleDay.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/scheduleday/{scheduledayId}",
            produces = { "application/json" }
    )
    default ResponseEntity<ScheduleDay> getScheduledayScheduledayId(
            @Parameter(name = "scheduledayId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("scheduledayId") int scheduledayId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PATCH /scheduleday : Your PATCH endpoint
     *
     * @param scheduleDay  (optional)
     * @return OK (status code 200)
     */
    @Operation(
            operationId = "patchScheduleday",
            summary = "Your PATCH endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleDay.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/scheduleday",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    default ResponseEntity<ScheduleDay> patchScheduleday(
            @Parameter(name = "ScheduleDay", description = "") @Valid @RequestBody(required = false) ScheduleDay scheduleDay
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /scheduleday : Your POST endpoint
     *
     * @param scheduleDay  (optional)
     * @return OK (status code 200)
     */
    @Operation(
            operationId = "postScheduleday",
            summary = "Your POST endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleDay.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/scheduleday",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    default ResponseEntity<ScheduleDay> postScheduleday(
            @Parameter(name = "ScheduleDay", description = "") @Valid @RequestBody(required = false) ScheduleDay scheduleDay
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


}

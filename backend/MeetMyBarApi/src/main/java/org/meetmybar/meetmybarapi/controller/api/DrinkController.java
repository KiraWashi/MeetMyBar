package org.meetmybar.meetmybarapi.controller.api;

import org.meetmybar.api.controller.ApiUtil;
import org.meetmybar.api.model.Bar;
import org.meetmybar.meetmybarapi.models.dto.Drink;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-14T14:14:59.611161700+01:00[Europe/Paris]")
@Validated
@Controller
@Tag(name = "Default", description = "the Default API")
public interface DrinkController {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }


    /**
     * DELETE /drink : Your DELETE endpoint
     *
     * @param drink  (optional)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "deleteDrink",
            summary = "Your DELETE endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Drink.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/drink",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    default ResponseEntity<Drink> deleteDrink(
            @Parameter(name = "Drink", description = "") @Valid @RequestBody(required = false) Drink drink
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /drink : Your GET endpoint
     *
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "getDrink",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = org.meetmybar.api.model.Drink.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/drink",
            produces = { "application/json" }
    )
    default ResponseEntity<List<Drink>> getDrink(

    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }, { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /drink/{drinkId} : Your GET endpoint
     *
     * @param drinkId  (required)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "getDrinkDrinkName",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = org.meetmybar.api.model.Drink.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/drink/{drinkId}",
            produces = { "application/json" }
    )
    default ResponseEntity<Drink> getDrinkByDrinkId(
            @Parameter(name = "drinkId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("drinkId") String drinkId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /drink/name/{drinkName} : Your GET endpoint
     *
     * @param drinkName  (required)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "getDrinkNameDrinkName",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = org.meetmybar.api.model.Drink.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/drink/name/{drinkName}",
            produces = { "application/json" }
    )
    default ResponseEntity<Drink> getDrinkByDrinkName(
            @Parameter(name = "drinkName", description = "", required = true, in = ParameterIn.PATH) @PathVariable("drinkName") String drinkName
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PATCH /drink : Your PATCH endpoint
     *
     * @param drink  (optional)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "patchDrink",
            summary = "Your PATCH endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Drink.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/drink",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    default ResponseEntity<Drink> patchDrink(
            @Parameter(name = "Drink", description = "") @Valid @RequestBody(required = false) Drink drink
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /drink : Your POST endpoint
     *
     * @param drink  (optional)
     * @return Created (status code 201)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "postDrink",
            summary = "Your POST endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Drink.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/drink",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    default ResponseEntity<Drink> postDrink(
            @Parameter(name = "Drink", description = "") @Valid @RequestBody(required = false) Drink drink
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }
}

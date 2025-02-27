/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
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
import org.meetmybar.meetmybarapi.models.dto.Photo;
import org.meetmybar.meetmybarapi.models.modif.Bar;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T15:34:47.595602900+01:00[Europe/Paris]")
@Validated
@Controller
@Tag(name = "Bar", description = "the Bar API")
public interface BarController {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * DELETE /bar/{barId} : Your DELETE endpoint
     *
     * @param barId  (required)
     * @return OK (status code 200)
     */
    @Operation(
            operationId = "deleteBarBarId",
            summary = "Your DELETE endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = org.meetmybar.api.model.Bar.class))
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
            value = "/bar/{barId}",
            produces = { "application/json" }
    )
    default ResponseEntity<Bar> deleteBarBarId(
            @Parameter(name = "barId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("barId") Integer barId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"planning\" : [ { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }, { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" } ], \"address\" : \"address\", \"city\" : \"city\", \"drinks\" : [ { \"name\" : \"name\", \"id\" : 1, \"type\" : \"biere_blonde\", \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }, { \"name\" : \"name\", \"id\" : 1, \"type\" : \"biere_blonde\", \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 } ], \"name\" : \"name\", \"id\" : 0, \"postal_code\" : \"postal_code\", \"capacity\" : 6 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /bar : Your GET endpoint
     *
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "getBar",
        summary = "Your GET endpoint",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Bar.class)))
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
        value = "/bar",
        produces = { "application/json" }
    )
    default ResponseEntity<List<Bar>> getBar(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"planning\" : [ { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }, { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" } ], \"address\" : \"address\", \"beers\" : [ { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }, { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 } ], \"name\" : \"name\", \"id\" : 0, \"capacity\" : 6 }, { \"planning\" : [ { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }, { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" } ], \"address\" : \"address\", \"beers\" : [ { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }, { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 } ], \"name\" : \"name\", \"id\" : 0, \"capacity\" : 6 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /bar/address/{barAddress} : Your GET endpoint
     *
     * @param barAddress  (required)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "getBarByAddress",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = org.meetmybar.api.model.Bar.class))
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
            value = "/bar/address/{barAddress}",
            produces = { "application/json" }
    )
    default ResponseEntity<Bar> getBarByAddress(
            @Parameter(name = "barAddress", description = "", required = true, in = ParameterIn.PATH) @PathVariable("barAddress") String barAddress
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"planning\" : [ { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }, { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" } ], \"address\" : \"address\", \"city\" : \"city\", \"drinks\" : [ { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }, { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 } ], \"name\" : \"name\", \"id\" : 0, \"postal_code\" : \"postal_code\", \"capacity\" : 6 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }



    /**
     * GET /bar/{barId} : Your GET endpoint
     *
     * @param barId  (required)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "getBarById",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = org.meetmybar.api.model.Bar.class))
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
            value = "/bar/{barId}",
            produces = { "application/json" }
    )
    default ResponseEntity<Bar> getBarById(
            @Parameter(name = "barId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("barId") int barId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"planning\" : [ { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }, { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" } ], \"address\" : \"address\", \"city\" : \"city\", \"drinks\" : [ { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }, { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 } ], \"name\" : \"name\", \"id\" : 0, \"postal_code\" : \"postal_code\", \"capacity\" : 6 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /bar/name/{barName} : Your GET endpoint
     *
     * @param barName  (required)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "getBarByName",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Bar.class))
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
            value = "/bar/name/{barName}",
            produces = { "application/json" }
    )
    default ResponseEntity<Bar> getBarByName(
            @Parameter(name = "barName", description = "", required = true, in = ParameterIn.PATH) @PathVariable("barName") String barName
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"planning\" : [ { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }, { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" } ], \"address\" : \"address\", \"city\" : \"city\", \"drinks\" : [ { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }, { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 } ], \"name\" : \"name\", \"id\" : 0, \"postal_code\" : \"postal_code\", \"capacity\" : 6 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /bar/photo/{barId} : Your GET endpoint
     *
     * @param barId  (required)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
            operationId = "getPhotoByBar",
            summary = "Your GET endpoint",
            tags = {  },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Photo.class)))
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
            value = "/bar/photo/{barId}",
            produces = { "application/json" }
    )
    default ResponseEntity<List<Photo>> getPhotoByBar(
            @Parameter(name = "barId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("barId") Integer barId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"id\": 1, \"description\": \"Photo du bar\", \"imageData\": \"base64EncodedImage\", \"mainPhoto\": true }, { \"id\": 2, \"description\": \"Terrasse\", \"imageData\": \"base64EncodedImage\", \"mainPhoto\": false } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PATCH /bar : Mettre à jour un bar
     *
     * @param bar Les informations du bar à mettre à jour (required)
     * @return Bar mis à jour avec succès (status code 200)
     *         ou Bad Request (status code 400)
     *         ou Unauthorized (status code 401)
     *         ou Forbidden (status code 403)
     *         ou Not Found (status code 404)
     *         ou Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "updateBar",
        summary = "Mettre à jour un bar",
        tags = { },
        responses = {
            @ApiResponse(responseCode = "200", description = "Bar mis à jour avec succès", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Bar.class))
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
        value = "/bar",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Bar> updateBar(
        @Parameter(name = "Bar", description = "Les informations du bar à mettre à jour") 
        @Valid @RequestBody(required = true) Bar bar
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"planning\" : [ { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" }, { \"closing\" : \"closing\", \"id\" : 5, \"opening\" : \"opening\", \"day\" : \"day\" } ], \"address\" : \"address\", \"city\" : \"city\", \"drinks\" : [ { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 }, { \"name\" : \"name\", \"id\" : 1, \"brand\" : \"brand\", \"alcohol_degree\" : 5.962133916683182 } ], \"name\" : \"name\", \"id\" : 0, \"postal_code\" : \"postal_code\", \"capacity\" : 6 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * POST /bar : Your POST endpoint
     *
     * @param bar  (optional)
     * @return Created (status code 201)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "addBar",
        summary = "Créer un nouveau bar avec son planning",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "201", description = "Bar créé avec succès", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Bar.class))
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
        value = "/bar",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Bar> addBar(
        @Parameter(name = "Bar", description = "Le bar à créer avec sa liste de jours d'ouverture") 
        @Valid @RequestBody(required = false) Bar bar
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"planning\" : [ { \"closing\" : \"18:00\", \"id\" : 5, \"opening\" : \"09:00\", \"day\" : \"LUNDI\" }, { \"closing\" : \"22:00\", \"id\" : 6, \"opening\" : \"14:00\", \"day\" : \"MARDI\" } ], \"address\" : \"123 rue de la Soif\", \"city\" : \"Paris\", \"drinks\" : [ { \"name\" : \"Blonde Artisanale\", \"id\" : 1, \"type\" : \"biere_blonde\", \"brand\" : \"Brasserie Locale\", \"alcohol_degree\" : 5.5 }, { \"name\" : \"IPA\", \"id\" : 2, \"type\" : \"biere_ipa\", \"brand\" : \"Craft Beer\", \"alcohol_degree\" : 6.2 } ], \"name\" : \"Le Bar des Amis\", \"id\" : 0, \"postal_code\" : \"75001\", \"capacity\" : 50 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}

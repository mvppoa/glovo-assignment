package com.glovoapp.backender.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

@Api(value = "root")
public interface RootApi {

    @ApiOperation(value = "root", nickname = "getWelcomeMessage", notes = "This endpoint will return the welcome message.",
            response = String.class, responseContainer = "String", tags={ "root-resource", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "String"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping(value = "/",
            produces = { "application/json" })
    String getWelcomeMessage();

}

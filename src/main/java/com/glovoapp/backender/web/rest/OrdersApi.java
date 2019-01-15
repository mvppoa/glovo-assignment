package com.glovoapp.backender.web.rest;

import com.glovoapp.backender.web.rest.vm.OrderVM;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-01-14T00:16:48.589Z")

@Api(value = "orders", description = "the orders API")
public interface OrdersApi {

    @ApiOperation(value = "orders", nickname = "ordersByCourierId", notes = "This endpoint will return all the available orders for the courier with the given id.", response = OrderVM.class, responseContainer = "List", tags={ "order-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = OrderVM.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/orders/{courierId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<OrderVM>> ordersByCourierId(@ApiParam(value = "Id of the courier for the orders search",required=true) @PathVariable("courierId") String courierId);


    @ApiOperation(value = "orders", nickname = "ordersUsingGET", notes = "This endpoint will return all the available orders.", response = OrderVM.class, responseContainer = "List", tags={ "order-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = OrderVM.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/orders",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<OrderVM>> ordersUsingGET();

}

package com.glovoapp.backender.cucumber.step;

import com.glovoapp.backender.web.rest.OrdersApiResource;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CouriersOrderSearchStepdefs extends StepDefs{

    @Autowired
    OrdersApiResource ordersApiResource;

    private MockMvc restMockMvc;

    @Given("^The groceries and couriers list located in /couriers.json and /orders.json$")
    public void the_groceries_and_couriers_list_located_in_and() {
        this.restMockMvc = MockMvcBuilders.standaloneSetup(ordersApiResource).build();
    }

    @When("^I search for the courier with id (.+)$")
    public void i_search_for_the_courier_with_id(String courierId) throws Exception {
        actions = this.restMockMvc.perform(get("/orders/" + courierId).accept(MediaType.APPLICATION_JSON));
    }

    @Then("^It returns (.+) orders for the courier$")
    public void it_returns_orders_for_the_courier(String string) throws Exception {
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isNotEmpty());

    }

    @When("^I search for the courier with invalid id (.+)$")
    public void i_search_for_the_courier_with_invalid_id(String courierId) throws Exception {
        actions = this.restMockMvc.perform(get("/orders/" + courierId).accept(MediaType.APPLICATION_JSON));
    }


    @Then("^It doesn't return any orders$")
    public void it_doesn_t_return_any_orders() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

}

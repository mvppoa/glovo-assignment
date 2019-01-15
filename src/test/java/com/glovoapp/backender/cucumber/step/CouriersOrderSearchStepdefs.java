package com.glovoapp.backender.cucumber.step;

import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.web.rest.OrdersApiResource;
import com.glovoapp.backender.web.rest.vm.OrderVM;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.constraints.AssertTrue;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CouriersOrderSearchStepdefs extends StepDefs {

    @Autowired
    OrdersApiResource ordersApiResource;

    @Value("${backender.resources.orders.box.restrictions.values}")
    List<String> validItems;

    private MockMvc restMockMvc;

    @Given("^The groceries and couriers list located in /couriers.json and /orders.json$")
    public void theGroceriesAndCouriersListLocatedInAnd() {
        this.restMockMvc = MockMvcBuilders.standaloneSetup(ordersApiResource).build();
    }

    @When("^I search for the courier with id (.+)$")
    public void iSearchForTheCourierWithId(String courierId) throws Exception {
        actions = this.restMockMvc.perform(get("/orders/" + courierId).accept(MediaType.APPLICATION_JSON));
    }

    @Then("^It returns a list of orders to the courier$")
    public void itReturnsOrdersForTheCourier() throws Exception {
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isNotEmpty());

    }

    @When("^I search for the courier with invalid id (.+)$")
    public void iSearchForTheCourierWithInvalidId(String courierId) throws Exception {
        actions = this.restMockMvc.perform(get("/orders/" + courierId).accept(MediaType.APPLICATION_JSON));
    }


    @Then("^It doesn't return any orders$")
    public void itDoesnTReturnAnyOrders() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Then("^It does not return any orders that contains the restriction values located in application.properties$")
    public void itDoesNotReturnAnyOrdersThatContainsTheRestrictionValuesLocatedInApplicationProperties() throws Exception {

        String result = actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn().getResponse().getContentAsString();

        Type type = new TypeToken<List<OrderVM>>() {
        }.getType();
        List<OrderVM> orderVMS = new Gson().fromJson(result, type);
        orderVMS.forEach(orderVM -> {
            validItems.forEach(validItem -> {
                assertFalse(orderVM.getDescription().toLowerCase().contains(validItem.toLowerCase()));
            });
        });
    }

    @Then("^It does not return any orders that contains the distances higher than 5KMs$")
    public void itDoesNotReturnAnyOrdersThatContainsTheDistancesHigherThanKMs() throws Exception {
        String result = actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn().getResponse().getContentAsString();

        Type type = new TypeToken<List<OrderVM>>() {
        }.getType();
        List<OrderVM> orderVMS = new Gson().fromJson(result, type);
        assertTrue(orderVMS.stream().noneMatch(orderVM -> orderVM.getId().equals("order-9cf3263e7992")));
    }

    @Then("^Returns a list of ordervms in the proper order$")
    public void returnsAListOfOrdervmsInTheProperOrder() throws Exception{
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[*].id").value(Matchers.containsinOrder("order-f44762e8eb0d", "order-eb69a1ec6a3d",
                        "order-21fa7ee99624","order-50800b932298","order-ad6408f60eb9","order-322919652412",
                        "order-bac0ba295e31","order-da5bb5e3a09c","order-80bce95470d3","order-9a33229f814e","order-29510b2af039")));
    }
}

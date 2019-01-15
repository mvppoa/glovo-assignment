package com.glovoapp.backender.cucumber.step;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration
public abstract class StepDefs {

    protected ResultActions actions;

}

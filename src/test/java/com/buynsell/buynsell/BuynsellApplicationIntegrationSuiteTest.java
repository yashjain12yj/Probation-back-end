package com.buynsell.buynsell;


import com.buynsell.buynsell.controller.AuthControllerIntegrationTest;
import com.buynsell.buynsell.controller.PostControllerIntegrationTest;
import com.buynsell.buynsell.controller.SearchControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthControllerIntegrationTest.class,
        PostControllerIntegrationTest.class,
        SearchControllerIntegrationTest.class
})
public class BuynsellApplicationIntegrationSuiteTest {
}

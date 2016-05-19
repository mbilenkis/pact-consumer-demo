package com.expedia.demo.pact;


import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class PactTest {

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("provider", "localhost", 8080, this);

    @Pact(provider="provider", consumer="consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("A request to get a message")
                .path("/message")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body("{\"text\": \"Hello World\"}")
                .toFragment();
    }

    @Test
    @PactVerification("provider")
    public void runTest() {

        new RestTemplate().getForObject("http://localhost:8080/message", String.class);
    }
}

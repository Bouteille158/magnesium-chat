package com.sodium.api;

import org.junit.jupiter.api.Test;

import com.sodium.api.controllers.TestController;

public class TestControllerTests {
    @Test
    public void test() {
        TestController testController = new TestController();
        assert testController.test().equals("Hello, World!");
    }
}

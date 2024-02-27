package com.sodium.api;

import org.junit.jupiter.api.Test;

public class TestControllerTests {
    @Test
    public void test() {
        TestController testController = new TestController();
        assert testController.test().equals("Hello, World!");
    }
}

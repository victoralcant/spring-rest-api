package com.victor.springboot.rest;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class RestApplicationTests {

    @Test
    void contextLoads() {
        assertThat(true, Is.is(Boolean.TRUE));
    }

}

package com.johnjqc.devsu.persona.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void givenValidPersonAndClient_whenClientIsCreated_thenClientShouldContainCorrectData() {

        Person person = Person.builder()
                .personId(1L)
                .name("Jose")
                .gender(Gender.MALE)
                .age(30)
                .identification("123456789")
                .address("Otavalo")
                .phone("999999")
                .build();

        Client client = Client.builder()
                .clientId(1L)
                .person(person)
                .password("1234")
                .active(true)
                .build();

        assertNotNull(client);
        assertEquals("1234", client.getPassword());
        assertTrue(client.getActive());
        assertEquals("Jose", client.getPerson().getName());
    }
}

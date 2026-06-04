package com.johnjqc.devsu.persona.controller;

import com.johnjqc.devsu.persona.api.CustomersApi;
import com.johnjqc.devsu.persona.dto.CreateCustomerRequest;
import com.johnjqc.devsu.persona.dto.CustomerPageResponse;
import com.johnjqc.devsu.persona.dto.CustomerResponse;
import com.johnjqc.devsu.persona.dto.UpdateCustomerRequest;

import com.johnjqc.devsu.persona.mapper.ClientApiMapper;
import com.johnjqc.devsu.persona.service.ClientService;
import com.johnjqc.devsu.persona.service.dto.ClientServiceRequest;
import com.johnjqc.devsu.persona.service.dto.ClientServiceResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CustomerController.class);

    private final ClientService clientService;
    private final ClientApiMapper clientApiMapper;

    @Override
    public ResponseEntity<CustomerPageResponse> getCustomers(
            UUID xCorrelationId,
            UUID xTransactionId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {

        LOGGER.info(
                "Getting customers. correlationId={}, transactionId={}, page={}, size={}",
                xCorrelationId,
                xTransactionId,
                page,
                size
        );

        Pageable pageable = PageRequest.of(page, size);

        Page<ClientServiceResponse> result = clientService.findAll(pageable);

        CustomerPageResponse response = clientApiMapper.toPageResponse(result);

        return ResponseEntity.ok()
                .header("X-Correlation-Id", String.valueOf(xCorrelationId))
                .header("X-Transaction-Id", String.valueOf(xTransactionId))
                .body(response);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerById(
            Long customerId,
            UUID xCorrelationId,
            UUID xTransactionId
    ) {
        LOGGER.info(
                "Getting customer. correlationId={}, transactionId={}, customerId={}",
                xCorrelationId,
                xTransactionId,
                customerId
        );

        ClientServiceResponse clientServiceResponse = clientService.findById(customerId);
        CustomerResponse response = clientApiMapper.toResponse(clientServiceResponse);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid CreateCustomerRequest createCustomerRequest,
            UUID xCorrelationId,
            UUID xTransactionId
    ) {

        LOGGER.info(
                "Creating customer. correlationId={}, transactionId={}, identification={}",
                xCorrelationId,
                xTransactionId,
                createCustomerRequest.getIdentification()
        );

        ClientServiceRequest serviceRequest = clientApiMapper.toServiceRequest(createCustomerRequest);
        ClientServiceResponse serviceResponse = clientService.create(serviceRequest);
        CustomerResponse response = clientApiMapper.toResponse(serviceResponse);

        LOGGER.info(
                "Customer created successfully. correlationId={}, transactionId={}, customerId={}",
                xCorrelationId,
                xTransactionId,
                response.getCustomerId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("X-Correlation-Id", String.valueOf(xCorrelationId))
                .header("X-Transaction-Id", String.valueOf(xTransactionId))
                .body(response);
    }

    @Override
    public ResponseEntity<CustomerResponse> updateCustomer(
            Long customerId,
            @Valid UpdateCustomerRequest updateCustomerRequest,
            UUID xCorrelationId,
            UUID xTransactionId
    ) {

        LOGGER.info(
                "Updating customer. correlationId={}, transactionId={}, customerId={}",
                xCorrelationId,
                xTransactionId,
                customerId
        );

        ClientServiceRequest serviceRequest = clientApiMapper.toServiceRequest(customerId, updateCustomerRequest);
        ClientServiceResponse serviceResponse = clientService.update(customerId, serviceRequest);
        CustomerResponse response = clientApiMapper.toResponse(serviceResponse);

        LOGGER.info(
                "Customer updated successfully. correlationId={}, transactionId={}, customerId={}",
                xCorrelationId,
                xTransactionId,
                customerId
        );

        return ResponseEntity
                .ok()
                .header("X-Correlation-Id", String.valueOf(xCorrelationId))
                .header("X-Transaction-Id", String.valueOf(xTransactionId))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(
            Long customerId,
            UUID xCorrelationId,
            UUID xTransactionId
    ) {

        LOGGER.info(
                "Deleting customer. correlationId={}, transactionId={}, customerId={}",
                xCorrelationId,
                xTransactionId,
                customerId
        );

        clientService.delete(customerId);

        LOGGER.info(
                "Customer deleted successfully. correlationId={}, transactionId={}, customerId={}",
                xCorrelationId,
                xTransactionId,
                customerId
        );

        return ResponseEntity
                .noContent()
                .header("X-Correlation-Id", String.valueOf(xCorrelationId))
                .header("X-Transaction-Id", String.valueOf(xTransactionId))
                .build();
    }
}
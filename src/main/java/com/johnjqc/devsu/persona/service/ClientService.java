package com.johnjqc.devsu.persona.service;

import com.johnjqc.devsu.persona.service.dto.ClientServiceRequest;
import com.johnjqc.devsu.persona.service.dto.ClientServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    ClientServiceResponse create(ClientServiceRequest request);

    ClientServiceResponse findById(Long id);

    Page<ClientServiceResponse> findAll(Pageable pageable);

    ClientServiceResponse update(Long id, ClientServiceRequest request);

    void delete(Long id);

    ClientServiceResponse findByIdentification(String identification);
}

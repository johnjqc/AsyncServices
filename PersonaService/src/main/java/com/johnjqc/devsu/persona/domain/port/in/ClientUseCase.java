package com.johnjqc.devsu.persona.domain.port.in;

import com.johnjqc.devsu.persona.domain.service.dto.ClientServiceRequest;
import com.johnjqc.devsu.persona.domain.service.dto.ClientServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientUseCase {

    ClientServiceResponse create(ClientServiceRequest request);

    ClientServiceResponse findById(Long id);

    Page<ClientServiceResponse> findAll(Pageable pageable);

    ClientServiceResponse update(Long id, ClientServiceRequest request);

    void delete(Long id);

    ClientServiceResponse findByIdentification(String identification);
}

package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.B2h;
import cl.kintsugi.delivery.service.registry.request.B2hRequest;

import java.util.List;

public interface IB2hService {

    List<B2h> getAllB2hTransactions();

    B2h findB2hByUuid(String uuid);

    B2h saveB2hTransaction(B2hRequest b2hRequest);

    B2h updateB2hTransaction(String uuid, B2hRequest b2hRequest);

    Boolean disableB2hTransaction(String uuid, String userName);

    Boolean deleteB2hTransactionByUuid(String uuid);
}

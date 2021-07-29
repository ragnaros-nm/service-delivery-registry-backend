package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.B2h;
import cl.kintsugi.delivery.service.registry.request.B2hRequest;
import cl.kintsugi.delivery.service.registry.response.B2hResponse;
import cl.kintsugi.delivery.service.registry.response.Response;

import java.util.List;

public interface IB2hService {

    List<B2hResponse> getAllB2hTransactions();

    B2h findB2hByUuid(String uuid);

    B2h saveB2hTransaction(B2hRequest b2hRequest);

    B2h updateB2hTransaction(String uuid, B2hRequest b2hRequest);

    Response disableB2hTransaction(String uuid, String userName);

    Response deleteB2hTransactionByUuid(String uuid);
}

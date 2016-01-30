package com.summercoding.zooplus.repository;

import com.summercoding.zooplus.model.ExchangeRateRequest;
import org.springframework.data.repository.CrudRepository;

public interface HistoryElementRepository extends CrudRepository<ExchangeRateRequest, Long> {
}

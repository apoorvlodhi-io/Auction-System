package com.arango.auction.repository;


import com.arango.auction.model.Item;
import com.arangodb.springframework.repository.ArangoRepository;

import java.util.List;

public interface ItemRepository extends ArangoRepository<Item, String> {
}

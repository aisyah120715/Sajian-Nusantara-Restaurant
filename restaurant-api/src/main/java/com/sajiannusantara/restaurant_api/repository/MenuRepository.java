package com.sajiannusantara.restaurant_api.repository;

import com.sajiannusantara.restaurant_api.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Menu entity
 */
@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {

    /**
     * Find all menu items by category
     */
    List<Menu> findByCategory(String category);

    /**
     * Find all available menu items
     */
    List<Menu> findByAvailableTrue();

    /**
     * Find all menu items by category and availability
     */
    List<Menu> findByCategoryAndAvailableTrue(String category);

}


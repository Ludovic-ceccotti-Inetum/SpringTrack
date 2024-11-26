package accounts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import rewards.internal.restaurant.RestaurantRepository;

/**
 * - Make this class implement HealthIndicator interface
 * - Make this class a component
 * - Inject RestaurantRepository through constructor injection
 * - health() method should return DOWN if the repository is empty
 *   (no restaurants) or UP otherwise. (Note that RestaurantRepository
 *   has a method that returns number of restaurants.)
 */
@Component
public class RestaurantHealthCheck implements HealthIndicator {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantHealthCheck(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Health health() {
        return restaurantRepository.getRestaurantCount() > 0 ? Health.status(Status.UP).build() : Health.down().build();
    }
}


/**
 * TODO-25 (Optional): Experiment with HealthIndicator above
 * - Change "spring.sql.init.data-locations" property in the
 *   "application.properties" file back to use "data-no-restaurants.sql"
 * - Include the restaurant count as extra detail when DOWN state.
 * - Instead of returning DOWN when there are no restaurants,
 *   define and use a custom status called NO_RESTAURANTS.
 * - When there are no restaurants in the DB, what top-level status
 *   is returned for the "application" health group?
 * - Set "management.endpoint.health.group.application.status.order" property
 *   in the "application.properties" file so that NO_RESTAURANTS
 *   gets displayed as top-level status for the "application" health group.
 * - Restart the application and verify the result.
 */
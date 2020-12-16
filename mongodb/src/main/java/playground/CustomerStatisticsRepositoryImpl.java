package playground;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.util.List;

@RequiredArgsConstructor
class CustomerStatisticsRepositoryImpl implements CustomerStatisticsRepository {

    private final MongoOperations operations;

    @Override
    public List<CustomerStatisticsByCountry> findStatisticsByCountry() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("address.country").count().as("count"),
                Aggregation.project("count").and("country").previousOperation(),
                Aggregation.sort(Sort.Direction.ASC, "address.country"));
        return this.operations.aggregate(agg, Customer.class, CustomerStatisticsByCountry.class).getMappedResults();
    }

}
package playground;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

@RequiredArgsConstructor
class CustomerStatisticsRepositoryImpl implements CustomerStatisticsRepository {

    private final ElasticsearchOperations operations;

    @Override
    public SearchHits<Customer> findStatisticsByCountry() {
        var builder = new NativeSearchQueryBuilder();
        var query = builder.addAggregation(AggregationBuilders.terms("statisticsByCountry")
                .field("address.country")
                .order(BucketOrder.key(true)))
                .build();
        return this.operations.search(query, Customer.class);
    }

}
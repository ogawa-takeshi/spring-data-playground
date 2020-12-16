package playground;

import org.springframework.data.elasticsearch.core.SearchHits;

public interface CustomerStatisticsRepository {

    SearchHits<Customer> findStatisticsByCountry();

}

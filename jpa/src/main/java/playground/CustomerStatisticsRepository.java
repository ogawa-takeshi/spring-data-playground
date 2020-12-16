package playground;

import java.util.List;

public interface CustomerStatisticsRepository {

    List<CustomerStatisticsByCountry> findStatisticsByCountry();

}

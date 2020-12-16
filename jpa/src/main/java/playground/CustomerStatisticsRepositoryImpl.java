package playground;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
class CustomerStatisticsRepositoryImpl implements CustomerStatisticsRepository {

    private final EntityManager em;

    @Override
    public List<CustomerStatisticsByCountry> findStatisticsByCountry() {
        var builder = this.em.getCriteriaBuilder();
        var criteria = builder.createQuery(CustomerStatisticsByCountry.class);
        var customer = criteria.from(Customer.class);
        criteria.multiselect(customer.get(Customer_.address).get(Address_.country), builder.count(customer.get(Customer_.id)))
                .groupBy(customer.get(Customer_.address).get(Address_.country))
                .orderBy(builder.asc(customer.get(Customer_.address).get(Address_.country)));
        return this.em.createQuery(criteria).getResultList();
    }

}
package com.example.usermanager.repository.specification;

import com.example.usermanager.model.UserAccount;
import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserAccountUsernameInSpecification implements SpecificationProvider<UserAccount> {
    private static final String FILTER_KEY = "usernameIn";
    private static final String FIELD_NAME = "username";

    @Override
    public Specification<UserAccount> getSpecification(String[] usernames) {
        return ((root, query, cb) -> {
            CriteriaBuilder.In<String> predicate = cb.in(root.get(FIELD_NAME));
            for (String value : usernames) {
                predicate.value(value);
            }
            return cb.and(predicate, predicate);
        });
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}

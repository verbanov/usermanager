package com.example.usermanager.repository.specification;

import com.example.usermanager.model.UserAccount;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserAccountSpecificationManager implements SpecificationManager<UserAccount> {
    private final Map<String, SpecificationProvider<UserAccount>> providersMap;

    @Autowired
    public UserAccountSpecificationManager(List<SpecificationProvider<UserAccount>>
                                                       userAccountSpecifications) {
        this.providersMap = userAccountSpecifications.stream()
                .collect(Collectors.toMap(
                        SpecificationProvider::getFilterKey, Function.identity()));
    }

    @Override
    public Specification<UserAccount> get(String filterKey, String[] params) {
        if (!providersMap.containsKey(filterKey)) {
            throw new RuntimeException("Key " + filterKey + " is not supported for data filtering");
        }
        return providersMap.get(filterKey).getSpecification(params);
    }
}

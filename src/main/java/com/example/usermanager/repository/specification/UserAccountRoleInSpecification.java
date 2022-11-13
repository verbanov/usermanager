package com.example.usermanager.repository.specification;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.UserAccount;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserAccountRoleInSpecification implements SpecificationProvider<UserAccount> {
    private static final String FILTER_KEY = "roleIn";
    private static final String FIELD_NAME = "roleName";

    @Override
    public Specification<UserAccount> getSpecification(String[] params) {
        return ((root, query, cb) -> {
            SetJoin<UserAccount, Role> roles = root.joinSet("roles", JoinType.LEFT);
            CriteriaBuilder.In<RoleName> predicate = cb.in(roles.get(FIELD_NAME));
            RoleName roleName = null;
            for (String value : params) {
                roleName = RoleName.valueOf(value);
                predicate.value(roleName);
            }
            return cb.and(predicate, predicate);
        });
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}

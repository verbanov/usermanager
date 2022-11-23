package com.example.usermanager.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(generator = "role_id_seq",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_id_seq",
            sequenceName = "role_id_seq",
            allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleName roleName;

    public enum RoleName {
        USER,
        ADMIN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id) && roleName == role.roleName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }
}


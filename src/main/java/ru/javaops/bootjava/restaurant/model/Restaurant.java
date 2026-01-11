package ru.javaops.bootjava.restaurant.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.bootjava.common.model.NamedEntity;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant",
        uniqueConstraints =
                {@UniqueConstraint(
                        columnNames = {"name"},
                        name = "uk_restaurant_name")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("date DESC")
    private Set<Menu> menus = new LinkedHashSet<>();

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(String name) {
        super(null, name);
    }

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    boolean enabled = true;
}

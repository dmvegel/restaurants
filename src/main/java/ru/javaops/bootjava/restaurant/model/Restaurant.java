package ru.javaops.bootjava.restaurant.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.bootjava.common.model.NamedEntity;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "uk_restaurant_name")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("date DESC")
    private Set<Menu> menus = new LinkedHashSet<>();

    public Restaurant(RestaurantTO restaurantTo) {
        super(restaurantTo.getId(), restaurantTo.getName());
    }
}

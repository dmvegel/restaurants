package ru.javaops.bootjava.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.bootjava.common.model.BaseEntity;
import ru.javaops.bootjava.restaurant.to.MenuTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id"}, name = "uk_date_restaurant_id")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {
    public Menu(MenuTO menuTo, Restaurant restaurant) {
        this.date = menuTo.getDate();
        this.restaurant = restaurant;
//        this.dishes = menuTo.getDishes().stream().map(Dish::new).collect(Collectors.toSet());
        menuTo.getDishes().stream()
                .map(Dish::new)
                .forEach(dish -> {
                    dish.setMenu(this);
                    this.dishes.add(dish);
                });
    }

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @JsonIgnore
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(min = 1)
    @JsonManagedReference
    private Set<Dish> dishes = new HashSet<>();
}

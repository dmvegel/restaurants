package ru.javaops.bootjava.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.bootjava.common.model.BaseEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menu",
        uniqueConstraints =
                {@UniqueConstraint(
                        columnNames = {"restaurant_id", "menu_date"},
                        name = "uk_restaurant_date")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {
    public Menu(Integer id, LocalDate date, Set<Dish> dishes, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.dishes.addAll(dishes);
    }

    @Column(name = "menu_date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id", nullable = false)
    private Set<Dish> dishes = new HashSet<>();
}

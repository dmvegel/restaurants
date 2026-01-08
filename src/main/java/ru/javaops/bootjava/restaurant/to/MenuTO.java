package ru.javaops.bootjava.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.bootjava.common.to.BaseTo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTO extends BaseTo {
    @NotNull
    LocalDate date;

    @Size(min = 1)
    Set<DishTO> dishes;

    @JsonCreator
    public MenuTO(@JsonProperty("date") LocalDate date,
                  @JsonProperty("dishes") Set<DishTO> dishes) {
        this.date = date;
        this.dishes = dishes != null ? dishes : new HashSet<>();
    }

    public MenuTO(Integer id, LocalDate date, Set<DishTO> dishes) {
        super(id);
        this.date = date;
        this.dishes = dishes;
    }
}

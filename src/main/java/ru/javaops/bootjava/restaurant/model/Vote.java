package ru.javaops.bootjava.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.bootjava.common.model.BaseEntity;
import ru.javaops.bootjava.user.model.User;

import java.time.LocalDate;

@Entity
@Table(name = "vote",
        uniqueConstraints =
                {@UniqueConstraint(
                        columnNames = {"user_id", "vote_date"},
                        name = "uk_user_id_date")},
        indexes = {
                @Index(
                        columnList = "vote_date, restaurant_id",
                        name = "vote_date_restaurant_idx"
                )
        })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Vote extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate date;
}

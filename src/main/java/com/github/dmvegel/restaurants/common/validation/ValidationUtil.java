package com.github.dmvegel.restaurants.common.validation;

import com.github.dmvegel.restaurants.common.HasId;
import com.github.dmvegel.restaurants.common.error.IllegalRequestDataException;
import com.github.dmvegel.restaurants.restaurant.to.MenuTO;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void assureDateConsistent(MenuTO menu, LocalDate date) {
        if (!date.equals(menu.getDate())) {
            throw new IllegalRequestDataException("Date in path=" + date + " must match date in body = " + menu.getDate());
        }
    }
}
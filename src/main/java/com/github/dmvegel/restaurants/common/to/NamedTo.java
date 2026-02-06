package com.github.dmvegel.restaurants.common.to;

import com.github.dmvegel.restaurants.common.validation.NoHtml;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamedTo extends BaseTo {
    @NotBlank
    @Size(min = 2, max = 64)
    @NoHtml
    protected String name;

    public NamedTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}

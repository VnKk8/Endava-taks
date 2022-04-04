package com.endava.school.supermarket.api.model;

import com.endava.school.supermarket.api.common.ItemTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotBlank
    private String itemName;

    @NotNull
    private Double itemPrice;

    @Enumerated(EnumType.STRING)
    private ItemTypeEnum itemType;

}

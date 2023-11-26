package com.MP3.supplierservice.model;

import com.MP3.supplierservice.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "supply")
public class ResupplyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String supplyNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ResupplyOrderLine> resupplyOrderLines;
    private DeliveryStatus status;
}

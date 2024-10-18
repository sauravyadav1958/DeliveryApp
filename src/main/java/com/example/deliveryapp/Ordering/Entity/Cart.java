package com.example.deliveryapp.Ordering.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
  private Long cartId;
  private Long restaurantId;
  private Double totalAmount;
  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String foodList;
  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String addOnList;
  private double deliveryCharge;
  private double tax;
  // ******************   cascadeType meaning   *******************
  // cascadeType if applied for a table, any action(Save,Delete,etc)
  // taken on that table will also be applied to the table which is in relation.

  // ******************   mappedBy   *******************
  // mappedBy if applied for a table, that table's primary key will be used
  // as foreign key in another table which is in relation for mapping the relation.
  // If this is not present, both the tables which are in relation
  // will use other table's primary key for mapping relation which is not required since one is enough.
  // if mappedBy is present, then it is important to set the mapped prop in other table to which it is in relation before saving data in repo
  // used only for bidirectional mapping

  // ******************   FetchType   *******************
  //  FetchType.LAZY = This does not load the relationships unless you invoke it via the getter method.
  //  FetchType.EAGER = This loads all the relationships.

  // ******************   OneToOne uni-directional   *******************
  // Here no new table is created, one table uses other tables primary key for mapping relation.
  // Relation can be accessed from one side only.
  // OneToOne mapping is used inside one table only.(Here mappedBy is not used since it is unidirectional)

  // ******************   OneToOne bi-directional   *******************
  // Relation can be accessed from both sides only.
  // OneToOne mapping is used inside both the tables.
  // mappedBy is used because
  // If this is not present, both the tables which are in relation
  // will use other table's primary for mapping relation which is not required since one is enough.

  // TODO Is mappedBy payment possible here in this class (It is by default here for unidirectional mapping ?)
  // TODO what about bidirectional Mapping ? OneTOne and ManyToMany
  @OneToOne(cascade = CascadeType.ALL,
//      mappedBy = "cart",
      fetch = FetchType.EAGER
  )
  private Payment payment;
//  @OneToOne(
//      mappedBy = "cart"
//  )
//  private OrderTicket orderTicket;

}

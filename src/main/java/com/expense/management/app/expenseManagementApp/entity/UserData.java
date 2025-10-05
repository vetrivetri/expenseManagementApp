package com.expense.management.app.expenseManagementApp.entity;

import com.expense.management.app.expenseManagementApp.beans.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="USER_DATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_DTL_SEQ")
    @SequenceGenerator(name = "USER_DTL_SEQ", sequenceName = "USER_DATA_SEQ", allocationSize = 1,initialValue = 1)
    @Column(name = "USER_PK_ID")
    private Long userPkId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Column(name = "USER_OAUTH_ID")
    private String userOauthId;
    @Enumerated(EnumType.STRING)
    private Role role; // USER or ADMIN
    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private Set<GroupsEntity> groups;
    @Column(name="CREATED_DATE")
    private LocalDate createdDate;

}

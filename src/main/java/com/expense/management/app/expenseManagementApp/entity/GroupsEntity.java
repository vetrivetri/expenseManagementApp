package com.expense.management.app.expenseManagementApp.entity;

import com.expense.management.app.expenseManagementApp.beans.GroupTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="GROUPS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHAT_HDR_SEQ")
    @SequenceGenerator(name = "CHAT_HDR_SEQ", sequenceName = "CHAT_MNG_SESSION_SEQ", allocationSize = 1,initialValue = 1)
    @Column(name = "GROUP_PK_ID")
    @JsonIgnore
    private Long groupPkId;
    @Column(name = "GROUP_NAME")
    private String groupName;
    @Column(name = "GROUP_CREATED_DATE")
    @JsonIgnore
    private LocalDate groupCreatedDate;
    @Column(name = "GROUP_CREATED_BY")
    private String groupCreatedBy;
    @Column(name = "GROUP_CREATED_BY_EMAIL")
    private String groupCreatedByEmail;
    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_pk_id")
    )
    private Set<UserData> members;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseData> expenses;
    @Column(name = "GROUP_TYPE")
    private GroupTypeEnum groupType;


}

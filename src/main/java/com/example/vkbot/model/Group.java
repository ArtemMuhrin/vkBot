package com.example.vkbot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "groups_info")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "token")
    private String token;

    @Column(name = "active")
    private Boolean active;

    public Group(Integer groupId, String token, boolean active) {
        this.groupId = groupId;
        this.token = token;
        this.active = active;
    }
}

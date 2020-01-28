package com.waes.assignment.entity;

import com.waes.assignment.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Provides entity with left and right string columns
 *
 * @developer -- ilkercolakoglu
 */

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "diffs")
public class Diff extends BaseEntity {

    @Column(length = 10000)
    private String leftSide;

    @Column(length = 10000)
    private String rightSide;


    public Diff(long id) {
        setId(id);
    }
}

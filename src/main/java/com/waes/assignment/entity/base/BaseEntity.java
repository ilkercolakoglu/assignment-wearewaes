package com.waes.assignment.entity.base;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Provides base or common columns for entity
 *
 * @developer -- ilkercolakoglu
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    private long id;
}

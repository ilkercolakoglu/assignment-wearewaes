package com.waes.assignment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waes.assignment.enums.ContentStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Response API Model for getting differences between two json
 *
 * @developer -- ilkercolakoglu
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@ApiModel(description = "Contains data that represents the difference between contents")
public class DiffResponse implements Serializable {

    private ContentStatus status;

    @ApiModelProperty(notes = "positions of the differences")
    private String offsets;

    @ApiModelProperty(notes = "the content size if status is same size and different content")
    private Integer length;
}

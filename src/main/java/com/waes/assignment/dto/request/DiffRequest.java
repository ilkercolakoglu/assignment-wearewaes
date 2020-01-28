package com.waes.assignment.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Diff Request API model for creating diff
 *
 * @developer -- ilkercolakoglu
 */

@Data
@ApiModel(description = "Represent a valid request body to save a Base64 encoded JSON")
public class DiffRequest implements Serializable {

    @Size(max = 10000, message = "Please type max 2000")
    @NotEmpty(message = "Please don't leave blank")
    @ApiModelProperty(notes = "Base64 encoded JSON")
    private String base64encodedJSON;

}

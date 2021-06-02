package com.appsdeveloperblog.ws.api.resourceserver.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRest {
    private String firstName;
    private String lastName;
    private String id;
}

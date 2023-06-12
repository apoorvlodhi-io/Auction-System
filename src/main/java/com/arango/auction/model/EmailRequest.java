package com.arango.auction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private Long emailId;
    private String to;
    private String subject;
    private String text;
}

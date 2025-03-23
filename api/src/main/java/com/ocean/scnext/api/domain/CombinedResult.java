package com.ocean.scnext.api.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record CombinedResult(
    AResponse aResponse,
    BResponse bResponse,
    CResponse cResponse
) {

}

package org.demo.cp.dto;

import org.demo.cp.domain.Ticker;

public record Holding(Ticker ticker,
                      Integer quantity) {
}

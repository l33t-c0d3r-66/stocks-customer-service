package org.demo.cp.dto;

import java.util.List;

public record CustomerInformation(Integer id,
                                  String name,
                                  Integer balance,
                                  List<Holding> holdings) {

}

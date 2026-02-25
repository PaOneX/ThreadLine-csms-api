package edu.icet.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pageable {
    private int page; // zero-based page index
    private int size; // page size

    public Pageable(int page, int size) {
        this.page = page;
        this.size = size;
    }

}


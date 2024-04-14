package com.capstone.smartclassapi.domain.sort;

import org.springframework.data.domain.Sort;

public class Sorting {
    public static Sort getSorting(String sortType, String sortValue) {
        if (sortType == null || sortType.isEmpty()) {
            sortValue = "created_at";
            sortType = "desc";
        }
        Sort sorting = Sort.by(sortValue);

        sorting = sortType.equalsIgnoreCase("desc") ? sorting.descending() : sorting.ascending();

        return sorting;
    }
}

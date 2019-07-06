package com.example.demo.utils;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageHelper {

    public List<?> doPage(List<?> list, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int index = pageable.getPageNumber();

        int leftIndex = index * pageSize;
        int rightIndex = (index + 1) * pageSize;
        rightIndex = rightIndex <= list.size() ? rightIndex : list.size();

        return list.subList(leftIndex, rightIndex);
    }

}

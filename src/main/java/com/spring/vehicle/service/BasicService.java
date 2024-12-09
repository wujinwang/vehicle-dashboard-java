package com.spring.vehicle.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * @author Tom
 */
public class BasicService {

    public static <T> Page<T> createPage(List<T> content, Page<?> p) {
        return new PageImpl<>(content, p.getPageable(), p.getTotalElements());
    }

    public PageRequest buildPageRequest(int pageNumber, int pagzSize, Sort sort) {
        sort = (null == sort ? Sort.by(Direction.DESC, "id") : sort);
        return PageRequest.of(pageNumber - 1, pagzSize, sort);
    }

    public PageRequest buildPageRequestNoId(int pageNumber, int pagzSize, Sort sort) {
        return PageRequest.of(pageNumber - 1, pagzSize, sort);
    }
}

package com.sysc.workshop.product.dto.common;

import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

//raw pagination logic
//handles pagination metadata generically
// the data comes from the service layer using the repository layer

@Data
public class PagedResponseDTO<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
    private boolean firstPage;
    private boolean empty;
    private boolean hasNext;

    public PagedResponseDTO(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean lastPage,
        boolean firstPage,
        boolean empty,
        boolean hasNext
    ) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.lastPage = lastPage;
        this.firstPage = firstPage;
        this.empty = empty;
        this.hasNext = hasNext;
    }

    public static <T, R> PagedResponseDTO<R> fromPage(
        Page<T> page,
        List<R> content
    ) {
        return new PagedResponseDTO<>(
            content,
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast(),
            page.isFirst(),
            page.isEmpty(),
            page.hasNext()
        );
    }
}

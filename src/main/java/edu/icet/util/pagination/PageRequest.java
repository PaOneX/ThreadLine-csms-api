package edu.icet.util.pagination;

public class PageRequest {
    private final int page;
    private final int size;
    private final String sortBy;
    private final String direction;

    public PageRequest(int page, int size, String sortBy, String direction) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.direction = direction;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getDirection() {
        return direction;
    }
}

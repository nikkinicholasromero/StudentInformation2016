package ph.com.nikkinicholas.util.datatables;

import java.util.List;

/**
 * Created by nikkinicholas on 6/28/16.
 */
public class DataTablesRequest {
    private int draw;
    private int start;
    private int length;
    private List<Column> columns;
    private List<Order> order;
    private Search search;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "DataTablesRequest{" +
                "draw=" + draw +
                ", start=" + start +
                ", length=" + length +
                ", columns=" + columns +
                ", order=" + order +
                ", search=" + search +
                '}';
    }

    public static class Column {
        private String data;
        private String name;
        private boolean searchable;
        private boolean orderable;
        private Search search;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSearchable() {
            return searchable;
        }

        public void setSearchable(boolean searchable) {
            this.searchable = searchable;
        }

        public boolean isOrderable() {
            return orderable;
        }

        public void setOrderable(boolean orderable) {
            this.orderable = orderable;
        }

        public Search getSearch() {
            return search;
        }

        public void setSearch(Search search) {
            this.search = search;
        }

        @Override
        public String toString() {
            return "Column{" +
                    "search=" + search +
                    ", orderable=" + orderable +
                    ", searchable=" + searchable +
                    ", name='" + name + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class Order {
        private int column;
        private String dir;

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "column=" + column +
                    ", dir='" + dir + '\'' +
                    '}';
        }
    }

    public static class Search {
        private String value;
        private boolean regex;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isRegex() {
            return regex;
        }

        public void setRegex(boolean regex) {
            this.regex = regex;
        }

        @Override
        public String toString() {
            return "Search{" +
                    "value='" + value + '\'' +
                    ", regex=" + regex +
                    '}';
        }
    }
}
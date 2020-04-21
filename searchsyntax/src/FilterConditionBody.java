public class FilterConditionBody {
    private FilterParameter parameter;
    private FilterComparator comparator;
    private String val;

    public FilterConditionBody(FilterParameter parameter, FilterComparator comparator, String val) {
        this.parameter = parameter;
        this.comparator = comparator;
        this.val = val;
    }
}

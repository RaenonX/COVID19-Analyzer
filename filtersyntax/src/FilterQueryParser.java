import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilterQueryParser {
    /**
     * Parse the given string to `FilterCondition`.
     *
     * @param query string to be parsed
     * @return parsed condition
     * @throws FilterSyntaxError thrown if there's any error in the filter query
     */
    public static FilterCondition parse(String query) throws FilterSyntaxError {
        if (query.contains("(") || query.contains(")")) {
            throw new FilterSyntaxError(FilterSyntaxErrorReason.PARENTHESES_NOT_ALLOWED);
        }

        FilterCondition ret = new FilterCondition();

        Scanner scOR = new Scanner(query);
        scOR.useDelimiter("\\|");

        while (scOR.hasNext()) {
            List<FilterConditionEntity> entityAND = new ArrayList<>();

            Scanner scAND = new Scanner(scOR.next().trim());
            scAND.useDelimiter("&");

            while (scAND.hasNext()) {
                String exprStr = scAND.next().trim();
                String[] expr = exprStr.split(" ");

                if (expr.length != 3) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.INCOMPLETE_EXPRESSION, exprStr);
                }

                FilterParameter prm = FilterParameter.parse(expr[0]);
                if (prm == null) {
                    continue;
                }

                FilterComparator cmp = FilterComparator.parse(expr[1]);
                if (cmp == null) {
                    continue;
                }

                entityAND.add(new FilterConditionEntity(prm, cmp, expr[2]));
            }

            ret.pushConditionsAND(entityAND);
        }

        return ret;
    }
}

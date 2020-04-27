import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataHolder {
    private DataHolder main;

    private static DataEntry d1;
    private static DataEntry d2;
    private static DataEntry d3;
    private static DataEntry d4;
    private static DataEntry d5;

    @BeforeAll
    static void prepare() throws Exception {

        PopulationDataParser.loadUsPopFile("res/pops.csv", new StateNameConverter("res/states.csv"));

        d1 = new DataEntry(LocalDate.of(2020, Month.APRIL, 18), UnitedStates.current.getState("WI"),
                UnitedStates.current.getCounty("Dane"), 100, 10);
        d2 = new DataEntry(LocalDate.of(2020, Month.APRIL, 19), UnitedStates.current.getState("WI"),
                UnitedStates.current.getCounty("Dane"), 100, 10);
        d3 = new DataEntry(LocalDate.of(2020, Month.APRIL, 20), UnitedStates.current.getState("WI"),
                UnitedStates.current.getCounty("Dane"), 100, 10);
        d4 = new DataEntry(LocalDate.of(2020, Month.APRIL, 19), UnitedStates.current.getState("WA"),
                UnitedStates.current.getCounty("King"), 300, 40);
        d5 = new DataEntry(LocalDate.of(2020, Month.APRIL, 20), UnitedStates.current.getState("WA"),
                UnitedStates.current.getCounty("King"), 300, 40);
    }

    @BeforeEach
    void load_data() {
        main = new DataHolder(new ArrayList<DataEntry>() {
            {
                add(d1);
                add(d2);
                add(d3);
                add(d4);
                add(d5);
            }
        }.stream());
    }

    @Test
    void test_filter_1() throws FilterSyntaxError {
        FilterCondition condition = new FilterCondition();
        condition.pushConditionsAND(new ArrayList<>() {
            {
                add(new FilterConditionEntity(FilterParameter.STATE, FilterComparator.EQ, "WI"));
            }
        });
        DataHolder holder = main.filterData(condition);

        assertEquals(3, holder.getDataCount());
    }

    @Test
    void test_filter_2() throws FilterSyntaxError {
        FilterCondition condition = new FilterCondition();
        condition.pushConditionsAND(new ArrayList<>() {
            {
                add(new FilterConditionEntity(FilterParameter.STATE, FilterComparator.EQ, "WI"));
                add(new FilterConditionEntity(FilterParameter.DATE, FilterComparator.LT, "2020-04-19"));
            }
        });
        DataHolder holder = main.filterData(condition);

        assertEquals(1, holder.getDataCount());
    }
}

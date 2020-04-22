import java.util.function.Predicate;

public interface IPopulationCondition<K> {
    int getPopulation(Predicate<? super K> predicate);
}

package dao.blueprint;

import models.Comparison;

import java.util.List;

public interface IComparisonDAO {
    Comparison addComparison(Comparison comparison);

    Comparison updateComparison(Comparison comparison);

    List<Comparison> getLatestComparisons(long unixTimeStamp);

    Comparison getById(long comparisonId);
}

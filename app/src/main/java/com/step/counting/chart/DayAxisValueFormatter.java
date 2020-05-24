package com.step.counting.chart;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.step.counting.bean.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter extends ValueFormatter
{

    public List<Step> mSteps = new ArrayList<>();


    private final BarLineChartBase<?> chart;

    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value) {
        float visible = chart.getVisibleXRange();
        int index = ((int)value);
        if (index>mSteps.size()){
            index = mSteps.size()-1;
        }
        String date = mSteps.get(index).getDATE().split("月")[1];
      return date;

    }

    public List<Step> getmSteps() {
        return mSteps;
    }

    public void setmSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
    }
}

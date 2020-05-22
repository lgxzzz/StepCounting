package com.step.counting.chart;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter extends ValueFormatter
{

    private final String[] mDays = new String[]{
            "1号","2号","3号","4号","5号","6号","7号","8号","9号","10号",
            "11号","12号","13号","14号","15号","16号","17号","18号","19号","20号",
            "21号","22号","23号","24号","25号","26号","27号","28号","29号","30号",
            "31号"
    };

    private final BarLineChartBase<?> chart;

    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value) {
        float visible = chart.getVisibleXRange();
      return mDays[((int)value)];

    }


}

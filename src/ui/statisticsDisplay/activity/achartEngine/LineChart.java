package ui.statisticsDisplay.activity.achartEngine;


import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.androidui_sample_demo.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

public class LineChart extends AbstractDemoChart{
	
	Context context;
	public LineChart(Context context) {
		this.context=context;
	
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intent execute(Context context) {
		// TODO Auto-generated method stub
	    return null;  
	}

	@Override
	public View initView() {
		String[] titles = new String[] { "First" };
		double x[] = new double[] { 1, 3, 5, 7, 9, 11 };
		double y[] = new double[] { 3, 14, 5, 30, 20, 25 };
		XYSeries series = new XYSeries(titles[0]);
		series.add(x[0], y[0]);
		series.add(x[1], y[1]);
		series.add(x[2], y[2]);
		series.add(x[3], y[3]);
		series.add(x[4], y[4]);
		series.add(x[5], y[5]);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		int[] colors = new int[] { R.color.main_red };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
				PointStyle.DIAMOND };
		// 曲线图的格式，包括颜色，值的范围，点和线的形状等等
		// 都封装在XYSeriesRender对象中，再将XYSeriesRender对象封装在XYMultipleSeriesRenderer对象中
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(context.getResources().getColor(R.color.main_red));
		r.setPointStyle(styles[0]);
		r.setLineWidth(3);
		r.setPointStrokeWidth(3);
		r.setFillPoints(false);
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(r);
		renderer.setPointSize(5);
		renderer.setGridColor(Color.WHITE);
		renderer.setXAxisMin(0);
		renderer.setXAxisMax(12);
		renderer.setYAxisMax(35);
		renderer.setYAxisMin(0);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setAxesColor(context.getResources().getColor(R.color.main_bg));
		renderer.setMarginsColor(Color.WHITE);
		renderer.setPointSize(200);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		renderer.setAxisTitleTextSize(30); // 设置轴标题文字的大小
		renderer.setChartTitleTextSize(30);// 设置整个图表标题文字大小
		renderer.setLabelsTextSize(5);// 设置刻度显示文字的大小(XY轴都会被设置)
		renderer.setLegendTextSize(25);// 图例文字大小
		// setChartSettings(renderer, "Line Chart Demo", "X", "Y", 0, 12, 0, 35
		// , R.color.main_bg, R.color.main_bg);
		View chart = ChartFactory.getLineChartView(context, dataset, renderer);
		return chart;
	}

	@Override
	public View initView(double[] heartRate) {
		// TODO Auto-generated method stub
		String[] titles = new String[] { "First" };
		double x[] = new double[] { 1, 3, 5, 7, 9, 11 };
		double y[] = new double[] { 3, 14, 5, 30, 20, 25 };
		XYSeries series = new XYSeries(titles[0]);
		series.add(x[0], y[0]);
		series.add(x[1], y[1]);
		series.add(x[2], y[2]);
		series.add(x[3], y[3]);
		series.add(x[4], y[4]);
		series.add(x[5], y[5]);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		int[] colors = new int[] { R.color.main_red };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
				PointStyle.DIAMOND };
		// 曲线图的格式，包括颜色，值的范围，点和线的形状等等
		// 都封装在XYSeriesRender对象中，再将XYSeriesRender对象封装在XYMultipleSeriesRenderer对象中
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(context.getResources().getColor(R.color.main_red));
		r.setPointStyle(styles[0]);
		r.setLineWidth(3);
		r.setPointStrokeWidth(3);
		r.setFillPoints(false);
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(r);

		renderer.setPointSize(5);
		renderer.setGridColor(Color.WHITE);
		renderer.setXAxisMin(0);
		renderer.setXAxisMax(12);
		renderer.setYAxisMax(35);
		renderer.setYAxisMin(0);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setAxesColor(context.getResources().getColor(R.color.main_bg));
		renderer.setAxisTitleTextSize(30); // 设置轴标题文字的大小
		renderer.setChartTitleTextSize(30);// 设置整个图表标题文字大小
		renderer.setLabelsTextSize(5);// 设置刻度显示文字的大小(XY轴都会被设置)
		renderer.setLegendTextSize(25);// 图例文字大小
		
		renderer.setShowGrid(true);// 是否显示网格
		renderer.setMarginsColor(Color.rgb(220, 178, 167));
		renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
		renderer.setBackgroundColor(Color.rgb(220, 178, 167));// 设置背景色RGB(221,198,194)
		renderer.setShowLabels(true);
		
		// setChartSettings(renderer, "Line Chart Demo", "X", "Y", 0, 12, 0, 35
		// , R.color.main_bg, R.color.main_bg);
		View chart = ChartFactory.getLineChartView(context, dataset, renderer);
		return chart;
	}

	@Override
	public View initView(double[] heartRate, double[] speed) {
		// TODO Auto-generated method stub
		return null;
	}



}

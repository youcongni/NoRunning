package ui.statisticsDisplay.activity.achartEngine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

public class SpeedControlChart extends AbstractDemoChart {

	Context context;

	public SpeedControlChart(Context context) {
		this.context = context;
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
		return null;
	}

	@Override
	public View initView(double[] speed) {
		String[] titles = new String[] { "速度值" };// 图例
		List<double[]> values = new ArrayList<double[]>();
		for(int i=0;i<speed.length-1;i++){
			speed[i]=speed[i+1];
		}
		double[] speedCopy = Arrays.copyOf(speed, speed.length);
		
		values.add(speed);// 柱子的数值
		Arrays.sort(speedCopy);
		
		int[] colors = new int[] { Color.rgb(118, 123, 138) };// 两种柱子的颜色
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);// 调用AbstractDemoChart中的方法构建renderer.
		setChartSettings(renderer, " ", "时间(min)", "速度(m/s)", 0, 11, 0, 7, Color.BLACK,
				Color.BLACK);// 调用AbstractDemoChart中的方法设置renderer的一些属性.
		renderer.getSeriesRendererAt(0).setDisplayChartValues(false);// 设置柱子上是否显示数量值
		// renderer.getSeriesRendererAt(1).setDisplayChartValues(true);//设置柱子上是否显示数量值
		/**设置XY轴参数*/
		renderer.setXLabels(12);// X轴的点个数
		renderer.setYLabels(10);// Y轴的点个数
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setXLabelsAlign(Align.LEFT);// 刻度线与X轴坐标文字左侧对齐
		renderer.setYLabelsAlign(Align.RIGHT);// Y轴与Y轴坐标文字左对齐
		renderer.setYLabelsPadding(15);// 设置数字和坐标轴的位置
		renderer.setXLabelsPadding(10);// 设置数字和坐标轴的位置
		/**放大拖动等一些参数设置*/
		renderer.setPanEnabled(true, true);// 允许左右拖动,允许上下拖动.
		renderer.setZoomEnabled(false);
		renderer.setPanLimits(new double[] { 0, speed.length+2, 0, speedCopy[speedCopy.length-1]+1 }); // 设置拖动时X轴Y轴允许的最大值最小值.
		renderer.setZoomLimits(new double[] { 0, speed.length+2, 0, speedCopy[speedCopy.length-1]+1 });// 设置放大缩小时X轴Y轴允许的最大最小值.
		renderer.setZoomRate(1.3f);// 放大的倍率
		renderer.setBarSpacing(0.7f);// 柱子间宽度
		/**背景色，图标位置，字体大小参数设置*/
		renderer.setMarginsColor(Color.rgb(220, 178, 167));
		renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
		renderer.setBackgroundColor(Color.rgb(220, 178, 167));// 设置背景色
		renderer.setMargins(new int[] { 50, 70, 50, 10 });// 设置图表的外边框(上/左/下/右)
		renderer.setPointSize(200);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		renderer.setAxisTitleTextSize(30); // 设置轴标题文字的大小
		renderer.setChartTitleTextSize(30);// 设置整个图表标题文字大小
		renderer.setLabelsTextSize(20);// 设置刻度显示文字的大小(XY轴都会被设置)25
		renderer.setLegendTextSize(25);// 图例文字大小
		renderer.setFitLegend(true);
		View view = ChartFactory.getBarChartView(context,
				buildBarDataset(titles, values), renderer, Type.DEFAULT);
		return view;
	}

	@Override
	public View initView(double[] heartRate, double[] speed) {
		// TODO Auto-generated method stub
		return null;
	}

}

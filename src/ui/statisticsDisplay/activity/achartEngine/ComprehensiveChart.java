package ui.statisticsDisplay.activity.achartEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

public class ComprehensiveChart extends AbstractDemoChart {

	Context context;

	public ComprehensiveChart(Context context) {
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
	public View initView(double[] heartRate) {
		String[] titles = new String[] { "低于", "高于","建议"  };// 图例
		List<double[]> x = new ArrayList<double[]>();
		List<double[]> values = new ArrayList<double[]>();// 曲线数据
		double[] xNum = new double[heartRate.length];// 横坐标个数
		double[] ceiling = new double[heartRate.length];// 上限
		double[] offline = new double[heartRate.length];// 下线
		double[] heartRateCopy = Arrays.copyOf(heartRate, heartRate.length);

		for (int i = 0; i < titles.length; i++) {// 根据心率多少显示X轴上面的值
			for (int j = 0; j < heartRate.length; j++) {
				xNum[j] = j + 1;
			}
			x.add(xNum);
		}
		for (int i = 0; i < heartRate.length; i++) {
			ceiling[i] = 140;
			offline[i] = 120;
		}
		values.add(ceiling);// 上限
		values.add(offline);// 下线
		values.add(heartRate);// 序列1中点的y坐标
		Arrays.sort(heartRateCopy);
		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN };// 每个序列的颜色设置
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
				PointStyle.POINT, PointStyle.POINT };// 每个序列中点的形状设置
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);// 调用AbstractDemoChart中的方法设置
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);// 设置图上的点为实心
		}

		setChartSettings(renderer, "", "速度(m/s)", "心率(times/min)", 0, 10,
				80, 150, Color.BLACK, Color.BLACK);// 调用AbstractDemoChart中的方法设置图表的renderer属性.

		renderer.setXLabels(13);// 设置x轴显示12个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		renderer.setYLabels(13);// 设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		renderer.setXLabelsAlign(Align.RIGHT);// 刻度线与刻度标注之间的相对位置关系
		renderer.setYLabelsAlign(Align.CENTER);// 刻度线与刻度标注之间的相对位置关系
		renderer.setYLabelsPadding(25);// 设置数字和坐标轴的位置
		renderer.setXLabelsPadding(10);// 设置数字和坐标轴的位置
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);

		renderer.setShowGrid(true);// 是否显示网格
		renderer.setMarginsColor(Color.rgb(220, 178, 167));
		renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
		renderer.setBackgroundColor(Color.rgb(220, 178, 167));// 设置背景色RGB(221,198,194)
		renderer.setShowLabels(true);

		renderer.setMargins(new int[] { 70, 70, 50, 50 });// 设置图表的外边框(上/左/下/右)
		renderer.setPointSize(200);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		renderer.setAxisTitleTextSize(30); // 设置轴标题文字的大小
		renderer.setChartTitleTextSize(30);// 设置整个图表标题文字大小
		renderer.setLabelsTextSize(20);// 设置刻度显示文字的大小(XY轴都会被设置)
		renderer.setLegendTextSize(25);// 图例文字大小

		renderer.setPanLimits(new double[] { 0, heartRate.length,
				heartRateCopy[0] - 10,
				heartRateCopy[heartRateCopy.length - 1] + 10 }); // 设置拖动时X轴Y轴允许的最大值最小值.
		renderer.setZoomLimits(new double[] { 0, heartRate.length,
				heartRateCopy[0] - 10,
				heartRateCopy[heartRateCopy.length - 1] + 10 });// 设置放大缩小时X轴Y轴允许的最大最小值.

		View chart = ChartFactory.getCubeLineChartView(context,
				buildDataset(titles, x, values), renderer, 0.4f);
		return chart;
	}

	@Override
	public View initView(double[] heartRate, double[] speed) {
		String[] titles = new String[] { "综合"  };// 图例
//		String[] titles = new String[] { "低于", "高于","建议"  };// 图例
		List<double[]> x = new ArrayList<double[]>();
		List<double[]> values = new ArrayList<double[]>();// 曲线数据
		double[] xNum = new double[heartRate.length];// 横坐标个数
//		double[] ceiling = new double[heartRate.length];// 上限
//		double[] offline = new double[heartRate.length];// 下线
		double[] heartRateCopy = Arrays.copyOf(heartRate, heartRate.length);

		for (int i = 0; i < titles.length; i++) {// 根据心率多少显示X轴上面的值
			for (int j = 0; j < heartRate.length; j++) {
				xNum[j] = speed[j];
			}
			x.add(xNum);
		}
//		for (int i = 0; i < heartRate.length; i++) {
//			ceiling[i] = 140;
//			offline[i] = 120;
//		}
//		values.add(ceiling);// 上限
//		values.add(offline);// 下线
		values.add(heartRate);// 序列1中点的y坐标
		Arrays.sort(heartRateCopy);
		int[] colors = new int[] { Color.BLUE};// 每个序列的颜色设置
//		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN };// 每个序列的颜色设置
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT};// 每个序列中点的形状设置
//		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
//				PointStyle.POINT, PointStyle.POINT };// 每个序列中点的形状设置
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);// 调用AbstractDemoChart中的方法设置
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);// 设置图上的点为实心
		}

		setChartSettings(renderer, "", "速度(m/s)", "心率(times/min)", 0, speed[speed.length -1],
				heartRateCopy[0]-10, heartRateCopy[heartRateCopy.length-1]+10, Color.BLACK, Color.BLACK);// 调用AbstractDemoChart中的方法设置图表的renderer属性.

		renderer.setXLabels(13);// 设置x轴显示12个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		renderer.setYLabels(13);// 设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		renderer.setXLabelsAlign(Align.RIGHT);// 刻度线与刻度标注之间的相对位置关系
		renderer.setYLabelsAlign(Align.CENTER);// 刻度线与刻度标注之间的相对位置关系
		renderer.setYLabelsPadding(25);// 设置数字和坐标轴的位置
		renderer.setXLabelsPadding(10);// 设置数字和坐标轴的位置
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);

		renderer.setShowGrid(true);// 是否显示网格
		renderer.setMarginsColor(Color.rgb(220, 178, 167));
		renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
		renderer.setBackgroundColor(Color.rgb(220, 178, 167));// 设置背景色RGB(221,198,194)
		renderer.setShowLabels(true);

		renderer.setMargins(new int[] { 70, 70, 50, 50 });// 设置图表的外边框(上/左/下/右)
		renderer.setPointSize(5000);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		renderer.setAxisTitleTextSize(30); // 设置轴标题文字的大小
		renderer.setChartTitleTextSize(30);// 设置整个图表标题文字大小
		renderer.setLabelsTextSize(20);// 设置刻度显示文字的大小(XY轴都会被设置)
		renderer.setLegendTextSize(25);// 图例文字大小
		renderer.setFitLegend(true);
		renderer.setPanLimits(new double[] { 0, speed[speed.length -1]+0.5,
				heartRateCopy[0] - 10,
				heartRateCopy[heartRateCopy.length - 1] + 10 }); // 设置拖动时X轴Y轴允许的最大值最小值.
		renderer.setZoomLimits(new double[] { 0, speed[speed.length -1]+0.5,
				heartRateCopy[0] - 10,
				heartRateCopy[heartRateCopy.length - 1] + 10 });// 设置放大缩小时X轴Y轴允许的最大最小值.

		View chart = ChartFactory.getCubeLineChartView(context,
				buildDataset(titles, x, values), renderer, 0.4f);
		return chart;
	}

}

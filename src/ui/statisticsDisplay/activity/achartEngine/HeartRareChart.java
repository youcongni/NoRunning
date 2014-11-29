package ui.statisticsDisplay.activity.achartEngine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

public class HeartRareChart extends AbstractDemoChart {

	Context context;

	public HeartRareChart(Context context) {
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
		// TODO Auto-generated method stub
		String[] titles = new String[] { "心率值" };
		List<double[]> x = new ArrayList<double[]>();
		for (int i = 0; i < titles.length; i++) {
			x.add(new double[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 });
		}
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 136, 134, 139, 138, 130, 124, 120, 138, 112,
				134, 121, 128, 118, 132, 130, 124, 127, 124, 131, 137, 140,
				128, 134, 136, 118, 120, 135, 121, 142, 132, 117, 132, 117,
				128, 127, 144, 131, 123, 135, 134, 124, 124, 114, 133, 128,
				121, 113, 136, 131, 124 });

		// int[] colors = new int[] { Color.BLUE };
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.rgb(255, 0, 0));
		r.setLineWidth(3);
		// PointStyle[] styles = new PointStyle[] { PointStyle.DIAMOND };
		// r.setPointStyle(styles[0]);
		// r.setPointStrokeWidth(3);
		// r.setFillPoints(false);
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(r);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer seriesRenderer = ((XYSeriesRenderer) renderer
					.getSeriesRendererAt(i));
			seriesRenderer.setDisplayChartValues(true);
		}

		setChartSettings(renderer, "", "时间", "心率", 0, 12.5, 50, 160,
				Color.BLACK, Color.BLACK);
		// renderer.setLabelsTextSize(20);
		renderer.setXLabels(10);
		renderer.setYLabels(10);
		renderer.setBarSpacing(1);// 设置间距
		renderer.setChartValuesTextSize(20);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		// renderer.setZoomButtonsVisible(true);
		renderer.setPanLimits(new double[] { 0, 40, 60, 170 });
		renderer.setZoomLimits(new double[] { 0, 40, 60, 170 });
		// renderer.setGridColor(Color.WHITE);
		// renderer.setZoomButtonsVisible(true);//是否显示放大缩小按钮

		renderer.setMarginsColor(Color.argb(5, 222, 178, 167));
		renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
		renderer.setBackgroundColor(Color.argb(5, 222, 178, 167));// 设置背景色

		renderer.setMargins(new int[] { 50, 70, 50, 10 });// 设置图表的外边框(上/左/下/右)
		renderer.setPointSize(1000);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		renderer.setAxisTitleTextSize(38); // 设置轴标题文字的大小
		renderer.setChartTitleTextSize(38);// ?设置整个图表标题文字大小
		renderer.setLabelsTextSize(38);// 设置刻度显示文字的大小(XY轴都会被设置)
		renderer.setLegendTextSize(38);// 图例文字大小

		XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
		XYSeries series = dataset.getSeriesAt(0);
		// series.addAnnotation("Vacation", 6, 30);
		View chart = ChartFactory.getLineChartView(context, dataset, renderer);
		return chart;
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
			ceiling[i] = 110;
			offline[i] = 130;
		}
		values.add(ceiling);// 上限
		values.add(offline);// 下线
		values.add(heartRate);// 序列1中点的y坐标
		Arrays.sort(heartRateCopy);
		int[] colors = new int[] { Color.WHITE, Color.RED, Color.BLACK };// 每个序列的颜色设置
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
				PointStyle.POINT, PointStyle.POINT };// 每个序列中点的形状设置
//		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);// 调用AbstractDemoChart中的方法设置
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);// 调用AbstractDemoChart中的方法设置
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);// 设置图上的点为实心
		}

		setChartSettings(renderer, "", "时间(min)", "心率(times/min)", 0, 10,
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
		renderer.setPointSize(200);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		renderer.setAxisTitleTextSize(30); // 设置轴标题文字的大小
		renderer.setChartTitleTextSize(30);// 设置整个图表标题文字大小
		renderer.setLabelsTextSize(20);// 设置刻度显示文字的大小(XY轴都会被设置)
		renderer.setLegendTextSize(25);// 图例文字大小
		renderer.setFitLegend(true);
		renderer.setPanLimits(new double[] { 0, heartRate.length,
				heartRateCopy[0] - 10,
				heartRateCopy[heartRateCopy.length - 1] + 10 }); // 设置拖动时X轴Y轴允许的最大值最小值.
		renderer.setZoomLimits(new double[] { 0, heartRate.length,
				heartRateCopy[0] - 10,
				heartRateCopy[heartRateCopy.length - 1] + 10 });// 设置放大缩小时X轴Y轴允许的最大最小值.

		View chart = ChartFactory.getCubeLineChartView(context,
				buildDataset(titles, x, values), renderer, 0.4f);
		return chart;
//		String[] titles = new String[] { "心率值" };
//		List<double[]> x = new ArrayList<double[]>();
//		double[] num = new double[heartRate.length];
//		List<double[]> values = new ArrayList<double[]>();
//		double[] heartRateCopy = Arrays.copyOf(heartRate, heartRate.length);
//
//		for (int i = 0; i < titles.length; i++) {
//			for (int j = 0; j < heartRate.length; j++) {
//				num[j] = j + 1;
//			}
//			x.add(num);
//		}
//		values.add(heartRate);
//		Arrays.sort(heartRateCopy);
//
//		XYSeriesRenderer r = new XYSeriesRenderer();
//		r.setColor(Color.rgb(200, 10, 20));
//		r.setLineWidth(2);
//
//		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
//		renderer.addSeriesRenderer(r);
//		int length = renderer.getSeriesRendererCount();
//		for (int i = 0; i < length; i++) {
//			SimpleSeriesRenderer seriesRenderer = ((XYSeriesRenderer) renderer
//					.getSeriesRendererAt(i));
//			// seriesRenderer.setDisplayChartValues(true);是否显示线上的点
//		}
//
//		setChartSettings(renderer, "", "时间(min)", "心率(times/min)", 0, 12.5, 70,
//				130, Color.BLACK, Color.BLACK);
//
//		renderer.setXLabels(13);
//		renderer.setYLabels(13);
//		renderer.setBarSpacing(1);// 设置间距
//		// renderer.setChartValuesTextSize(20);
//		renderer.setXLabelsAlign(Align.RIGHT);// 刻度线与刻度标注之间的相对位置关系
//		renderer.setYLabelsAlign(Align.CENTER);// 刻度线与刻度标注之间的相对位置关系
//		renderer.setYLabelsPadding(25);// 设置数字和坐标轴的位置
//		renderer.setXLabelsPadding(10);// 设置数字和坐标轴的位置
//		renderer.setXLabelsColor(Color.BLACK);
//		renderer.setYLabelsColor(0, Color.BLACK);
//		renderer.setPanLimits(new double[] { 0, heartRate.length,
//				heartRateCopy[0] - 10,
//				heartRateCopy[heartRateCopy.length - 1] + 10 });
//		renderer.setZoomLimits(new double[] { 0, heartRate.length,
//				heartRateCopy[0] - 10,
//				heartRateCopy[heartRateCopy.length - 1] + 10 });
//
//		renderer.setShowGrid(true);// 是否显示网格
//		renderer.setMarginsColor(Color.rgb(220, 178, 167));
//		renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
//		renderer.setBackgroundColor(Color.rgb(220, 178, 167));// 设置背景色RGB(221,198,194)
//		renderer.setShowLabels(true);
//
//		renderer.setMargins(new int[] { 50, 70, 50, 10 });// 设置图表的外边框(上/左/下/右)
//		renderer.setPointSize(200);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
//		renderer.setAxisTitleTextSize(30); // 设置轴标题文字的大小
//		renderer.setChartTitleTextSize(30);// 设置整个图表标题文字大小
//		renderer.setLabelsTextSize(20);// 设置刻度显示文字的大小(XY轴都会被设置)
//		renderer.setLegendTextSize(25);// 图例文字大小
//		renderer.setFitLegend(true);
//		XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
//		XYSeries series = dataset.getSeriesAt(0);
//		// series.addAnnotation("Vacation", 6, 30);
//		View chart = ChartFactory.getCubeLineChartView(context, dataset,
//				renderer, 0.4f);
//		return chart;
	}

	@Override
	public View initView(double[] heartRate, double[] speed) {
		// TODO Auto-generated method stub
		return null;
	}
}

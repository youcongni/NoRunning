package domain.dataCollect;

public class CurrentSportData {
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 累计卡路里
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private double totalCalorie;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 距离
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private double distance;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 实时心率
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Integer currentHeartRate;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 累计时间
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String totalTime;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 实时速度
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private double currentSpeed;

	public CurrentSportData() {
		totalCalorie = -1;// 初始化卡路里为0
		currentSpeed = -1;// 初始化速度为0
		currentHeartRate = -1;// 初始化速度为0
		distance = -1;// 初始化距离为0
	}

	// 清空数据
	public void ClearCurrentSportData(CurrentSportData currentSportData) {
		currentSportData.setCurrentHeartRate(-1);
		currentSportData.setCurrentSpeed(-1);
		currentSportData.setDistance(-1);
		currentSportData.setTotalCalorie(-1);
	}

	// 拷贝数据
	public CurrentSportData CopyCurrentSportData(
			CurrentSportData currentSportData) {
		CurrentSportData c = new CurrentSportData();

		// 拷贝数据
		c.setCurrentHeartRate(currentSportData.getCurrentHeartRate());
		c.setCurrentSpeed(currentSportData.getCurrentSpeed());
		c.setDistance(currentSportData.getDistance());
		c.setTotalCalorie(currentSportData.getTotalCalorie());

		return c;
	}

	public double getTotalCalorie() {
		return totalCalorie;
	}

	public void setTotalCalorie(double totalCalorie) {
		this.totalCalorie = totalCalorie;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Integer getCurrentHeartRate() {
		return currentHeartRate;
	}

	public void setCurrentHeartRate(Integer currentHeartRate) {
		this.currentHeartRate = currentHeartRate;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

}

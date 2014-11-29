package domain.dataCollect;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "T_MinuteSportData")
public class MinuteSportData {
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 编号
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@DatabaseField(generatedId = true)
	private int id;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 采集的第几次
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@DatabaseField(canBeNull = false)
	private Integer number;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@DatabaseField(canBeNull = false)
	private double speed;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@DatabaseField(canBeNull = false)
	private Integer heartRate;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@DatabaseField(canBeNull = false)
	private Date collectTime;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@DatabaseField(canBeNull = false, foreign = true)
	private OneSport oneSport;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public OneSport getOneSport() {
		return oneSport;
	}

	public void setOneSport(OneSport oneSport) {
		this.oneSport = oneSport;
	}

}

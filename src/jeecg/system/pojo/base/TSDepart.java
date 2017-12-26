package jeecg.system.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 部门机构表
 */
@Entity
@Table(name = "t_s_depart")
public class TSDepart extends IdEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TSDepart TSPDepart;//上级部门
	private String departname;//部门名称
	private String code;//行政编码
	private java.lang.String areacode2;//所属区县
	private String grade;//部门级别
	private String description;//部门描述
	private String address;//部门地址
	private List<TSDepart> TSDeparts = new ArrayList<TSDepart>();//下属部门

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentdepartid")
	public TSDepart getTSPDepart() {
		return this.TSPDepart;
	}

	public void setTSPDepart(TSDepart TSPDepart) {
		this.TSPDepart = TSPDepart;
	}

	@Column(name = "departname", nullable = false, length = 100)
	public String getDepartname() {
		return this.departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSPDepart")
	public List<TSDepart> getTSDeparts() {
		return TSDeparts;
	}

	public void setTSDeparts(List<TSDepart> tSDeparts) {
		TSDeparts = tSDeparts;
	}
	
	@Column(name = "GRADE", length = 1)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name="address", length = 300)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="code",length=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属区县
	 */
	@Column(name ="AREACODE2",nullable=true,length=6)
	public java.lang.String getAreacode2() {
		return areacode2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属区县
	 */
	public void setAreacode2(java.lang.String areacode2) {
		this.areacode2 = areacode2;
	}
}
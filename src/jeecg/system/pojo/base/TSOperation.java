package jeecg.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 权限操作表
 */
@Entity
@Table(name = "t_s_operation")
public class TSOperation extends IdEntity implements java.io.Serializable {
	private String operationname;
	private String operationcode;
	private String operationicon;
	private Short status;
	private TSIcon TSIcon = new TSIcon();
	private String iconId;//菜单图标
	private TSFunction TSFunction = new TSFunction();

	@Column(name = "iconid", nullable = true, length = 255)
	public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	@Column(name = "operationname", length = 50)
	public String getOperationname() {
		return this.operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

	@Column(name = "operationcode", length = 50)
	public String getOperationcode() {
		return this.operationcode;
	}

	public void setOperationcode(String operationcode) {
		this.operationcode = operationcode;
	}

	@Column(name = "operationicon", length = 100)
	public String getOperationicon() {
		return this.operationicon;
	}

	public void setOperationicon(String operationicon) {
		this.operationicon = operationicon;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Transient
	public TSIcon getTSIcon() {
		return TSIcon;
	}

	public void setTSIcon(TSIcon tSIcon) {
		TSIcon = tSIcon;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public TSFunction getTSFunction() {
		return TSFunction;
	}

	public void setTSFunction(TSFunction tSFunction) {
		TSFunction = tSFunction;
	}
}
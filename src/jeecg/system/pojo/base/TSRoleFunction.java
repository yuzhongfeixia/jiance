package jeecg.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.common.entity.IdEntity;

/**
 * TRoleFunction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_s_role_function")
public class TSRoleFunction extends IdEntity implements java.io.Serializable {
	private TSFunction TSFunction;
	private TSRole TSRole;
	private String operation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public TSFunction getTSFunction() {
		return this.TSFunction;
	}

	public void setTSFunction(TSFunction TSFunction) {
		this.TSFunction = TSFunction;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	public TSRole getTSRole() {
		return this.TSRole;
	}

	public void setTSRole(TSRole TSRole) {
		this.TSRole = TSRole;
	}

	@Column(name = "operation", length = 1000)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
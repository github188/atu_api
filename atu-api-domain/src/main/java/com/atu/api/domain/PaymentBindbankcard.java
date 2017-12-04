/**
 * 
 */
package com.atu.api.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 绑定银行卡
 * @author 77SK742-A
 *
 */
public class PaymentBindbankcard  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer mer_cust_id;
	
	private String vender_user_id;
	
	private String usr_busi_agreement_id;
	
	private String usr_pay_agreement_id;
	
	private String mobile;
	
	private String bank;
	
	private String bank_ac;
	
	private String branch;
	
	private String branch_ac;
	
	private String bank_ac_last4;
	
	private String branch_ac_last4;
	
	private String identityType;
	
	private String identityCode;
	
	private String cardHolder;
	
	private Date createdate;
	
	private Date updatedate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMer_cust_id() {
		return mer_cust_id;
	}

	public void setMer_cust_id(Integer mer_cust_id) {
		this.mer_cust_id = mer_cust_id;
	}

	public String getVender_user_id() {
		return vender_user_id;
	}

	public void setVender_user_id(String vender_user_id) {
		this.vender_user_id = vender_user_id;
	}

	public String getUsr_busi_agreement_id() {
		return usr_busi_agreement_id;
	}

	public void setUsr_busi_agreement_id(String usr_busi_agreement_id) {
		this.usr_busi_agreement_id = usr_busi_agreement_id;
	}

	public String getUsr_pay_agreement_id() {
		return usr_pay_agreement_id;
	}

	public void setUsr_pay_agreement_id(String usr_pay_agreement_id) {
		this.usr_pay_agreement_id = usr_pay_agreement_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBank_ac() {
		return bank_ac;
	}

	public void setBank_ac(String bank_ac) {
		this.bank_ac = bank_ac;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBranch_ac() {
		return branch_ac;
	}

	public void setBranch_ac(String branch_ac) {
		this.branch_ac = branch_ac;
	}

	public String getBank_ac_last4() {
		return bank_ac_last4;
	}

	public void setBank_ac_last4(String bank_ac_last4) {
		this.bank_ac_last4 = bank_ac_last4;
	}

	public String getBranch_ac_last4() {
		return branch_ac_last4;
	}

	public void setBranch_ac_last4(String branch_ac_last4) {
		this.branch_ac_last4 = branch_ac_last4;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	
}

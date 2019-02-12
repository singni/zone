package com.zone.bos.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 工作单
 */
public class WorkOrderManage implements java.io.Serializable {
	private String id; // 编号
	private String arrivecity; // 到达城市
	private String product; // 货物
	private Integer num; // 数量
	private Double weight; // 重量
	private String floadreqr; // 配置要求 （1 无、2 禁航、 3禁铁路）
	private String managerCheck = "0";//是否审核 1：已审核 0：未审核
	private String start = "0";//对应流程是否已经启动 0：未启动 1：已启动
	private String prodtimelimit;
	private String prodtype;
	private String sendername;
	private String senderphone;
	private String senderaddr;
	private String receivername;
	private String receiverphone;
	private String receiveraddr;
	private Integer feeitemnum;
	private Double actlweit;
	private String vol;
	private Date updatetime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArrivecity() {
		return arrivecity;
	}
	public void setArrivecity(String arrivecity) {
		this.arrivecity = arrivecity;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getFloadreqr() {
		return floadreqr;
	}
	public void setFloadreqr(String floadreqr) {
		this.floadreqr = floadreqr;
	}
	public String getProdtimelimit() {
		return prodtimelimit;
	}
	public void setProdtimelimit(String prodtimelimit) {
		this.prodtimelimit = prodtimelimit;
	}
	public String getProdtype() {
		return prodtype;
	}
	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public String getSenderphone() {
		return senderphone;
	}
	public void setSenderphone(String senderphone) {
		this.senderphone = senderphone;
	}
	public String getSenderaddr() {
		return senderaddr;
	}
	public void setSenderaddr(String senderaddr) {
		this.senderaddr = senderaddr;
	}
	public String getReceivername() {
		return receivername;
	}
	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}
	public String getReceiverphone() {
		return receiverphone;
	}
	public void setReceiverphone(String receiverphone) {
		this.receiverphone = receiverphone;
	}
	public String getReceiveraddr() {
		return receiveraddr;
	}
	public void setReceiveraddr(String receiveraddr) {
		this.receiveraddr = receiveraddr;
	}
	public Integer getFeeitemnum() {
		return feeitemnum;
	}
	public void setFeeitemnum(Integer feeitemnum) {
		this.feeitemnum = feeitemnum;
	}
	public Double getActlweit() {
		return actlweit;
	}
	public void setActlweit(Double actlweit) {
		this.actlweit = actlweit;
	}
	public String getVol() {
		return vol;
	}
	public void setVol(String vol) {
		this.vol = vol;
	}
	public String getManagerCheck() {
		return managerCheck;
	}
	public void setManagerCheck(String managerCheck) {
		this.managerCheck = managerCheck;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String toString() {
		return "工作单信息 [编号=" + id + ", 货物名称=" + product
				+ ", 货物重量=" + weight + ", 收货人="
				+ receivername + ", 收货人电话=" + receiverphone + "]";
	}
}
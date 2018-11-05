package cn.ffcs.uoo.rabbitmq.manage.vo;

import java.io.Serializable;

public class SystemQueueVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String queueName;

    private String systemName;

    private String doubleName;

    private String chargePerson;

    private String chargeContact;
    
    private String dateTime;
    
    private String key;
    
    public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getDoubleName() {
		return doubleName;
	}

	public void setDoubleName(String doubleName) {
		this.doubleName = doubleName;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public String getChargeContact() {
		return chargeContact;
	}

	public void setChargeContact(String chargeContact) {
		this.chargeContact = chargeContact;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SystemQueueVo [queueName=" + queueName + ", systemName=" + systemName + ", doubleName=" + doubleName
				+ ", chargePerson=" + chargePerson + ", chargeContact=" + chargeContact + ", dateTime=" + dateTime
				+ ", key=" + key + "]";
	}
}
package cn.ffcs.uoo.rabbitmq.manage.constant;

public enum QueueConstant {
	
	valid("1000"),unvalid("1100");
	
	private String value;
	
	private QueueConstant(String value) {
		this.setValue(value);
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
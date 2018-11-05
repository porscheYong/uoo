package cn.ffcs.uoo.message.server.constant;

public enum StatusConstant {
	success("1000"),validateFail("1100"),handleFail("1200");
	
	private String value;
	
	private StatusConstant(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
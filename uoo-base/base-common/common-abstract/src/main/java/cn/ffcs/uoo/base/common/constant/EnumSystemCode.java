package cn.ffcs.uoo.base.common.constant;


public enum EnumSystemCode {
	OPERATION_SUCCESS("000", "操作成功"),
	DATAVERIFY_EXCEPTION("001","数据验证失败");

	private String code;
	private String msg;

	private EnumSystemCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "{\"code\":\"" + this.code + "\",\"msg\":\"" + this.msg + "\"}";
	}
}

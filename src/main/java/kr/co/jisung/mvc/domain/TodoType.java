package kr.co.jisung.mvc.domain;

public enum TodoType implements BaseCodeLabelEnum{
	ACTIVE("할 일"),
	COMPLETED("완료된 일"),
	DUE_DATE("기한이 지난 일")
	;
	private String code;
	private String label;
	
	/*
	 * name() : 열거 객체의 문자열 리턴
	 */
	private TodoType(String label) {
		this.code = name(); 
		this.label = label;
	}
	@Override
	public String code() {
		return code;
	}

	@Override
	public String label() {
		return label;
	}

}

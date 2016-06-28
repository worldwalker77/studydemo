package ${groupId}.domain.result;

public enum ResultCode {
	SUCCESS(0, "success", "success");
	public int code;
	public String insideDesc;
	public String returnDesc;
	
	private ResultCode(int code,String insideDesc,String returnDesc){
		this.code = code;
		this.insideDesc = insideDesc;
		this.returnDesc = returnDesc;
	}
	
	public String getInsideDesc(int code){
		for(ResultCode result : ResultCode.values()){
			if (code == result.code) {
				return result.insideDesc;
			}
		}
		return null;
	}
	
	public static String getReturnDesc(int code){
		for(ResultCode result : ResultCode.values()){
			if (code == result.code) {
				return result.returnDesc;
			}
		}
		return null;
	}
	
}

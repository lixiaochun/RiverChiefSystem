package onemap.util;

public class IdUitl {

	public static String getRegionId(String regionId){
		
		String str=String.valueOf(regionId);
		if(str.length() != 12)
			return "";
		String sheng=str.substring(0, 2);
		String shi=str.substring(2,4);
		String xian=str.substring(4, 6);
		String jiedao=str.substring(6, 9);
		String cun=str.substring(9, 12);
		str="";
		if(!sheng.equals("00")){
			str=sheng;
			if(!shi.equals("00")){
				str+=shi;
				if(!xian.equals("00")){
					str+=xian;
					if(!jiedao.equals("000")){
						str+=jiedao;
						if(!cun.equals("000")){
							str+=cun;
						}
					}
				}
			}
		}
		regionId=str;
		System.out.println("regionId:"+regionId);
		return regionId;
	}
	
	public static String getRiverId(String riverId){
		
		String str=String.valueOf(riverId);
		
		riverId=str;
		return riverId;
	}
	
}

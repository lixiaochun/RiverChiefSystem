package basemanage.util;

/**
 * 主键id处理工具
 * @author wangwch
 *
 */
public class BaseIdUtil {

	/**
	 * 获取网格id的
	 * @param id
	 * @return
	 */
	public static String getGridIdPrefix(String id) {
		String str = id;
		if(str.length() != 12)
			return "";
		String sheng=str.substring(0, 2);
		String shi=str.substring(2,4);
		String xian=str.substring(4, 6);
		String zhen=str.substring(6, 9);
		String cun=str.substring(9, 12);
		str="";
		if(!sheng.equals("00")){
			str+=sheng;
			if(!shi.equals("00")){
				str+=shi;
				if(!xian.equals("00")){
					str+=xian;
					if(!zhen.equals("000")){
						str+=zhen;
						if(!cun.equals("000")){
							str+=cun;
						}
					}
				}
			}
		}
		
		return str;
	} 
	
	/**
	 * 获取网格id的子节点级别
	 * @param id
	 * @return
	 */
	public static String getGridIdLevel(String id) {
		String str = id;
		if(str.length() != 12)
			return "";
		String sheng=str.substring(0, 2);
		String shi=str.substring(2,4);
		String xian=str.substring(4, 6);
		String zhen=str.substring(6, 9);
		String cun=str.substring(9, 12);
		String level = "";
		if(sheng.equals("00")){
			level = "1";
		}
		if(!shi.equals("00")){
			level = "2";
		}
		if(!xian.equals("00")){
			level = "3";
		}
		if(!zhen.equals("000")){
			level = "4";
		}
		if(!cun.equals("000")){
			level = "5";
		}
		level = String.valueOf(Integer.valueOf(level)+1);
		return level;
	} 
	
	/**
	 * 获取河流id的前缀
	 * @param id
	 * @return
	 */
	public static String getRiverIdPrefix(String id) {
		String str = id.substring(2, id.length());
		if(str.length() != 12)
			return "BR";
		String sheng=str.substring(0, 2);
		String shi=str.substring(2,4);
		String xian=str.substring(4, 6);
		String zhen=str.substring(6, 9);
		String cun=str.substring(9, 12);
		str="BR";
		if(!sheng.equals("00")){
			str+=sheng;
			if(!shi.equals("00")){
				str+=shi;
				if(!xian.equals("00")){
					str+=xian;
					if(!zhen.equals("000")){
						str+=zhen;
						if(!cun.equals("000")){
							str+=cun;
						}
					}
				}
			}
		}
		
		return str;
	} 

	/**
	 * 获取河流id的子节点级别
	 * @param id
	 * @return
	 */
	public static String getRiverIdLevel(String id) {
		String str = id.substring(2, id.length());
		if(str.length() != 12)
			return "";
		String sheng=str.substring(0, 2);
		String shi=str.substring(2,4);
		String xian=str.substring(4, 6);
		String zhen=str.substring(6, 9);
		String cun=str.substring(9, 12);
		String level = "";
		if(sheng.equals("00")){
			level = "1";
		}
		if(!shi.equals("00")){
			level = "2";
		}
		if(!xian.equals("00")){
			level = "3";
		}
		if(!zhen.equals("000")){
			level = "4";
		}
		if(!cun.equals("000")){
			level = "5";
		}
		level = String.valueOf(Integer.valueOf(level)+1);
		return level;
	}
	
	/**
	 * 获取河段id的前缀
	 * @param id
	 * @return
	 */
	public static String getReachIdPrefix(String id) {
		if(id.equals("") || id == null){
			return "BR";
		}
		String str = id.substring(2, id.length());
		if(str.length() != 12)
			return "BR";
		String sheng=str.substring(0, 2);
		String shi=str.substring(2,4);
		String xian=str.substring(4, 6);
		String zhen=str.substring(6, 9);
		String cun=str.substring(9, 12);
		str="BR";
		if(!sheng.equals("00")){
			str+=sheng;
			if(!shi.equals("00")){
				str+=shi;
				if(!xian.equals("00")){
					str+=xian;
					if(!zhen.equals("000")){
						str+=zhen;
						if(!cun.equals("000")){
							str+=cun;
						}
					}
				}
			}
		}
		
		return str;
	} 
	
	/**
	 * 获取河段id的子节点级别
	 * @param id
	 * @return
	 */
	public static String getReachIdLevel(String id) {
		if(id.equals("") || id == null){
			return null;
		}
		String str = id.substring(2, id.length());
		if(str.length() != 12)
			return "";
		String sheng=str.substring(0, 2);
		String shi=str.substring(2,4);
		String xian=str.substring(4, 6);
		String zhen=str.substring(6, 9);
		String cun=str.substring(9, 12);
		String level = "";
		if(sheng.equals("00")){
			level = "1";
		}
		if(!shi.equals("00")){
			level = "2";
		}
		if(!xian.equals("00")){
			level = "3";
		}
		if(!zhen.equals("000")){
			level = "4";
		}
		if(!cun.equals("000")){
			level = "5";
		}
		level = String.valueOf(Integer.valueOf(level)+1);
		return level;
	}
	
	
}

package common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 该工具类 “暂时”主要应用数据实体的校验
 * 2017-08-25
 * @author 李玮勤
 */
public class ValidateUtil {
	/**
	 * 该静态方法 “暂时”主要应用数据实体的校验
	 * 
	 * @param temp T“泛型，入参可以是任意实体”
	 * @return
	 *        msg  List<String> 返回字符串类型链表，其中包含了错误字段的错误信息。同样可以用msg做判断，当msg.size>0则校验出错应与相应处理，遍历msg可得到详细错误信息；
	 *        当msg.size=0则校验成功无需其他操作。
	 *        注：该msg链表为无序数组，有序待优化！
	 *  
	 */
	public static <T> List<String> BeanValidate(T temp){
		  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();  
	        Validator validator = factory.getValidator();  
	        Set<ConstraintViolation<T>> validators = validator.validate(temp);  
	        List<String> msg = new ArrayList<String>();
	        for (ConstraintViolation<T> constraintViolation : validators) {  
	        	msg.add(constraintViolation.getMessage());
	            System.out.println(constraintViolation.getMessage());  
	        }  
	        System.out.println("--------------------------------------------------------");
	      
		return msg;
	}
}

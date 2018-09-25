package tflow.com.yzs.flow.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

	/**
	 * 导出字段的标题名称
	 */
	String title();
	
	/**
	 * 导出字段排序
	 */
	int sort() default 0;
	
	/**
	 * 导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）
	 */
	int align() default 0;
	
	/**
	 * 如果是日期类型参数，将其转换成字符串的格式类型
	 */
	String datePattern() default "yyyy-MM-dd";
	
	/**
	 * 利用枚举类对字段的值进行转换
	 */
	String jsonStr() default "";
	
}

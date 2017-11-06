package test;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import com.app.core.annotation.Excel;

public class User {
	
	@Excel(exportName = "名称",exportFieldWidth = 20 , orderNum = "1", align = HSSFCellStyle.ALIGN_LEFT)
	private String name;
	@Excel(exportName = "年龄",exportFieldWidth = 20 , orderNum = "2", align = HSSFCellStyle.ALIGN_RIGHT)
	private Integer age;
	@Excel(exportName = "身价",exportFieldWidth = 20 , orderNum = "3", align = HSSFCellStyle.ALIGN_RIGHT)
	private Double money;
	@Excel(exportName = "生日",exportFieldWidth = 20 , orderNum = "4",exportFormat = "yyyy-MM-dd",align = HSSFCellStyle.ALIGN_CENTER)
	private Date bir;
	
	public User(String name, Integer age, Double money, Date bir) {
		super();
		this.name = name;
		this.age = age;
		this.money = money;
		this.bir = bir;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getBir() {
		return bir;
	}
	public void setBir(Date bir) {
		this.bir = bir;
	}
	
	
	
}

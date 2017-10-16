package com.lyg.mybatisdb;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface Mapper {

	@Insert("insert into studenttb(id, name, age, otherinfo) values(#{id}, #{name},#{age},#{otherinfo})")
	public void insertStudent(Student std);
	
	@Update("update studenttb set name=#{name},age=#{age}, otherinfo=#{otherinfo} where id=#{id}")
	public void updateStudent(Student std);
	
	@Select("select * from studenttb where id=#{id}")
	public Student getStudent(int id);
	
	@Select("select * from studenttb")
	public List<Student> getAllStudents();
	
}

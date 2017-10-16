package com.lyg.mybatisdb;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface Mapper6 {

	@Insert("insert into studenttb6(id, name, age, otherinfo) values(#{id}, #{name},#{age},#{otherinfo})")
	public void insertStudent(Student std);
	
	@Update("update studenttb6 set name=#{name},age=#{age}, otherinfo=#{otherinfo} where id=#{id}")
	public void updateStudent(Student std);
	
	@Select("select * from studenttb6 where id=#{id}")
	public Student getStudent(int id);
	
	@Select("select * from studenttb6")
	public List<Student> getAllStudents();
	
}

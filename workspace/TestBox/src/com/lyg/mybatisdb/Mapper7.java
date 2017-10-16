package com.lyg.mybatisdb;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface Mapper7 {

	@Insert("insert into studenttb7(id, name, age, otherinfo) values(#{id}, #{name},#{age},#{otherinfo})")
	public void insertStudent(Student std);
	
	@Update("update studenttb7 set name=#{name},age=#{age}, otherinfo=#{otherinfo} where id=#{id}")
	public void updateStudent(Student std);
	
	@Select("select * from studenttb7 where id=#{id}")
	public Student getStudent(int id);
	
	@Select("select * from studenttb7")
	public List<Student> getAllStudents();
	
}

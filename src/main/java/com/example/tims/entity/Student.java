package com.example.tims.entity;


import com.example.tims.anno.MySqlField;
import com.example.tims.anno.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(tableName = "students")
public class Student {

    @MySqlField(name = "id", notNull = true, primaryKey = true, comment = "学号",length = 50)
    private String id;
    @MySqlField(name = "name", notNull = true,  comment = "姓名",length = 50)
    private String name;
    @MySqlField(name = "gender", notNull = true, comment = "性别",length = 50)
    private String gender;
    @MySqlField(name = "phoneNumber",notNull = true,  comment = "电话号码",length = 50)
    private String phoneNumber;
}

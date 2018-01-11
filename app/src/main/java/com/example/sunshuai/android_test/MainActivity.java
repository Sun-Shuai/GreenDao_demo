package com.example.sunshuai.android_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.greendao.query.QueryBuilder;

import com.example.sunshuai.android_test.model.DaoMaster;
import com.example.sunshuai.android_test.model.DaoSession;
import com.example.sunshuai.android_test.model.Student;
import com.example.sunshuai.android_test.model.StudentDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivity self = MainActivity.this;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private StudentDao stuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStuDao();
        initView();
    }


    /**
     * 获取StudentDao
     */
    private void getStuDao() {
        // 创建数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(self, "test.db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        stuDao = daoSession.getStudentDao();
    }


    private void initView() {
        // 新增一条数据
        findViewById(R.id.id_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Student stu = new Student(null, "001", "赵大", "男", "50");
                    long end = stuDao.insert(stu);
                    if (end > 0) {
                        Toast.makeText(self, "001新增成功~", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(self, "001新增失败~", Toast.LENGTH_SHORT).show();
                    }
                    stuDao.insert(new Student(null, "002", "钱二", "男", "66"));
                    stuDao.insert(new Student(null, "003", "孙三", "男", "23"));
                    stuDao.insert(new Student(null, "004", "李四", "男", "65"));
                    Toast.makeText(self, "002 003 004新增成功~", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(self, "学员编号唯一，新增失败~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 新增List集合数据
        findViewById(R.id.id_insert_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Student> stuList = new ArrayList<Student>();
                    stuList.add(new Student(null, "005", "许婧", "女", "43"));
                    stuList.add(new Student(null, "006", "许婧", "女", "35"));
                    stuList.add(new Student(null, "007", "许婧", "女", "99"));
                    stuList.add(new Student(null, "008", "许婧", "女", "88"));
                    stuDao.insertInTx(stuList);
                    Toast.makeText(self, "新增成功~", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(self, "学员编号唯一，新增失败~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 查询所有
        findViewById(R.id.id_search_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Student> stuList = stuDao.queryBuilder().list();
                    if (stuList != null) {
                        String searchAllInfo = "";
                        for (int i = 0; i < stuList.size(); i++) {
                            Student stu = stuList.get(i);
                            searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
                        }
                        TextView tvSearchInfo = (TextView) findViewById(R.id.id_search_all_info);
                        tvSearchInfo.setText(searchAllInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 查询指定数据 查询姓名为"赵大"的信息
        findViewById(R.id.id_search_assign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String searchAssignInfo = "";
                    List<Student> stuList = stuDao.queryBuilder().where(StudentDao.Properties.StuName.eq("赵大")).list();
                    for (int i = 0; i < stuList.size(); i++) {
                        Student stu = stuList.get(i);
                        searchAssignInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
                    }
                    TextView tvSearchAssign = (TextView) findViewById(R.id.id_search_assign_info);
                    tvSearchAssign.setText(searchAssignInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 查询指定数据 查询姓名为"许婧"的信息并按照成绩排序-降序
        findViewById(R.id.id_search_assign_order_desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String searchAssignOrderDesc = "";
                    List<Student> stuList = stuDao.queryBuilder().where(StudentDao.Properties.StuName.eq("许婧")).orderDesc(StudentDao.Properties.StuScore).list();
                    for (int i = 0; i < stuList.size(); i++) {
                        Student stu = stuList.get(i);
                        searchAssignOrderDesc += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore();
                    }
                    TextView tvSearchOrderDesc = (TextView) findViewById(R.id.id_search_assign_order_desc_info);
                    tvSearchOrderDesc.setText(searchAssignOrderDesc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 查询指定数据 查询姓名为"许婧"的信息并按照成绩排序-升序
        findViewById(R.id.id_search_assign_order_asc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String searchAssignOrderDesc = "";
                    List<Student> stuList = stuDao.queryBuilder().where(StudentDao.Properties.StuName.eq("许婧")).orderAsc(StudentDao.Properties.StuScore).list();
                    for (int i = 0; i < stuList.size(); i++) {
                        Student stu = stuList.get(i);
                        searchAssignOrderDesc += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore();
                    }
                    TextView tvSearchOrderDesc = (TextView) findViewById(R.id.id_search_assign_order_asc_info);
                    tvSearchOrderDesc.setText(searchAssignOrderDesc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 组合查询数据 查询姓名为"许婧" 并且成绩小于等于60
        findViewById(R.id.id_search_combination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String searchAssignOrderDesc = "";
                    QueryBuilder<Student> stuQB = stuDao.queryBuilder();
                    stuQB.where(StudentDao.Properties.StuName.eq("许婧"), StudentDao.Properties.StuScore.le("60"));
                    List<Student> stuList = stuQB.list();
                    for (int i = 0; i < stuList.size(); i++) {
                        Student stu = stuList.get(i);
                        searchAssignOrderDesc += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
                    }
                    TextView tvSearchOrderDesc = (TextView) findViewById(R.id.id_search_combination_info);
                    tvSearchOrderDesc.setText(searchAssignOrderDesc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 查询所有返回数据 但只返回前三条数据
        findViewById(R.id.id_search_limit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Student> stuList = stuDao.queryBuilder().limit(3).list();
                    if (stuList != null) {
                        String searchAllInfo = "";
                        for (int i = 0; i < stuList.size(); i++) {
                            Student stu = stuList.get(i);
                            searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
                        }
                        TextView tvSearchInfo = (TextView) findViewById(R.id.id_search_limit_info);
                        tvSearchInfo.setText(searchAllInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 查询所有返回数据 但只返回前三条数据 并且跳过第一条数据
        findViewById(R.id.id_search_limit_offset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Student> stuList = stuDao.queryBuilder().limit(3).offset(1).list();
                    if (stuList != null) {
                        String searchAllInfo = "";
                        for (int i = 0; i < stuList.size(); i++) {
                            Student stu = stuList.get(i);
                            searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
                        }
                        TextView tvSearchInfo = (TextView) findViewById(R.id.id_search_limit_offset_info);
                        tvSearchInfo.setText(searchAllInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 查询所有信息总条数
        findViewById(R.id.id_search_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int stuSumCount = stuDao.queryBuilder().list().size();
                    TextView tvSearchInfo = (TextView) findViewById(R.id.id_search_count_info);
                    tvSearchInfo.setText(stuSumCount + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 删除指定信息
        findViewById(R.id.id_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    stuDao.queryBuilder().where(StudentDao.Properties.StuName.eq("李四")).buildDelete().executeDeleteWithoutDetachingEntities();
                    Toast.makeText(self, "删除成功~", Toast.LENGTH_SHORT).show();
//                stuDao.delete(new Student()); // 删除指定对象
//                stuDao.deleteAll(); // 删除所有
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 更新指定信息
        findViewById(R.id.id_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Student student = stuDao.queryBuilder().where(StudentDao.Properties.StuName.eq("钱二")).build().unique();
                    if (student != null) {
                        student.setStuName("I Love You");
                        stuDao.update(student);
                    }
                    Toast.makeText(self, "更新成功~", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

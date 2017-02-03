package com.alienlab.db;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/1/3.
 */

/**
 * alienlab entity
 * 自定义简单entity的dao操作。
 * @param <T> entity类型
 */
@Component
public class AlienEntity<T> {

    @Autowired
    Logger logger = Logger.getLogger(AlienEntity.class);

    /**
     *
     * @param entity
     * @return
     */
    public String InsertSql(T entity){
        //解析表名
        Class entityClass=entity.getClass();
        Table table= (Table) entityClass.getAnnotation(Table.class);
        String tableName=table.name();
        StringBuffer sqlbuffer=new StringBuffer();
        sqlbuffer.append("insert into ").append(tableName);
        StringBuffer fieldbuffer=new StringBuffer();//记录需要insert的字段
        StringBuffer valuebuffer=new StringBuffer();//记录insert的value
        //解析字段
        Field[] fields=entityClass.getDeclaredFields();
        for (Field field:fields) {
            Column column=field.getAnnotation(Column.class);
            if(fieldbuffer.length()==0){
                fieldbuffer.append(column.name());
            }else{
                fieldbuffer.append(",").append(column.name());
            }
            if(column!=null){
                try {
                    //日期与字符类型需要单引号
                    if(field.getType().equals(Date.class)||field.getType().equals(String.class)){
                        if(valuebuffer.length()==0){
                            valuebuffer.append(",").append("'").append(field.get(entity)).append("'");
                        }else{
                            valuebuffer.append("'").append(field.get(entity)).append("'");
                        }
                    }else{
                        if(valuebuffer.length()==0){
                            valuebuffer.append(",").append(field.get(entity));
                        }else{
                            valuebuffer.append(field.get(entity));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        sqlbuffer.append("(").append(fieldbuffer.toString()).append(")").append(" values(").append(valuebuffer.toString()).append(")");
        logger.info("entity "+entityClass.getName()+" insert sql is:"+sqlbuffer.toString());
        return sqlbuffer.toString();
    }

    public String UpdateSql(T entity){
        return "";
    }


    /**
     * 将查询出的list转换成为指定的entity对象
     * @param list  查询结果集
     * @param entityclass
     */
    public List list2T(List<Map<String, Object>> list,Class<T> entityclass){
        try {
            List result=new ArrayList();
            if(list!=null){
                Field[] fields=entityclass.getDeclaredFields();
                for(int i=0;i<list.size();i++){
                    //每行数据产生一个实例
                    Object instance=entityclass.newInstance();
                    Map<String,Object> item=list.get(i);
                    //根据实例中的字段，从list中获得值
                    for(int j=0;j<fields.length;j++){
                        fields[j].setAccessible(true);
                        Column column=fields[j].getAnnotation(Column.class);
                        if(column!=null){
                            if(item.containsKey(column.name().toUpperCase())){
                                Object value=item.get(column.name().toUpperCase());
                                if(value==null||value.equals("")){
                                    fields[j].set(instance,null);//赋值
                                }else{
                                    if(fields[j].getType().equals(float.class)){
                                        value=Float.parseFloat((String)value);
                                    }else if(fields[j].getType().equals(int.class)){
                                        value=Integer.parseInt((String)value);
                                    }else if(fields[j].getType().equals(boolean.class)){
                                        value=Boolean.parseBoolean((String)value);
                                    }else if(fields[j].getType().equals(long.class)){
                                        value=Long.parseLong((String)value);
                                    }else if(fields[j].getType().equals(double.class)){
                                        value=Double.parseDouble((String)value);
                                    }else if(fields[j].getType().equals(Date.class)){
                                        if(((String)value).indexOf(":")>0){
                                            value= DateUtils.getDate((String)value,"yyyy-MM-dd HH:mm:ss");
                                        }else{
                                            value=new Date(Long.parseLong((String)value));
                                        }

                                    }else if(fields[j].getType().equals(String.class)){

                                    }else
                                    {
                                        Constructor constructor = fields[j].getType().getConstructor(String.class);
                                        value=constructor.newInstance(value);
                                    }
                                    fields[j].set(instance,value);//赋值
                                }
                            }
                        }
                    }
                    result.add(instance);
                }
                return result;
            }else{
                return null;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.alienlab.db;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    Logger logger = Logger.getLogger(AlienEntity.class);

    /**
     * 根据entity类型，获取单个对象语句
     * @param entityClass
     * @param id
     * @return
     * @throws Exception
     */
    public String getOne(Class entityClass,long id) throws Exception {
        //Class entityClass=entity.getClass();
        Table table= (Table) entityClass.getAnnotation(Table.class);
        String tableName=table.name();
        Field idfield=getIdField(entityClass);
        Column column=idfield.getAnnotation(Column.class);
        if(column==null){
            PropertyDescriptor pd = null;
            try {
                pd = new PropertyDescriptor(idfield.getName(),
                        entityClass);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            Method getMethod = pd.getReadMethod();//获得get方法
            column=getMethod.getAnnotation(Column.class);
        }
        if(idfield!=null&&column!=null){
            String sql="select * from "+tableName+" where "+column.name()+"="+id;
            return sql;
        }else{
            throw new Exception("can not find id field.");
        }

    }

    /**
     * 根据传入entity类型，生成delete语句
     * @param entityClass
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOne(Class entityClass,long id) throws Exception{
        //Class entityClass=entity.getClass();
        Table table= (Table) entityClass.getAnnotation(Table.class);
        String tableName=table.name();
        Field idfield=getIdField(entityClass);
        Column column=idfield.getAnnotation(Column.class);
        if(column==null){
            PropertyDescriptor pd = null;
            try {
                pd = new PropertyDescriptor(idfield.getName(),
                        entityClass);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            Method getMethod = pd.getReadMethod();//获得get方法
            column=getMethod.getAnnotation(Column.class);
        }
        if(idfield!=null&&column!=null){
            String sql="delete from "+tableName+" where "+column.name()+"="+id;
            return sql;
        }else{
            throw new Exception("can not find id field.");
        }
    }

    public T setId(T entity,Long id){
        Field field=getIdField(entity.getClass());
        try {
            field.set(entity,id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public Long getIdValue(T entity){
        Field field=getIdField(entity.getClass());
        try {
            return (Long)field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 通过entity实例生成insert语句
     * @param entity
     * @return
     */
    public String InsertSql(T entity){
        //解析表名
        Class entityClass=entity.getClass();
        Field idfield=getIdField(entityClass);
        Table table= (Table) entityClass.getAnnotation(Table.class);
        String tableName=table.name();
        StringBuffer sqlbuffer=new StringBuffer();
        sqlbuffer.append("insert into ").append(tableName);
        StringBuffer fieldbuffer=new StringBuffer();//记录需要insert的字段
        StringBuffer valuebuffer=new StringBuffer();//记录insert的value
        //解析字段
        Field[] fields=entityClass.getDeclaredFields();
        for (Field field:fields) {
            field.setAccessible(true);
            Column column=field.getAnnotation(Column.class);
            if(column==null){
                PropertyDescriptor pd = null;
                try {
                    pd = new PropertyDescriptor(field.getName(),
                            entityClass);
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
                Method getMethod = pd.getReadMethod();//获得get方法
                column=getMethod.getAnnotation(Column.class);
            }
            //如果不是主键字段
            if(!field.getName().equals(idfield.getName())){
                if(fieldbuffer.length()==0){
                    fieldbuffer.append(column.name());
                }else{
                    fieldbuffer.append(",").append(column.name());
                }

                if(column!=null){
                    try {
                        //日期与字符类型需要单引号
                        if(field.getType().equals(Date.class)||field.getType().equals(String.class)||field.getType().equals(Timestamp.class)){
                            Object d=field.get(entity);
                            if(d==null||d.equals("null")){
                                d="";
                                if(field.getType().equals(Date.class)||field.getType().equals(Timestamp.class)){
                                    DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    d=ZonedDateTime.now().format(format);
                                }
                            }
                            if(valuebuffer.length()>0){
                                valuebuffer.append(",").append("'").append(d).append("'");
                            }else{
                                valuebuffer.append("'").append(field.get(entity)).append("'");
                            }
                        }else{
                            if(valuebuffer.length()>0){
                                valuebuffer.append(",").append(field.get(entity));
                            }else{
                                valuebuffer.append(field.get(entity));
                            }
                        }
                    }catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        sqlbuffer.append("(").append(fieldbuffer.toString()).append(")").append(" values(").append(valuebuffer.toString()).append(")");
        logger.info("entity "+entityClass.getName()+" insert sql is:"+sqlbuffer.toString());
        return sqlbuffer.toString();
    }

    public String UpdateSql(T entity){
        Field idfield=getIdField(entity.getClass());
        if(idfield==null){
            logger.error("update error,there is no id field in entity!");
            return null;
        }
        //解析表名
        Class entityClass=entity.getClass();
        Table table= (Table) entityClass.getAnnotation(Table.class);
        String tableName=table.name();
        StringBuffer sqlbuffer=new StringBuffer();
        sqlbuffer.append("update ").append(tableName).append(" set ");
        StringBuffer wherebuffer=new StringBuffer();
        StringBuffer updatebuffer=new StringBuffer();
        //解析字段
        Field[] fields=entityClass.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            Column column=field.getAnnotation(Column.class);
            if(column==null){
                PropertyDescriptor pd = null;
                try {
                    pd = new PropertyDescriptor(field.getName(),
                            entityClass);
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
                Method getMethod = pd.getReadMethod();//获得get方法
                column=getMethod.getAnnotation(Column.class);
            }
            if(column!=null){
                try {
                    if(field.getName().equals(idfield.getName())){

                        wherebuffer.append(" where ").append(column.name()).append("=").append(field.get(entity));
                    }else{
                        //日期与字符类型需要单引号
                        if(field.getType().equals(Date.class)||field.getType().equals(String.class)||field.getType().equals(Timestamp.class)){
                            if(updatebuffer.length()>0){
                                Object o=null;
                                if(field.get(entity)==null||field.get(entity).equals("null")){
                                    continue;
                                }else{
                                    o=field.get(entity);
                                    updatebuffer.append(","+column.name()).append("='").append(o).append("'");
                                }
                            }else{
                                Object o=null;
                                if(field.get(entity)==null||field.get(entity).equals("null")){
                                    continue;
                                }else{
                                    o=field.get(entity);
                                    updatebuffer.append(column.name()).append("='").append(o).append("'");
                                }

                            }
                        }else{
                            if(updatebuffer.length()>0){
                                updatebuffer.append(",").append(column.name()).append("=").append(field.get(entity));
                            }else{
                                updatebuffer.append(column.name()).append("=").append(field.get(entity));
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if(updatebuffer.length()>0&&wherebuffer.length()>0){
            sqlbuffer.append(updatebuffer.toString()).append(wherebuffer.toString());
            return sqlbuffer.toString();
        }else{
            logger.error("update error,no id field matched!");
            return null;
        }
    }

    public  Field getIdField(Class entityClass){
        //Class entityClass=entity.getClass();
        Field[] fields=entityClass.getDeclaredFields();
        for(Field field:fields){
            Id id=field.getAnnotation(Id.class);
            if(id==null){
                PropertyDescriptor pd = null;
                try {
                    pd = new PropertyDescriptor(field.getName(),
                            entityClass);
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
                Method getMethod = pd.getReadMethod();//获得get方法
                id=getMethod.getAnnotation(Id.class);
            }
            if(id!=null){
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }


    public T Obj2T(Map<String,Object> item,Class entityclass){
        if(item==null) return null;
        try {
            Object instance=entityclass.newInstance();
            Field[] fields=entityclass.getDeclaredFields();
            //根据实例中的字段，从list中获得值
            for(int j=0;j<fields.length;j++){
                fields[j].setAccessible(true);
                Column column=fields[j].getAnnotation(Column.class);
                if(column==null){
                    PropertyDescriptor pd = null;
                    try {
                        pd = new PropertyDescriptor(fields[j].getName(),
                                entityclass);
                    } catch (IntrospectionException e) {
                        e.printStackTrace();
                    }
                    Method getMethod = pd.getReadMethod();//获得get方法
                    column=getMethod.getAnnotation(Column.class);
                }
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
                            }else if(fields[j].getType().equals(Timestamp.class)){
                                value=Timestamp.valueOf((String)value);
                            }else {
                                Constructor constructor = fields[j].getType().getConstructor(String.class);
                                value=constructor.newInstance(value);
                            }
                            fields[j].set(instance,value);//赋值
                        }
                    }
                }
            }
            return (T)instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将查询出的list转换成为指定的entity对象
     * @param list  查询结果集
     * @param entityclass
     */
    public List list2T(List<Map<String, Object>> list,Class entityclass){
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
                        if(column==null){
                            PropertyDescriptor pd = null;
                            try {
                                pd = new PropertyDescriptor(fields[j].getName(),
                                        entityclass);
                            } catch (IntrospectionException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            if(pd!=null) {
                                Method getMethod = pd.getReadMethod();//获得get方法
                                column = getMethod.getAnnotation(Column.class);
                            }
                        }
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
                                    }else if(fields[j].getType().equals(Timestamp.class)){
                                        value=Timestamp.valueOf((String)value);
                                    }else {
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

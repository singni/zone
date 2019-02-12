package com.zone.bos.action;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zone.bos.crm.CustomerService;
import com.zone.bos.dao.DecidedzoneService;
import com.zone.bos.service.*;
import com.zone.bos.utils.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Rian on 2019/1/22.
 * Copyright © 2018 Rian. All rights reserved
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    @Autowired
    protected RegionService regionService;
    @Autowired
    protected StaffService staffService;
    @Autowired
    protected SubareaService subareaService;
    @Autowired
    protected DecidedzoneService decidedzoneService;
    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected NoticebillService noticebillService;
    @Autowired
    protected WorkordermanageService workordermanageService;
    @Autowired
    protected  UserService userService;
    @Autowired
    protected  FunctionService functionService;
    @Autowired
    protected RoleService roleService;
    protected T tClass;

    public T getModel() {

        return  tClass;
    }

    protected PageBean pageBean = new PageBean();
    DetachedCriteria detachedCriteria = null;

    public void setRows(int rows) {
        pageBean.setPageSize(rows);
    }

    public void setPage(int page) {
        pageBean.setCurrentPage(page);
    }
    public void writePageBean2Json(PageBean pageBean)
            throws IOException {
        String json = JSONObject.toJSONString(pageBean);
        ServletActionContext.getResponse().setContentType(
                "text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
    }
    public void writeObject2Json(Object object)
            throws IOException {
        String json = JSONObject.toJSONString(object);
        ServletActionContext.getResponse().setContentType(
                "text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
    }
    public  void writeList2Json(List list) throws IOException {

        String json = JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
        ServletActionContext.getResponse().setContentType(
                "text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
    }
    public BaseAction(){
        ParameterizedType genericSuperclass = null;

        if(this.getClass().getGenericSuperclass() instanceof ParameterizedType){
            genericSuperclass = (ParameterizedType) this
                    .getClass().getGenericSuperclass();
        }else{//当前为Action创建了代理
            genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
        }

        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        // 获得实体类型
        Class<T> entityClass = (Class<T>) actualTypeArguments[0];
        detachedCriteria = DetachedCriteria.forClass(entityClass);
        pageBean.setDetachedCriteria(detachedCriteria);
        try {
            // 通过反射创建对象
            tClass = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public  void writeList2Json(List list, String[] excludes) throws IOException {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONArray jsonObject = JSONArray.fromObject(list, jsonConfig);
        String json = jsonObject.toString();
        ServletActionContext.getResponse().setContentType(
                "text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
    }
}

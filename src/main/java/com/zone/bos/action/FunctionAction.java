package com.zone.bos.action;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zone.bos.domain.Function;
import com.zone.bos.utils.JsonUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * 权限管理
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
    /**
     * 分页查询方法
     *
     * @throws IOException
     */
    public String pageQuery() throws IOException {
        String page = tClass.getPage();
        pageBean.setCurrentPage((Integer.parseInt(page)));
        functionService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean);
        return NONE;
    }

    public String listajax() throws IOException {
        List<Function> list = functionService.findAll();
        this.writeList2Json(list);
        return NONE;
    }

    /**
     * 添加权限
     */
    public String add() {
        functionService.save(tClass);

        return "list";
    }

    /**
     * 根据登录人查询对应的菜单数据
     *
     * @throws IOException
     */
    public String findMenu() throws IOException {

        List<Function> list = functionService.findMenu();
        String json = JsonUtils.objectToJson(list);
        ServletActionContext.getResponse().setContentType(
                "text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
        return NONE;
    }
}

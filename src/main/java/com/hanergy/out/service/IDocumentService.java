package com.hanergy.out.service;

import com.hanergy.out.entity.Document;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.print.Doc;
import java.util.List;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-10
 */
public interface IDocumentService extends IService<Document> {

    /*
     * @Author hld
     * @Description //TODO
     * @Date 17:40 2019/6/10
     * @Param 文件信息
     * @return void
     **/
    public void saveDocument(Document document);

    /*
     * @Author hld
     * @Description //通过外键id删除附件信息
     * @Date 17:45 2019/6/10
     * @Param [serviceId] 外键id
     * @return void
     **/
    public void delDocumentByServiceId(Long serviceId);

    /*
     * @Author hld
     * @Description // 通过外键id获取附件信息
     * @Date 9:50 2019/6/11
     * @Param [serviceId]
     * @return java.util.List<com.hanergy.out.entity.Document>
     **/
    public List<Document> getDocumentByServiceId(Long serviceId);

}

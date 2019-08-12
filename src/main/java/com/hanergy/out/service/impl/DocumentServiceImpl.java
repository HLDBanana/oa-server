package com.hanergy.out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hanergy.out.entity.Document;
import com.hanergy.out.dao.DocumentMapper;
import com.hanergy.out.service.IDocumentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-10
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements IDocumentService {

    @Override
    public void saveDocument(Document document) {
        this.baseMapper.insert(document);
    }

    @Override
    public void delDocumentByServiceId(Long serviceId) {
        UpdateWrapper<Document> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("service_id",serviceId);
        this.baseMapper.delete(updateWrapper);
    }

    @Override
    public List<Document> getDocumentByServiceId(Long serviceId) {
        QueryWrapper<Document> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_id",serviceId);
        return this.baseMapper.selectList(queryWrapper);
    }
}

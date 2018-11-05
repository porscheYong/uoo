package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.config.SolrCacheConfig;
import cn.ffcs.uoo.core.organization.service.SolrService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.SolrOrgVo;
import cn.ffcs.uoo.core.organization.vo.SolrPsonOrgVo;
import cn.ffcs.uoo.core.organization.vo.SolrReqVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/21
 */
@Service
public class SolrServiceImpl implements SolrService {


    private static String orgCoreInstance = "org";
    private static String psnOrgCoreInstance = "pson";
    @Autowired
    private SolrClient solrClient;

    @Autowired
    private SolrCacheConfig solrCacheConfig;

    private HttpSolrClient connetHttpSolrClientServer(String coreName){
        HttpSolrClient server = new HttpSolrClient(solrCacheConfig.getHost() +"/"+ coreName);
        server.setSoTimeout(5000);
        server.setConnectionTimeout(1000);
        server.setDefaultMaxConnectionsPerHost(1000);
        server.setMaxTotalConnections(5000);
        return server;
    }
    /**
     * 数据插入solr
     * @param coreName
     * @param input
     * @return
     */
    @Override
    public boolean addDataIntoSolr(String coreName ,SolrInputDocument input){
        boolean flag = false;
        try {
            solrClient = connetHttpSolrClientServer(coreName);
            solrClient.add(input);
            solrClient.commit();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                solrClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    /**
     * 数据插入solr
     * @param coreName
     * @param id
     * @return
     */
    @Override
    public boolean deleteDataIntoSolr(String coreName,String id){
        boolean flag = false;
        try {
            solrClient = connetHttpSolrClientServer(coreName);
            UpdateResponse ur = solrClient.deleteById(id);
            UpdateResponse c = solrClient.commit();
            System.out.println(c);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                solrClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 更新数据
     * @param id
     * @param maps
     * @return
     */
    @Override
    public boolean updateDataIntoSolr(String coreName,String id,Map<String,String> maps){
        boolean flag = false;
        try {
            solrClient = connetHttpSolrClientServer(coreName);
            Set<String> keys = maps.keySet();
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", id);
            for(String key : keys)
            {
                HashMap<String, Object> oper = new HashMap<String, Object>();
                oper.put("set", maps.get(key));
                doc.addField(key, oper);
            }
            UpdateResponse rsp = solrClient.add(doc);
            System.out.println("update doc id:" + id + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
            UpdateResponse rspCommit = solrClient.commit();

            System.out.println("commit doc to index" + " result:" + rspCommit.getStatus() + " Qtime:" + rspCommit.getQTime());
        } catch (SolrServerException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return flag;
    }
    /**
     * 查询组织
     * @param solrReqVo
     * @return
     */
    @Override
    public List<SolrDocument> getOrgSolrIndex(SolrReqVo solrReqVo) {
        List<SolrDocument> list = null;
        try {
            solrClient = connetHttpSolrClientServer(orgCoreInstance);
            //查询所有数据
            String search = "";
            if(StrUtil.isNullOrEmpty(solrReqVo.getQuery())){
                search="searchText:*";
            }else{
                search="searchText:"+solrReqVo.getQuery();
            }
            SolrQuery solrQuery = new SolrQuery(search);
            solrQuery.setQuery(search);
            String orgRelTypeQue = "orgRelTypeId:"+solrReqVo.getOrgRelTypeId();
            solrQuery.setQuery(orgRelTypeQue);
            int pageNum = solrReqVo.getPageNum()==0?1:solrReqVo.getPageNum();
            int pageSize = solrReqVo.getPageSize()==0?10:solrReqVo.getPageSize();
            solrQuery.setStart((Math.max(pageNum, 1) - 1) * pageSize);
            solrQuery.setRows(pageSize);
            QueryResponse rsp = solrClient.query(solrQuery);
            list = rsp.getResults();
            SolrDocumentList list1 = rsp.getResults();
            Page<SolrDocumentList> page = new Page(pageNum,pageSize);
            page.setTotal(list1.getNumFound());
            //page.set
            long numFound = list1.getNumFound();
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                solrClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 人员组织查询
     * @param solrReqVo
     * @return
     */
    @Override
    public List<SolrDocument> getPsonOrgSolrIndex(SolrReqVo solrReqVo){
        List<SolrDocument> list = null;
        try {
            solrClient = connetHttpSolrClientServer(psnOrgCoreInstance);
            String search = "";
            if(StrUtil.isNullOrEmpty(solrReqVo.getQuery())){
                search="searchText:*";
            }else{
                search="searchText:"+solrReqVo.getQuery();
            }
            SolrQuery solrQuery = new SolrQuery(search);
            solrQuery.setQuery(search);
            String orgRelTypeQue = "orgRelTypeId:"+solrReqVo.getOrgRelTypeId();
            solrQuery.setQuery(orgRelTypeQue);
            int pageNum = solrReqVo.getPageNum()==0?1:solrReqVo.getPageNum();
            int pageSize = solrReqVo.getPageSize()==0?10:solrReqVo.getPageSize();
            solrQuery.setStart((Math.max(pageNum, 1) - 1) * pageSize);
            solrQuery.setRows(pageSize);

            QueryResponse rsp = solrClient.query(solrQuery);
            list = rsp.getResults();
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                solrClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 对象
     * @param solrOrgVo
     * @return
     */
    @Override
    public SolrInputDocument getOrgSolrDate(SolrOrgVo solrOrgVo){
        SolrInputDocument input = new SolrInputDocument();
        input.addField("id", solrOrgVo.getId());
        input.addField("orgId", solrOrgVo.getOrgId());
        input.addField("orgCode", solrOrgVo.getOrgCode());
        input.addField("orgRelTypeId", solrOrgVo.getOrgRelTypeId());
        input.addField("orgName", solrOrgVo.getOrgName());
        input.addField("fullName", solrOrgVo.getFullName());
        return input;
    }

    /**
     *
     * @param solrPsonOrgVo
     * @return
     */
    @Override
    public SolrInputDocument getPsonOrgSolrDate(SolrPsonOrgVo solrPsonOrgVo){
        SolrInputDocument input = new SolrInputDocument();
        input.addField("psnName", solrPsonOrgVo.getPsnName());
        input.addField("certNo", solrPsonOrgVo.getCertNo());
        input.addField("orgRootId", solrPsonOrgVo.getOrgRootId());
        input.addField("userId", solrPsonOrgVo.getUserId());
        input.addField("id", solrPsonOrgVo.getId());
        input.addField("psnFullName", solrPsonOrgVo.getPsnFullName());
        input.addField("acct", solrPsonOrgVo.getAcct());
        input.addField("mobile", solrPsonOrgVo.getMobile());
        return input;
    }
}

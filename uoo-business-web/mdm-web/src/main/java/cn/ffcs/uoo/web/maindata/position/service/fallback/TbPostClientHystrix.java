package cn.ffcs.uoo.web.maindata.position.service.fallback;

import cn.ffcs.uoo.web.maindata.position.dto.TbPost;
import cn.ffcs.uoo.web.maindata.position.service.TbPostClient;
import cn.ffcs.uoo.web.maindata.position.vo.OrgPostInfoVo;
import cn.ffcs.uoo.web.maindata.position.vo.PostNodeVo;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TbPostClientHystrix implements TbPostClient {
    @Override
    public ResponseResult<TbPost> addTbPost(TbPost tbPost) {
        ResponseResult<TbPost> responseResult = new ResponseResult<TbPost>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbPost> removeTbPost(Long postId, Long updateUser) {
        ResponseResult<TbPost> responseResult = new ResponseResult<TbPost>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbPost> updateTbPost(TbPost tbPost) {
        ResponseResult<TbPost> responseResult = new ResponseResult<TbPost>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public List<TbPost> queryPostList(String postName) {
        return null;
    }

    @Override
    public List<TbPost> queryChildPostList(Long parentPostId) {
        return null;
    }

    @Override
    public List<TbPost> queryParentPostList() {
        return null;
    }

    @Override
    public List<OrgPostInfoVo> queryPostListByOrgId(Long orgId) {
        return null;
    }

    @Override
    public ResponseResult<List<PostNodeVo>> getPostTree() {
        ResponseResult<List<PostNodeVo>> responseResult = new ResponseResult<List<PostNodeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}

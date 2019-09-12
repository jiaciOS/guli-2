package com.atguigu.guli.ucenter.service.impl;

import com.atguigu.guli.ucenter.entity.Member;
import com.atguigu.guli.ucenter.mapper.MemberMapper;
import com.atguigu.guli.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2019-09-04
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Member getByOpenid(String openid) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        return baseMapper.selectOne(queryWrapper);
    }
}

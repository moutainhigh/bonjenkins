package com.fuzamei.bonuspoint.controller.asset;

import com.alibaba.fastjson.JSON;
import com.fuzamei.bonuspoint.entity.Result;
import com.fuzamei.bonuspoint.entity.Token;
import com.fuzamei.bonuspoint.entity.dto.asset.CompanyCashRecordDTO;
import com.fuzamei.bonuspoint.entity.dto.user.UserDTO;
import com.fuzamei.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/4/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CompanyCashManageControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private Token token;

    @Before
    public void getToken() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("hr");
        userDTO.setPassword("a123456");
        userDTO.setMark("1234567");
        userDTO.setRole(2);
        String result = this.mockMvc.perform(post("/bonus-point/browser-login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonBytes(userDTO)))
                .andExpect(status().isOk()).andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        token = JSON.parseObject(result, Result.class).getData();
    }

    @Test
    public void saveRechargeCashRecord() throws Exception {
        CompanyCashRecordDTO companyCashRecordDTO = new CompanyCashRecordDTO();
        BigDecimal bigDecimal = new BigDecimal("100");
        companyCashRecordDTO.setAmount(bigDecimal);
        companyCashRecordDTO.setPayword("123456");
        log.info(JSON.toJSONString(companyCashRecordDTO));
        this.mockMvc.perform(post("/bonus-point/asset/company/recharge/save")
                .header("Authorization", token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonBytes(companyCashRecordDTO)))
                .andExpect(status().isOk()).andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
    }

    @Test
    public void saveWithdrawCashRecord() throws Exception {
        CompanyCashRecordDTO companyCashRecordDTO = new CompanyCashRecordDTO();
        BigDecimal bigDecimal = new BigDecimal("100");
        companyCashRecordDTO.setAmount(bigDecimal);
        companyCashRecordDTO.setPayword("123456");
        log.info(JSON.toJSONString(companyCashRecordDTO));
        this.mockMvc.perform(post("/bonus-point/asset/company/withdraw/save")
                .header("Authorization", token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonBytes(companyCashRecordDTO)))
                .andExpect(status().isOk()).andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
    }

}

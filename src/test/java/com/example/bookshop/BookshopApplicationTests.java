package com.example.bookshop;

import com.example.bookshop.bean.*;
import com.example.bookshop.dao.*;
import com.example.bookshop.service.MsgService;
import com.example.bookshop.service.impl.BookServiceImpl;
import com.example.bookshop.service.impl.UserServiceImpl;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.BookVo;
import com.example.bookshop.vo.FavoritesListVo;
import com.example.bookshop.vo.OrderDetailVo;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.bookshop.util.SnowFlake;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
class BookshopApplicationTests{
    @Autowired
    BookServiceImpl bookService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    BookDao bookDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RecordsDao recordsDao;

    @Autowired
    AdminDao adminDao;

    @Autowired
    DealDao dealDao;

    @Autowired
    MsgDao msgDao;
    @Autowired
    MsgService msgService;

    private SnowFlake snowFlake=new SnowFlake(2,3);
    
    @Test
    public void testGetLinkIdList(){
        String A = "测试02";
        String B = "测试07";
        System.out.println("返回值！："+msgDao.getLinkIdList(A,B));
    }
    
    @Test
    public void testIsChatLink(){
        String A = "测试02";
        String B = "测试更改10";
        List<Integer> linkIdList = new ArrayList<>();
        linkIdList.add(msgDao.IsChatLink(A,B).getLinkId());
        linkIdList.add(msgDao.IsChatLink(B,A).getLinkId());
        System.out.println("删除前A,B"+msgDao.IsChatLink(A,B));
        System.out.println("删除前B,A"+msgDao.IsChatLink(B,A));
        System.out.println("删除列表的ID值为:"+linkIdList);
        System.out.println("执行删除操作！共删除了："+msgDao.deleteLinkByList(linkIdList)+"条信息！");
        System.out.println("删除后B,A"+msgDao.IsChatLink(B,A));
        System.out.println("删除后A,B"+msgDao.IsChatLink(A,B));
    }
    
    @Test
    public void testGetHistoryMsg(){
        String A = "测试02";
        String B = "测试更改10";
        List<Msg> msgList = msgService.GetMsgService(A,B);
        System.out.println("删除前的记录为："+msgList+"，其中共有数据："+msgList.size()+"条");
        System.out.println("共删除记录"+msgService.deleteMsgAndLinkService(A,B)+"条");
        List<Msg> newMsgList = msgService.GetMsgService(A,B);
        System.out.println("删除前的记录为："+newMsgList+"，其中共有数据："+newMsgList.size()+"条");
    }


    @Test
    public void testFindAllBook(){
        List<BookVo> bookList = bookService.AllBookService(0,"计算机");
        System.out.println(bookList);
    }

    @Test
    public void testGetBookDtl(){
        List<BookDetailVo> bookDetailVo = bookService.BookDtlService(15);
        System.out.println(bookDetailVo);
    }

    @Test
    public void testGetCategoryList(){
        List<Category> categoryList = bookDao.getCategoryList();
        System.out.println(categoryList);
    }

    @Test
    public void testGetUserService(){
        System.out.println(userService.GetUserService(2));
    }
    @Test
    public void testUpdateUserService(){
        System.out.println("修改前"+userService.GetUserService(11));
        User user = new User();
        user.setUserId(11);
        user.setUserName("测试更改10");
        user.setUserEmail("test@qq.com");
        int i = userService.UpdateUserService(user);
        System.out.println("执行修改后返回的int值"+i);   //测试得成功修改时返回1，失败时（未执行修改）返回0

        System.out.println("修改后："+userService.GetUserService(11));
    }

    @Test
    public void testGetPublishRecordList(){
        List<Book> bookList = recordsDao.getPublishRecordList(1);
        System.out.println(bookList);
    }

    @Test
    public void testGetFavoritesListByUserId(){
        List<FavoritesListVo> favoritesListVoList = recordsDao.getFavoritesListByUserId(6);
        System.out.println(favoritesListVoList);
    }

    @Test
    public void testDeleteFavoriteById(){
        int i = recordsDao.deleteFavoriteById(9);
        System.out.println("删除ID为9时失败返回的int值"+i);
        int j = recordsDao.deleteFavoriteById(8);
        System.out.println("删除ID为9时成功返回的int值"+j);
    }

    @Test
    public void testFindIsFavorite(){
        Favorites favorites = recordsDao.findIsFavorite(6,1);
        System.out.println("favorites:"+favorites);
        if ( favorites == null){
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }

    @Test
    public void testUploadImg(){
        BookImg bookImg = new BookImg();
        bookImg.setImgIsbig(1);
        bookImg.setImgUrl("test");
        bookImg.setItemId(1);
        if (bookDao.addBookImg(bookImg)){
            int bookId = bookImg.getImgId();
            System.out.println("自增ID为："+bookId);
        } else {
            System.out.println("插入未执行！");
        }
    }
    @Test
    public void testGetBookList(){
        System.out.println(adminDao.getBookList());
    }

    @Test
    public void testGetUserList(){
        System.out.println(adminDao.getUserList());
    }

    @Test
    public void testAddOrder(){
        Order order = new Order();
        String orderId=String.valueOf(snowFlake.nextId());
        order.setOrderId(orderId);
        order.setBookId(2);
        order.setSellerId(2);
        order.setBuyerId(6);
        System.out.println("插入成功返回："+dealDao.addOrder(order));
    }

    @Test
    public void testCheckIsOrder(){
        //查询后与null做判断，空（即无数据）返回true，有数据即非空
        System.out.println(dealDao.checkIsOrder(2,5) == null);
    }

    @Test
    void contextLoads() {
    }
    @Test
    public void testGetOrderDtlList(){
        List<OrderDetailVo> orderDetailVoList = dealDao.getOrderDtlList(5);
        System.out.println(orderDetailVoList.size());
    }
    @Test
    public void testDelOrder(){
        System.out.println(dealDao.deleteOrderById("844462615081267200"));
    }
    @Test
    public void testMsg(){
        Msg msg = new Msg();
        msg.setMsgTo("测试02");
        msg.setMsgFrom("测试05");
        msg.setMsgText("测试语句！");
        msg.setMsgDate("time");
        System.out.println("测试插入返回值："+msgDao.insertMsg(msg));
        List<Msg> returnmsg = msgDao.getHistoryMsg("测试02","测试05");
        System.out.println("这是测试查找"+returnmsg);
    }

    @Test
    public void testChat(){
        System.out.println(msgDao.IsChatLink("测试1","测试2"));
    }

}

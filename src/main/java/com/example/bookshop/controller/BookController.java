package com.example.bookshop.controller;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.BookImg;
import com.example.bookshop.bean.Category;
import com.example.bookshop.bean.User;
import com.example.bookshop.service.BookService;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.BookVo;
import com.example.bookshop.vo.ResMsg;
import io.github.classgraph.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api(tags={"图书接口"})
@RestController
@CrossOrigin("http://localhost:8080")
public class BookController {
    @Autowired
    private BookService bookService;

    @ApiOperation(value="获取图书详情")
    @GetMapping("bookDetail")
    public List<BookDetailVo> getBookDetail(@RequestParam(value = "bookId")int bookId){
        List<BookDetailVo> book = bookService.BookDtlService(bookId);
        return book;
    }

    @ApiOperation(value="获取图书详情")
    @GetMapping("getBook")
    public ResMsg getBookById(@RequestParam(value = "bookId")int bookId){
        ResMsg resMsg = new ResMsg();
        Book book = bookService.GetBookByIdService(bookId);
        if ( book != null) {
            resMsg.setErrmsg("获取图书成功！");
            resMsg.setErrcode("0");
            resMsg.setResult(book);
        } else {
            resMsg.setErrmsg("获取图书失败！");
            resMsg.setErrcode("0");
        }
        System.out.println("查找到的图书为："+book);
        return resMsg;
    }

    @ApiOperation(value="获取所有图书")
    @GetMapping("allbook")
    public List<BookVo> getAllBook(@RequestParam(value = "categoryId") int categoryId ,@RequestParam(value = "bookName",required = false) String bookName){
//    public List<BookVo> getAllBook(@RequestBody int categoryId ,@RequestBody String bookName){
        List<BookVo> bookList = bookService.AllBookService(categoryId, bookName);
        return bookList;
    }

    @ApiOperation(value="获取图书种类列表")
    @GetMapping("allcategory")
    public List<Category> getCategoryList(){
        List<Category> categoryList = bookService.AllCategoryService();
        return categoryList;
    }

    @ApiOperation(value="插入图书")
    @PostMapping("insertBook")
    public ResMsg testAddImg(Book book, @RequestParam(value = "files") MultipartFile[] files){
        ResMsg resMsg = new ResMsg();
        String filePath = "E:/imgupload/";  //文件存放路径
        if (files == null || files.length ==0){
            resMsg.setErrmsg("请上传图片！");
            resMsg.setErrcode("1");
            return resMsg;
        }
        if (book == null) {
            resMsg.setErrmsg("图书信息上传错误！");
            resMsg.setErrcode("2");
            return resMsg;
        }
        if (bookService.InsertBookService(book)){
            //图书信息存储成功
            int bookId = book.getBookId();
            System.out.println("当前上传图书信息："+book);
            for (int i=0; i< files.length; i++){
                MultipartFile file = files[i];
                if (!file.isEmpty()){
                    //获取原文件名称
                    String FileName = file.getOriginalFilename();
                    //contains -> 查询字符串中是否含有子字符串 substring(begin)->从索引位置开始获取字符串 lastIndexOf->获取最后出现该字符串位置的索引
                    //获取原文件后缀
                    String type = FileName.contains(".") ? FileName.substring(FileName.lastIndexOf(".")) : null;
                    //假定ISBN号
                    String ISBN = "ISBN_" + book.getBookIsbn() +"_";
                    //假定卖家ID
                    String SellerId = "SID_" + Integer.toString(book.getBookSeller()) + "_";
                    //为文件重新命名
                    String newFileName = SellerId + ISBN + UUID.randomUUID() + type;
                    String savePath = filePath + newFileName;
                    //设置bookImg
                    BookImg bookImg = new BookImg();
                    bookImg.setImgUrl(newFileName);
                    bookImg.setItemId(bookId);
                    bookImg.setImgIsbig(i == 0 ? 1 : 0);
                    //存储到本地磁盘
                    try {
                        file.transferTo(new File(savePath));
                        bookService.InsertBookImgService(bookImg);
                        resMsg.setErrmsg("图书发布成功，请等待管理员审核！");
                        System.out.println("上传成功，文件存储路径为："+savePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("抛出异常："+e.getMessage());
                        System.out.println("上传失败！");
                    }
                }
            }
        } else {
            //图书信息存储失败
            System.out.println("图书信息存储失败");
            resMsg.setErrmsg("图书信息存储失败！");
            resMsg.setErrcode("3");
        }

        return resMsg;
    }

    @PostMapping("/testupload")
    public ResMsg testUpload(Book book, @RequestParam(value = "files") MultipartFile[] files){
        ResMsg resMsg = new ResMsg();
        System.out.println(book);
        if (files == null || files.length ==0){
            resMsg.setErrmsg("请上传图片！");
            return resMsg;
        }
        for (int i=0; i< files.length; i++){
            MultipartFile file = files[i];
            if (!file.isEmpty()){
                System.out.println("文件名："+file.getOriginalFilename());
            }
        }
        return resMsg;
    }
    
    /**
     * 通过图书ID重新上传信息
     */
    @ApiOperation(value = "修改图书信息")
    @PutMapping("changeBook")
    public ResMsg changBookById(@RequestBody Book book){
        ResMsg resMsg = new ResMsg();
        book.setBookRelease(0);
        System.out.println("本次修改的信息为："+book);
        int flag = bookService.ChangeBookService(book);
        if (flag == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("修改失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("修改成功！");
        }
        return resMsg;
    }
}

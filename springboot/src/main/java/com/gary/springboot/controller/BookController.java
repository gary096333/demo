package com.gary.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.gary.springboot.common.Result;
import com.gary.springboot.controller.request.BookPageRequest;
import com.gary.springboot.entity.Admin;
import com.gary.springboot.entity.Book;
import com.gary.springboot.service.IBookService;
import com.gary.springboot.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@RestController // 標示這個接口為Rest風格
@RequestMapping("/book") // 統一前綴路由名稱
public class BookController {

    @Autowired
    IBookService bookService;

    @GetMapping("/list") // 次級路由名稱
    public Result list() {
        List<Book> list = bookService.list();
        return Result.success(list);
    }

    @GetMapping("/page") // 次級路由名稱
    public Result page(BookPageRequest pageRequest) {

        return Result.success(bookService.page(pageRequest));
    }

    @PostMapping("/save") // 次級路由名稱，新增
    public Result save(@RequestBody Book obj) {
        bookService.save(obj);
        return Result.success();
    }

    @GetMapping("/{id}") // 次級路由名稱，先查詢(修改)
    public Result getById(@PathVariable Integer id) {
        Book obj = bookService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update") // 次級路由名稱，再修改(修改)
    public Result update(@RequestBody Book obj) {
        bookService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}") // 次級路由名稱，刪除
    public Result deleteById(@PathVariable Integer id) {
        bookService.deleteById(id);
        return Result.success();
    }

    // 圖片下載後上傳，返回傳給前端
    // System.getProperty("user.dir");//獲取當前項目的根目錄
    // "/files/";//在根目錄下創建資料夾
    private static final String BASE_FILE_PATH = System.getProperty("user.dir") + "/files/";// 設置當前位置下的files的路徑

    // 上傳
    @PostMapping("/file/upload")
    public Result uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();// 獲取圖片原始的名稱

        // 判斷如果為空
        if (StrUtil.isBlank(originalFilename)) {
            return Result.error("文件上傳失敗");
        }

        long flag = System.currentTimeMillis(); // 獲取時間戳

        // D:\intelliji idea\work\demo\springboot/files/1714297244119_下載 (2).jpg
        // 文件名稱是 1714297244119_下載 (2).jpg
        String filePath = BASE_FILE_PATH + flag + "_" + originalFilename;// 生成圖片路徑

        try {
            FileUtil.mkParentDirs(filePath); // 開始創件filePath路徑(創建files名稱的資料夾)
            file.transferTo(FileUtil.file(filePath));

            Admin currentAdmin = TokenUtils.getCurrentAdmin();
            String token = TokenUtils.genTokens(currentAdmin.getId().toString(), currentAdmin.getPassword());// 圖書封面無過期時間

            String url = "http://54.199.152.2:9091/api/book/file/download/" + flag + "?token=" + token;

            // 判斷檔名類型是png jpg pdf
            // play等於1代表可在瀏覽器直接預覽圖片
            if (originalFilename.endsWith("png") || originalFilename.endsWith("jpg")
                    || originalFilename.endsWith("pdf")) {
                url += "&play=1";
            }

            return Result.success(url);

        } catch (Exception e) {
            log.info("文件上傳失敗", e);
        }

        return Result.error("文件上傳失敗");

    }

    // 下載(兩種下載方式:預覽下載、附件下載)
    // 利用play參數區分圖片以哪種方式下載
    @GetMapping("/file/download/{flag}")
    public void download(@PathVariable String flag, @RequestParam(required = false) String play,
            HttpServletResponse response) {
        OutputStream os;
        // 根據BASE_FILE_PATH 獲取路徑下的所有文件
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);

        // 取出文件名稱(根據與flag進行對比，如當前文件夾名稱所對應的flag是一樣的)
        // 1714297244119_下載 (2).jpg(fileName)
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");

        try {
            if (StrUtil.isNotEmpty(fileName)) {
                // 根據 "_" 後的第一位擷取名稱
                // 下載 (2).jpg(realName)
                String realName = fileName.substring(fileName.indexOf("_") + 1);
                // play等於1代表可在瀏覽器直接預覽圖片
                if ("1".equals(play)) {
                    // "inline;filename ="圖片以預覽在瀏覽器可直接打開下載

                    response.addHeader("Content-Disposition",
                            "inline;filename =" + URLEncoder.encode(realName, "UTF-8"));

                } else {
                    // "attachment;filename ="圖片以附件的形式下載
                    response.addHeader("Content-Disposition",
                            "attachment;filename =" + URLEncoder.encode(realName, "UTF-8"));

                }

                // fileName文件名稱格式 flag + originalFilename
                // 讀取文件字節流
                byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();

            }
        } catch (Exception e) {
            log.error("文件下載失敗", e);
        }

    }

}

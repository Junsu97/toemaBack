package junsu.personal.controller;

import junsu.personal.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final IFileService fileService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String url = null;
        try {
            url = fileService.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    @GetMapping(value = "{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(@PathVariable("fileName") String fileName){
        Resource resource = null;
        try {
            resource = fileService.getImage(fileName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return resource;
    }
}
